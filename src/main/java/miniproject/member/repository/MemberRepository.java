package miniproject.member.repository;

import miniproject.member.dao.Member;

import java.util.Optional;

public interface MemberRepository {
    /**
     * 회원 id로 조회
     * @param id
     * @return member
     */
    Optional<Member> findByMemberPk(String id);

    /**
     * 회원가입 시 사용. 회원 id 로 아이디 중복 확인
     * @param id
     * @return Boolean
     */
    boolean existsByMemberId(String id);

    /**
     * 회원가입시 사용. 입력한 id, pw 로 회원 데이터 생성.
     * @param id
     * @param pw
     * @return Boolean
     */
    boolean createMember(String id, String pw);

    /**
     * 멤버 닉네임 변경
     * @param pk
     * @param nickname
     * @return
     */
    boolean updateMemberNickname(int pk, String nickname);

    /**
     * 멤버 비밀번호 변경
     * @param pk
     * @param pw
     * @return
     */
    boolean updateMemberPw(int pk, String pw);

    /**
     * 마지막 로그인 시간 저장
     * @param pk
     * @return
     */
    boolean saveLatestLogin(int pk);

    /**
     * 마지막 로그아웃 시간 저장
     * @param pk
     * @return
     */
    boolean saveLatestLogout(int pk);

    /**
     * 정보 수정 데이터 입력
     * @param pk
     * @param editMemberPk
     * @return
     */
    boolean updateEditInfo(int pk, int editMemberPk);
}
