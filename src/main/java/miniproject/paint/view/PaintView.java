package miniproject.paint.view;

import miniproject.App;
import miniproject.common.code.PaintColorCode;
import miniproject.common.code.StateCode;
import miniproject.global.InputChecker;
import miniproject.member.dao.Member;
import miniproject.paint.service.PaintService;

import java.util.List;
import java.util.Scanner;

public class PaintView {

    private final int EXIT = StateCode.EXIT.getCode();

    private final Scanner sc = new Scanner(System.in);
    private final Member member = App.member;
    private final PaintService paintService = new PaintService();

    public void paintMenuView() throws Exception{
        // TODO : 페인트 등록부터 시작 필요.

        String search = "";
        List<PaintColorCode> codeList = null;

        while(true) {
            System.out.println("\n=====물감 검색=====");
            System.out.println("1. 물감 전체조회");
            System.out.println("2. 물감 이름 검색");
            System.out.println("3. 물감 내 팔레트에 추가하기");
            System.out.println("4. 물감 검색 필터 설정");
            System.out.println("5. 물감 검색 필터 해제");
            if(member.getMemberRoll() == 0) System.out.println("6. 물감 등록");
            System.out.println("0. 뒤로가기");

            String input = sc.nextLine();
            if(member.getMemberRoll() == 1)InputChecker.validate(input, 0, 5); // 일반 사용자
            else InputChecker.validate(input, 0, 6); // 관리자

            int n = Integer.parseInt(sc.nextLine());

            if(n == 1) {

                paintService.findAllPaint("", codeList);

            } else if(n == 2) {

                System.out.println("\n------------------");
                System.out.print("검색할 페인트명 입력 : ");
                search = sc.nextLine();
                paintService.findAllPaint(search, codeList);

            } else if(n==3){

                paintService.addPaintToPalette(member);

            } else if(n == 4) {

                codeList = paintService.setPaintSearchFilter();

            } else if(n==5){

                codeList = null;
                System.out.println("검색 필터 초기화 완료");

            } else if(n==6 && member.getMemberRoll() == 0) {

                // 더미데이터 추가
                paintService.paintDataWriter();

            } else if(n == EXIT) {

                break;

            }
        }
    }
}
