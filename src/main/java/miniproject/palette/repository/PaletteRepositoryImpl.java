package miniproject.palette.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import miniproject.common.code.PaintColorCode;
import miniproject.global.DBConnection;
import miniproject.member.dao.Member;
import miniproject.paint.dao.Paint;
import miniproject.palette.dao.Palette;
import miniproject.palette.dto.PaletteDto;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaletteRepositoryImpl implements PaletteRepository {

    private final String paletteDBPath = "C:/temp/pfdb/palette.dat";

    @Override
    public Optional<List<PaletteDto>> findAll(){

        String sql = """
            SELECT
              JSON_OBJECT(
                'palPk' VALUE pal.pal_pk,
                'member' VALUE JSON_OBJECT(
                   'memberPk' VALUE m.member_pk,
                   'memberId' VALUE m.member_id,
                   'nickname' VALUE m.nickname
                 ),
             	'palName' VALUE pal.pal_name,
            	'imgSrc' VALUE pal.img_src,
                'likeCount' VALUE NVL(l.like_count, 0),
                'paintList' VALUE JSON_ARRAYAGG(
                  JSON_OBJECT(
                    'paintPk' VALUE p.paint_pk,
                    'colorEn' VALUE p.color_en
                  )
                )
              ) AS palette_json
            FROM pal_paint pp
            LEFT JOIN palette pal ON pp.pal_idx = pal.pal_pk
            LEFT JOIN member m ON pal.member_idx = m.member_pk
            LEFT JOIN paint p ON pp.paint_idx = p.paint_pk
            LEFT JOIN (
              SELECT pal_idx, COUNT(*) AS like_count
              FROM pal_like
              GROUP BY pal_idx
            ) l ON pal.pal_pk = l.pal_idx
            GROUP BY pal.pal_pk, m.member_pk, m.member_id, m.nickname, pal.pal_name, pal.img_src, l.like_count
        """;

        List<PaletteDto> palettes = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper(); // Jackson 사용

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String json = rs.getString("palette_json");
                PaletteDto dto = mapper.readValue(json, PaletteDto.class);
                palettes.add(dto);
            }

            return Optional.of(palettes);

        } catch (Exception e){
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<PaletteDto> findByPalettePk(int pk) {
        String sql = """
                SELECT
              JSON_OBJECT(
                'palPk' VALUE pal.pal_pk,
                'member' VALUE JSON_OBJECT(
                   'memberPk' VALUE m.member_pk,
                   'memberId' VALUE m.member_id,
                   'nickname' VALUE m.nickname
                 ),
             	'palName' VALUE pal.pal_name,
            	'imgSrc' VALUE pal.img_src,
                'likeCount' VALUE NVL(l.like_count, 0),
                'paintList' VALUE JSON_ARRAYAGG(
                  JSON_OBJECT(
                    'paintPk' VALUE p.paint_pk,
                    'colorEn' VALUE p.color_en
                  )
                )
              ) AS palette_json
            FROM pal_paint pp
            LEFT JOIN palette pal ON pp.pal_idx = pal.pal_pk
            LEFT JOIN member m ON pal.member_idx = m.member_pk
            LEFT JOIN paint p ON pp.paint_idx = p.paint_pk
            LEFT JOIN (
              SELECT pal_idx, COUNT(*) AS like_count
              FROM pal_like
              GROUP BY pal_idx
            ) l ON pal.pal_pk = l.pal_idx
            WHERE pal.pal_pk = ?
            GROUP BY pal.pal_pk, m.member_pk, m.member_id, m.nickname, pal.pal_name, pal.img_src, l.like_count
                """;

        ObjectMapper mapper = new ObjectMapper(); // Jackson 사용

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, pk);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String json = rs.getString("palette_json");
                return Optional.of(mapper.readValue(json, PaletteDto.class));
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void insertOrUpdate(Palette palette) throws Exception{
        File file = new File(paletteDBPath);

        List<Palette> palettes = new ArrayList<>();

        // 기존 객체 읽기
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        Palette p = (Palette) ois.readObject();
                        palettes.add(p);
                    } catch (EOFException e) {
                        break;
                    }
                }
            }
        }

        boolean updated = false;

        for (int i = 0; i < palettes.size(); i++) {
//            if (palette.getId() == palettes.get(i).getId()) {
//                palettes.set(i, palette); // 교체
//                updated = true;
//                break;
//            }
        }
        if (!updated) {
            palettes.add(palette); // 없으면 새로 추가
        }

        // 3. 전체 덮어쓰기
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Palette pal : palettes) {
                oos.writeObject(pal);
            }
        }
    }

    @Override
    public List<Palette> findByMemberLike(Member member) throws Exception {

        List<Palette> palettes = new ArrayList<>();

        File file = new File(paletteDBPath);
        // 파일이 존재하지 않거나 비어 있으면 false 반환
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Palette palette = (Palette) ois.readObject();
//                if(member.getLikes().contains(palette.getId())){
//                    palettes.add(palette);
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }

        return palettes;
    }

    @Override
    public Optional<List<Palette>> findByMemberIdx(int memberIdx) {

        List<Palette> palettes = new ArrayList<>();

        String sql = "SELECT * FROM PALETTE WHERE MEMBER_IDX = ?";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, memberIdx);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {

                palettes.add(new Palette(
                        rs.getInt("PAL_PK"),
                        rs.getInt("MEMBER_IDX"),
                        rs.getString("PAL_NAME"),
                        rs.getString("IMG_SRC"),
                        rs.getObject("CREATE_DATE", LocalDateTime.class),
                        rs.getObject("EDIT_DATE", LocalDateTime.class)
                ));

                return Optional.of(palettes);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int findLastId() throws Exception {
        File file = new File(paletteDBPath);

        int maxId = 0;

        if (!file.exists() || file.length() == 0) {
            return maxId;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Palette palette = (Palette) ois.readObject();
//                if(palette.getId() > maxId){
//                    maxId = palette.getId();
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }

        return maxId;
    }

    @Override
    public Palette findById(int id) throws Exception {
        File file = new File(paletteDBPath);

        Palette palette = null;

        if (!file.exists() || file.length() == 0) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Palette p = (Palette) ois.readObject();
//                if(p.getId() == id){
//                    palette = p;
//                    break;
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }

        return palette;
    }

    @Override
    public Optional<Integer> save(Palette palette) {
        String sql = "BEGIN " + "INSERT INTO PALETTE (MEMBER_IDX, PAL_NAME, IMG_SRC)" +
                "VALUES (?, ?, ?) RETURNING PAL_PK INTO ?; END;";
                // ORACLE 은 INSERT ... RETURNING ... INTO ... 구문을 지원하므로, 생성된 PK나 특정 컬럼 값을 변수로 직접 가져올 수 있다

        try(
                Connection conn = DBConnection.connect();
                CallableStatement stmt = conn.prepareCall(sql)
        ){
            stmt.setInt(1, palette.getMemberIdx());
            stmt.setString(2, palette.getPalName());
            stmt.setString(3, palette.getImgSrc());
            stmt.registerOutParameter(4, Types.INTEGER);

            stmt.executeUpdate();

            return Optional.of(stmt.getInt(4));

        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }
}