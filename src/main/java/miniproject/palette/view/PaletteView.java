package miniproject.palette.view;

import miniproject.common.code.StateCode;
import miniproject.global.InputChecker;
import miniproject.palette.service.PaletteService;

import java.util.Scanner;

public class PaletteView {

    private final int EXIT = StateCode.EXIT.getCode();

    private final PaletteService paletteService = new PaletteService();

    public void paletteMenuView(){
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n=====팔레트 페이지=====");
            System.out.println("1. 팔레트 전체조회");
            System.out.println("2. 팔레트 상세조회(이미지 조회)");
            System.out.println("3. 팔레트 좋아요");
            System.out.println("4. 내 팔레트 목록 조회");
            System.out.println("5. 내가 좋아요 한 팔레트 목록 조회");
            System.out.println("6. 팔레트 수정");
            System.out.println("7. 팔레트 신규등록");
            System.out.println("0. 뒤로가기");

            int input = InputChecker.validate(sc.nextLine(), 0, 7);

            if(input == EXIT) {
                break;
            } else if(input == 1) {

                paletteService.findAllPalette();

            } else if(input == 2){

                paletteService.findPaletteDetailById();

            } else if (input == 3){

                paletteService.changePaletteLike();

            } else if (input == 4) {

                paletteService.findMyPalette();

            } else if(input == 5) {

                paletteService.findMyLikePaletteList();

            } else if(input == 6){

                paletteService.editPalette();

            } else if(input == 7){

                paletteService.createPalette();

            }
        }
    }
}
