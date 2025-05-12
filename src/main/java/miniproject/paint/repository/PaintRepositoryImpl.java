package miniproject.paint.repository;

import miniproject.App;
import miniproject.brand.code.BrandCode;
import miniproject.common.code.PaintColorCode;
import miniproject.global.DBConnection;
import miniproject.member.dao.Member;
import miniproject.paint.dao.Paint;
import miniproject.paint.dto.PaintDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaintRepositoryImpl implements PaintRepository {

    private final String paintDBPath = "C:/temp/pfdb/paint.dat";

    private final Member member = App.member;

    @Override
    public Optional<List<PaintDto>> findAll(String search, List<PaintColorCode> codeList){
        String sql = "SELECT p.PAINT_PK, p.BRAND_IDX, p.COLOR_EN, p.COLORGROUP_EN, p.PROD_IMG "
                + "FROM PAINT p LEFT OUTER JOIN BRAND b ON (p.BRAND_IDX = b.BRAND_PK) ";

        if(search != null && !search.isEmpty()) {
            sql += "WHERE LOWER(b.BRAND_NAME_EN) LIKE LOWER(?) OR LOWER(p.COLOR_EN) LIKE LOWER(?) ";
        }

        if(codeList != null && !codeList.isEmpty()) {
            if(!sql.contains("WHERE")) sql += "WHERE ";
            else sql += "AND ";
            sql += "( ";
            for(int i = 0; i<codeList.size(); i++) {
                sql += " p.COLORGROUP_EN = ? ";
                if(i < codeList.size() - 1) sql += " OR ";
            }
            sql += ")";
        }

        try (
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {
            int i = 1;
            if(search != null && !search.isEmpty()) {
                pstmt.setString(i++, "%" + search + "%");
                pstmt.setString(i++, "%" + search + "%");
            }
            if(codeList != null && !codeList.isEmpty()) {
                for(PaintColorCode code : codeList) {
                    pstmt.setString(i++, code.toString());
                }
            }

            try(ResultSet rs = pstmt.executeQuery()) {
                List<PaintDto> paintDtos = new ArrayList<>();
                while(rs.next()) {
                    paintDtos.add(new PaintDto(
                            rs.getInt("PAINT_PK"),
                            BrandCode.fromId(rs.getInt("BRAND_IDX")),
                            rs.getString("COLOR_EN"),
                            PaintColorCode.fromString(rs.getString("COLORGROUP_EN")),
                            rs.getString("PROD_IMG")
                    ));
                }
                return Optional.of(paintDtos);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Paint> findById(int id){
        String sql = "SELECT * FROM PAINT WHERE PAINT_PK = ?";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {

                Paint paint = new Paint(
                        rs.getInt("PAINT_PK"),
                        rs.getInt("BRAND_IDX"),
                        rs.getString("COLOR_EN"),
                        PaintColorCode.fromString(rs.getString("COLORGROUP_EN")),
                        rs.getString("PROD_IMG"),
                        rs.getObject("CREATE_DATE", LocalDateTime.class),
                        rs.getInt("CREATE_MEMBER_IDX"),
                        rs.getObject("EDIT_DATE", LocalDateTime.class),
                        rs.getInt("EDIT_MEMBER_IDX")
                );

                return Optional.of(paint);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean registPaint(Paint paint) {
        String sql = "INSERT INTO PAINT (BRAND_IDX, COLOR_EN, COLORGROUP_EN, PROD_IMG, CREATE_MEMBER_IDX)" +
                "VALUES(?,?,?,?,?)";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, paint.getBrandIdx());
            pstmt.setString(2, paint.getColorEn());
            pstmt.setString(3, paint.getColorgroupEn().toString());
            pstmt.setString(4, paint.getProdImg());
            pstmt.setInt(5, paint.getCreateMemberIdx());

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isPaintExist(int id) {
        String sql = "SELECT 1 FROM PAINT WHERE PAINT_PK = ?";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, id);

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
