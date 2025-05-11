package miniproject.member.service;

import miniproject.App;
import miniproject.global.dto.ResponseDto;
import miniproject.member.dao.Member;
import miniproject.member.repository.MemberRepository;
import miniproject.member.repository.MemberRepositoryImpl;

public class MemberService {

    private final MemberRepository memberRepository = new MemberRepositoryImpl();

    /**
     * 아이디로 회원 유무 판별 서비스
     * @param id
     * @return member
     */
    public ResponseDto login(String id, String pw){

        Member member = memberRepository.findByMemberPk(id).orElse(null);

        if(member != null){
            if(member.getPassword().equals(pw)){
                App.member = member;
                return ResponseDto.success(member);
            } else {
                return ResponseDto.fail("비밀번호가 올바르지 않습니다.\n");
            }
        } else {
            return ResponseDto.fail("사용자가 존재하지 않습니다.\n");
        }
    }

    /**
     * 신규 회원 등록 서비스
     * @param id
     * @param pw
     * @return success or fail
     */
    public ResponseDto memberRegister(String id, String pw){

        if(id == null || id.trim().isEmpty() || id.length() > 20){
            return ResponseDto.fail("아이디 형식이 올바르지 않습니다.\n");
        } else if (pw == null || pw.trim().isEmpty() || pw.length() > 20){
            return ResponseDto.fail("비밀번호 형식이 올바르지 않습니다.\n");
        } else {

            if(memberRepository.existsByMemberId(id)){
                return ResponseDto.fail("사용중인 아이디입니다.\n");
            } else {

                boolean isSuccess = memberRepository.createMember(id,pw);

                if(isSuccess){
                    return ResponseDto.success();
                } else {
                    return ResponseDto.fail("알 수 없는 문제로 회원가입 실패\n");
                }
            }
        }
    }

    /**
     * 비밀번호 체크 메서드
     * @param password
     * @return
     */
    public ResponseDto checkPassword(String password){
        if (App.member == null) {
            return ResponseDto.fail("로그인된 사용자가 없습니다.\n");
        }
        if(App.member.getPassword().equals(password)){
            return ResponseDto.success();
        } else {
            return ResponseDto.fail("비밀번호가 일치하지 않습니다.\n");
        }
    }

    /**
     * 닉네임 변경 메소드
     * @param pk
     * @param nickname
     * @return
     */
    public ResponseDto editNickname(int pk, String nickname){
        if(nickname == null || nickname.trim().isEmpty() || nickname.length() > 20){
            return ResponseDto.fail("닉네임 형식이 올바르지 않습니다.\n");
        } else {
            boolean isSuccess = memberRepository.updateMemberNickname(pk,nickname);

            if(isSuccess){
                memberRepository.updateEditInfo(pk, App.member.getMemberPk());
                return ResponseDto.success();
            } else {
                return ResponseDto.fail("알 수 없는 문제로 닉네임 변경 실패\n");
            }
        }
    }

    /**
     * 비밀번호 변경 메소드
     * @param pk
     * @param pw
     * @return
     */
    public ResponseDto editPassword(int pk, String pw){
        if(pw == null || pw.trim().isEmpty() || pw.length() > 20){
            return ResponseDto.fail("비밀번호 형식이 올바르지 않습니다.\n");
        } else {
            boolean isSuccess = memberRepository.updateMemberPw(pk,pw);

            if(isSuccess){
                memberRepository.updateEditInfo(pk, App.member.getMemberPk());
                return ResponseDto.success();
            } else {
                return ResponseDto.fail("알 수 없는 문제로 비밀번호 변경 실패\n");
            }

        }
    }

    /**
     * 최근 로그인 시간 저장 메소드
     */
    public ResponseDto saveLatestLogin(int pk){
        boolean isSuccess = memberRepository.saveLatestLogin(pk);
        if(isSuccess){
            return ResponseDto.success();
        } else {
            return ResponseDto.fail("알 수 없는 이유로 마지막 로그인 시간 갱신 실패");
        }
    }

    /**
     * 최근 로그아웃 시간 저장 메소드
     */
    public ResponseDto saveLatestLogout(int pk){
        boolean isSuccess = memberRepository.saveLatestLogout(pk);
        if(isSuccess){
            return ResponseDto.success();
        } else {
            return ResponseDto.fail("알 수 없는 이유로 마지막 로그인 시간 갱신 실패");
        }
    }
}
