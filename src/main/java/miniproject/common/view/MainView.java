package miniproject.common.view;

import miniproject.App;
import miniproject.common.code.StateCode;
import miniproject.global.InputChecker;
import miniproject.global.dto.ResponseDto;
import miniproject.member.dao.Member;
import miniproject.member.dto.MemberDto;
import miniproject.member.service.MemberService;
import miniproject.member.view.MemberView;
import miniproject.paint.view.PaintView;
import miniproject.palette.view.PaletteView;

import java.util.Scanner;

public class MainView {

    private final int EXIT = StateCode.EXIT.getCode();
    private final int LOGIN = StateCode.LOGIN.getCode();
    private final int MAINMENU = StateCode.MAINMENU.getCode();

    private final Scanner sc = new Scanner(System.in);
    private final MemberService memberService = new MemberService();

    /**
     * 로그인 화면으로 보일 view
     */
    public MemberDto loginView(){
        System.out.println("\n=====PaintFinder 로그인 화면=====");
        while (true) {

            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");

            String menu = sc.nextLine();

            int select = InputChecker.validate(menu, 1, 2);

            /**
             * 1. 로그인
             */
            if(select == 1){
                System.out.print("아이디를 입력하세요 : ");
                String id = sc.nextLine();
                System.out.print("비밀번호를 입력하세요 : ");
                String password = sc.nextLine();

                ResponseDto responseDto = memberService.login(id, password);

                if(responseDto.isSuccess()){
                    System.out.println("로그인에 성공했습니다.\n");
                    memberService.saveLatestLogin(App.member.getMemberPk());
                    return new MemberDto((Member) responseDto.getData(), MAINMENU);
                } else {
                    System.out.println(responseDto.getMessage());
                }
            }

            /**
             * 2. 회원가입
             */
            else if(select == 2) {
                System.out.print("사용할 아이디를 입력하세요 : ");
                String id = sc.nextLine();
                System.out.print("사용할 비밀번호를 입력하세요 : ");
                String password = sc.nextLine();

                ResponseDto responseDto = memberService.memberRegister(id, password);
                if(responseDto.isSuccess()){
                    System.out.println("회원가입에 성공했습니다.\n");
                } else {
                    System.out.println(responseDto.getMessage());
                }
            }
        }
    }

    public int mainMenuView(Member member) throws Exception{
        Scanner sc = new Scanner(System.in);

        MemberView memberView = new MemberView();
        PaletteView paletteView = new PaletteView();
        PaintView paintView = new PaintView();

        while(true) {
            System.out.println("\n=====PaintFinder 메인화면=====");
            System.out.println("1. 팔레트(그림) 둘러보기");
            System.out.println("2. 물감 둘러보기");
            System.out.println("3. 내 정보 페이지");
            System.out.println("4. 로그아웃");
            System.out.println("0. 앱 종료하기");

            String inp = sc.nextLine();
            int input = InputChecker.validate(inp, 0, 4);

            // 마이팔레트
            if (input == 1) {
                paletteView.paletteMenuView();
            }
            // 물감
            else if (input == 2) {
                paintView.paintMenuView();
            }
            // 유저 관리
            else if (input == 3) {
                memberView.memberMenuView();
            }
            // 로그아웃
            else if (input == 4) {
                memberService.saveLatestLogout(App.member.getMemberPk());
                App.member = null;
                return LOGIN;
            }
            // 종료
            else if(input == EXIT) {
                memberService.saveLatestLogout(App.member.getMemberPk());
                System.out.println("PaintFinder 앱을 종료합니다.");
                return EXIT;
            }
        }
    }
}
