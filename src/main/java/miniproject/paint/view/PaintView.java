package miniproject.paint.view;

import miniproject.App;
import miniproject.common.code.PaintColorCode;
import miniproject.common.code.StateCode;
import miniproject.global.InputChecker;
import miniproject.global.dto.ResponseDto;
import miniproject.member.dao.Member;
import miniproject.paint.service.PaintService;

import java.util.List;
import java.util.Scanner;

public class PaintView {

    private final int EXIT = StateCode.EXIT.getCode();

    private final Scanner sc = new Scanner(System.in);
    private final Member member = App.member;
    private final PaintService paintService = new PaintService();

    public void paintMenuView(){

        String search = "";
        List<PaintColorCode> codeList = null;

        while(true) {
            System.out.println("\n=====물감 검색=====");
            System.out.println("1. 물감 전체조회");
            System.out.println("2. 물감 이미지 확인");
            System.out.println("3. 물감 이름 검색");
            System.out.println("4. 물감 내 팔레트에 추가하기");
            System.out.println("5. 물감 검색 필터 설정");
            System.out.println("6. 물감 검색 필터 해제");
            if(member.getMemberRoll() == 0) System.out.println("7. 물감 등록");
            System.out.println("0. 뒤로가기");

            String input = sc.nextLine();
            int n = 0;
            if(member.getMemberRoll() == 1) n = InputChecker.validate(input, 0, 6); // 일반 사용자
            else n = InputChecker.validate(input, 0, 7); // 관리자


            if(n == 1) {

                paintService.findAllPaint("", codeList);

            } else if(n == 2){

                System.out.println("\n--------------------------");
                System.out.print("이미지를 확인할 페인트 번호 입력 : ");
                String idxString = sc.nextLine();
                int idx = InputChecker.validate(idxString);

                if(idx == -1) continue;
                paintService.paintImgViewer(idx);

            } else if(n == 3) {

                System.out.println("\n------------------");
                System.out.print("검색할 페인트명 입력 : ");
                search = sc.nextLine();
                paintService.findAllPaint(search, codeList);

            } else if(n==4){

                paintService.addPaintToPalette();

            } else if(n == 5) {

                codeList = paintService.setPaintSearchFilter();

            } else if(n==6){

                codeList = null;
                System.out.println("검색 필터 초기화 완료");

            } else if(n==7 && member.getMemberRoll() == 0) {

                ResponseDto responseDto = paintService.registPaint();
                if(responseDto.isSuccess()) {
                    System.out.println("등록에 성공했습니다.\n");
                } else {
                    System.out.println(responseDto.getMessage());
                }

            } else if(n == EXIT) {

                break;

            }
        }
    }
}
