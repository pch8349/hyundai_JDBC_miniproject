package miniproject.brand.repository;

import miniproject.App;
import miniproject.brand.code.BrandCode;
import miniproject.global.DBConnection;
import miniproject.member.dao.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BrandRepositoryImpl implements BrandRepository {

    private final Member member = App.member;

    @Override
    public boolean registBrand(BrandCode brandCode) {
        String sql = "INSERT INTO BRAND (BRAND_NAME_EN, BRAND_NAME_KR, CREATE_MEMBER_IDX)" +
                "VALUES(?,?,?)";

        try(
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){
            pstmt.setString(1, brandCode.name());
            pstmt.setString(2, brandCode.getKorName());
            pstmt.setInt(3, member.getMemberPk());

            int rs = pstmt.executeUpdate(); // insert, update 실행 시.

            return rs > 0; // 1개 이상 삽입되었으면 true 반환

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
