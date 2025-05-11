package miniproject.member.repository;

import miniproject.App;
import miniproject.global.DBConnection;
import miniproject.member.dao.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository {

    @Override
    public Optional<Member> findByMemberPk(String id) {
        String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
        try (
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ) {

            pstmt.setString(1, id);
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    Member member = new Member(
                            rs.getInt("MEMBER_PK"),
                            rs.getString("MEMBER_ID"),
                            rs.getString("NICKNAME"),
                            rs.getString("PASSWORD"),
                            rs.getInt("MEMBER_ROLE"),
                            rs.getObject("JOIN_DATE", LocalDateTime.class),
                            rs.getObject("EDIT_DATE", LocalDateTime.class),
                            rs.getInt("EDIT_MEMBER_IDX"),
                            rs.getObject("LAST_LOGIN_DATE", LocalDateTime.class),
                            rs.getObject("LAST_LOGOUT_DATE", LocalDateTime.class)
                    );
                    return Optional.of(member);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByMemberId(String id) {
        String sql = "SELECT 1 \n" +
                "FROM MEMBER \n" +
                "WHERE MEMBER_ID = ? \n" +
                "FETCH FIRST 1 ROWS ONLY";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){

            pstmt.setString(1, id);

            try(ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // 값이 있으면 true, 없으면 false
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean createMember(String id, String pw) {
        String sql = "INSERT INTO MEMBER (MEMBER_ID, PASSWORD)" +
                "VALUES(?,?)";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ){
            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMemberNickname(int pk, String nickname) {
        String sql = "UPDATE MEMBER SET NICKNAME = ? WHERE MEMBER_PK = ? ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, nickname);
            pstmt.setInt(2, pk);

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateMemberPw(int pk, String pw) {
        String sql = "UPDATE MEMBER SET PASSWORD = ? WHERE MEMBER_PK = ? ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, pw);
            pstmt.setInt(2, pk);

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveLatestLogin(int pk) {
        String sql = "UPDATE MEMBER SET LAST_LOGIN_DATE = ? WHERE MEMBER_PK = ? ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(2, pk);

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveLatestLogout(int pk) {
        String sql = "UPDATE MEMBER SET LAST_LOGOUT_DATE = ? WHERE MEMBER_PK = ? ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(2, pk);

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEditInfo(int pk, int editMemberPk) {
        String sql = "UPDATE MEMBER SET EDIT_DATE = ?, EDIT_MEMBER_IDX = ? WHERE MEMBER_PK = ? ";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(2, editMemberPk);
            pstmt.setInt(3, pk);

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
