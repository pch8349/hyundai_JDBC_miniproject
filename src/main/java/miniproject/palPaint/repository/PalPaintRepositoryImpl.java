package miniproject.palPaint.repository;

import miniproject.App;
import miniproject.brand.code.BrandCode;
import miniproject.common.code.PaintColorCode;
import miniproject.global.DBConnection;
import miniproject.member.dao.Member;
import miniproject.paint.dao.Paint;
import miniproject.paint.dto.PaintDto;
import miniproject.palPaint.dao.PalPaint;
import miniproject.palPaint.dto.PalPaintDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PalPaintRepositoryImpl implements PalPaintRepository {

    private final Member member = App.member;

    @Override
    public boolean save(PalPaint palPaint) {
        String sql = "INSERT INTO PAL_PAINT (PAL_IDX, PAINT_IDX) " +
                "VALUES(?,?) ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){
            pstmt.setInt(1, palPaint.getPalIdx());
            pstmt.setInt(2, palPaint.getPaintIdx());

            int rs = pstmt.executeUpdate();

            return rs > 0;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByPalPaintPk(int palPaintPk) {
        String sql = "DELETE FROM PAL_PAINT " +
                "WHERE PAL_PAINT_PK = ? ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){
            pstmt.setInt(1, palPaintPk);

            int rs = pstmt.executeUpdate();

            return rs > 0;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<List<PalPaintDto>> findByPalIdx(int palIdx) {
        String sql = """
                SELECT pp.PAL_PAINT_PK, pp.PAL_IDX, pp.PAINT_IDX, p.COLOR_EN, p.COLORGROUP_EN, p.BRAND_IDX
                FROM PAL_PAINT pp LEFT OUTER JOIN paint p ON (pp.PAINT_IDX = p.PAINT_PK)
                WHERE pp.PAL_IDX = ?
                """;

        List<PalPaintDto> palPaintDtos = new ArrayList<>();

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, palIdx);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                boolean add = palPaintDtos.add(new PalPaintDto(
                        rs.getInt("PAL_PAINT_PK"),
                        rs.getInt("PAL_IDX"),
                        rs.getInt("PAINT_IDX"),
                        rs.getString("COLOR_EN"),
                        PaintColorCode.fromString(rs.getString("COLORGROUP_EN")),
                        BrandCode.fromId(rs.getInt("BRAND_IDX"))
                ));
            }

            return Optional.of(palPaintDtos);

        } catch (Exception e){
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
