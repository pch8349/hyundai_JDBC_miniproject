package miniproject.member.view;

import miniproject.App;
import miniproject.common.code.MessageCode;
import miniproject.common.code.StateCode;
import miniproject.global.InputChecker;
import miniproject.global.dto.ResponseDto;
import miniproject.member.dao.Member;
import miniproject.member.service.MemberService;

import java.util.Scanner;

public class MemberView {

    private final String WRONGMESSAGE = MessageCode.WRONG.getMessage();
    private final int EXIT = StateCode.EXIT.getCode();

    Member member = App.member;

    private final MemberService memberService = new MemberService();

    public void memberMenuView(){

        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("\n=====PaintFinder 내 정보 관리=====");
            System.out.println("1. 내 정보 확인");
            System.out.println("2. 닉네임 변경");
            System.out.println("3. 비밀번호 변경");
            System.out.println("0. 뒤로가기");

            int menuNum = InputChecker.validate(sc.nextLine(), 0, 3);

            if(menuNum == 1){

                System.out.println("아이디 : " + member.getMemberId() +"\n닉네임 : " + member.getNickname());

            } else if(menuNum == 2){
                System.out.print("기존 비밀번호를 입력하세요 : ");
                String oldPassword = sc.nextLine();
                ResponseDto passwordResponse = memberService.checkPassword(oldPassword);
                if(passwordResponse.isSuccess()){
                    System.out.print("닉네임을 입력하세요 : ");
                    String nickname = sc.nextLine();

                    ResponseDto responseDto = memberService.editNickname(member.getMemberPk(), nickname);
                    if(responseDto.isSuccess()){
                        System.out.println("닉네임 변경에 성공했습니다.\n");

                        App.member = (Member) memberService.login(member.getMemberId(), App.member.getPassword()).getData();

                        member = App.member;
                    } else {
                        System.out.println(responseDto.getMessage());
                    }
                } else {
                    System.out.println(passwordResponse.getMessage());
                }

            } else if(menuNum == 3){

                System.out.print("기존 비밀번호를 입력하세요 : ");
                String oldPassword = sc.nextLine();
                ResponseDto passwordResponse = memberService.checkPassword(oldPassword);
                if(passwordResponse.isSuccess()){
                    System.out.print("비밀번호를 입력하세요 : ");
                    String newPassword = sc.nextLine();

                    ResponseDto responseDto = memberService.editPassword(member.getMemberPk(), newPassword);
                    if(responseDto.isSuccess()){
                        System.out.println("비밀번호 변경에 성공했습니다.\n");

                        App.member = (Member) memberService.login(member.getMemberId(), newPassword).getData();
                        member = App.member;
                    } else {
                        System.out.println(responseDto.getMessage());
                    }
                } else {
                    System.out.println(passwordResponse.getMessage());
                }

            } else if(menuNum == EXIT){
                return;
            }
        }
    }
}
