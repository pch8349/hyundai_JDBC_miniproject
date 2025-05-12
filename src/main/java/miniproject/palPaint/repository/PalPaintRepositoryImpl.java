package miniproject.palPaint.repository;

import miniproject.App;
import miniproject.global.DBConnection;
import miniproject.member.dao.Member;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

public class PalPaintRepositoryImpl implements PalPaintRepository {

    private final Member member = App.member;

    @Override
    public boolean save(int palIdx, int paintIdx) {
        String sql = "INSERT INTO PAL_PAINT (PAL_IDX, PAINT_IDX)" +
                "VALUES(?,?) ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareCall(sql)
        ){
            pstmt.setInt(1, palIdx);
            pstmt.setInt(2, paintIdx);

            int rs = pstmt.executeUpdate();

            return rs > 0;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
