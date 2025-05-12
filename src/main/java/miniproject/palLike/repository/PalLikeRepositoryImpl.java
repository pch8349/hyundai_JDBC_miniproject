package miniproject.palLike.repository;

import miniproject.global.DBConnection;
import miniproject.palLike.dao.PalLike;

import java.sql.*;

public class PalLikeRepositoryImpl implements PalLikeRepository{
    @Override
    public boolean deleteByMemberIdxAndPalIdx(PalLike palLike) {
        String sql = "DELETE FROM PAL_LIKE WHERE MEMBER_IDX=? AND PAL_IDX=?";
        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)
                ) {

            pstmt.setInt(1, palLike.getMemberIdx());
            pstmt.setInt(2, palLike.getPalIdx());

            int affected = pstmt.executeUpdate();
            return affected > 0;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean save(PalLike palLike) {
        String sql = "INSERT INTO PAL_LIKE (MEMBER_IDX, PAL_IDX)" +
                "VALUES (?, ?)";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ){
            pstmt.setInt(1, palLike.getMemberIdx());
            pstmt.setInt(2, palLike.getPalIdx());

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExistByPalLike(PalLike palLike) {
        String sql = "SELECT 1 FROM PAL_LIKE WHERE MEMBER_IDX = ? AND PAL_IDX = ?";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setInt(1, palLike.getMemberIdx());
            pstmt.setInt(2, palLike.getPalIdx());

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
