package miniproject.paint.service;

import miniproject.App;
import miniproject.brand.code.BrandCode;
import miniproject.common.code.PaintColorCode;
import miniproject.common.code.StateCode;
import miniproject.common.util.MoveAndCleanAsset;
import miniproject.global.ImgViewer;
import miniproject.global.InputChecker;
import miniproject.global.dto.ResponseDto;
import miniproject.member.dao.Member;
import miniproject.paint.dao.Paint;
import miniproject.paint.dto.PaintDto;
import miniproject.paint.repository.PaintRepository;
import miniproject.paint.repository.PaintRepositoryImpl;
import miniproject.palPaint.dao.PalPaint;
import miniproject.palPaint.repository.PalPaintRepository;
import miniproject.palPaint.repository.PalPaintRepositoryImpl;
import miniproject.palette.dao.Palette;
import miniproject.palette.repository.PaletteRepository;
import miniproject.palette.repository.PaletteRepositoryImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PaintService {
    private final int EXIT = StateCode.EXIT.getCode();

    private final PaintRepository paintRepository = new PaintRepositoryImpl();
    private final PaletteRepository paletteRepository = new PaletteRepositoryImpl();
    private final MoveAndCleanAsset moveAndCleanAsset = new MoveAndCleanAsset();
    private final PalPaintRepository palPaintRepository = new PalPaintRepositoryImpl();

    private final Member member = App.member;

    Scanner sc = new Scanner(System.in);

    /**
     * 페인트 검색어, 색 조건에 맞는 결과 모두 출력
     * @param search
     * @param codeList
     * @throws Exception
     */
    public void findAllPaint(String search, List<PaintColorCode> codeList){

        List<PaintDto> paints = paintRepository.findAll(search, codeList).orElse(Collections.emptyList());

        System.out.println("\n-----------물감 검색------------");
        paints.stream().forEach(PaintDto::printInfo);
        System.out.println();
    }

    /**
     * 페인트 검색 필터 설정
     * @return
     */
    public List<PaintColorCode> setPaintSearchFilter(){
        PaintColorCode[] codeArr = {PaintColorCode.RED, PaintColorCode.ORANGE, PaintColorCode.YELLOW, PaintColorCode.GREEN, PaintColorCode.BLUE, PaintColorCode.NAVY, PaintColorCode.PURPLE, PaintColorCode.PINK, PaintColorCode.WHITE, PaintColorCode.BLACK, PaintColorCode.GRAY, PaintColorCode.BROWN, PaintColorCode.IVORY};
        List<PaintColorCode> codeList = new ArrayList<PaintColorCode>();


        while(true) {
            System.out.println("\n-----------------");
            System.out.println("검색하고 싶은 색상을 선택하십시오.");
            PaintColorCode.printInfo();
            System.out.println("0. 뒤로가기");
            int input = InputChecker.validate(sc.nextLine(), 0, PaintColorCode.getAllColors().size());


            if(input == EXIT) break;
            else if(input >= 0 && input <= PaintColorCode.getAllColors().size()) {
                codeList.add(PaintColorCode.getAllColors().get(input-1));
            }

            System.out.println("현재 적용된 필터 목록");
            codeList.stream().map(PaintColorCode::toString).forEach(System.out::println);
        }

        return codeList;
    }

    public ResponseDto registPaint(){
        System.out.println("\n\n--------새로운 페인트 입력-------");

        System.out.println("브랜드 번호를 선택하세요");
        List<BrandCode> brandCodes = BrandCode.getAllBrandCodes();
        for(int i = 0; i<brandCodes.size(); i++){
            System.out.println((i+1)+". "+brandCodes.get(i).name());
        }
        int brandIdx = InputChecker.validate(sc.nextLine(), 1, brandCodes.size());

        System.out.print("색상명을 입력하세요 : ");
        String colorEn = sc.nextLine();

        System.out.println("색상 그룹을 선택하세요.");
        List<PaintColorCode> paintColorCodes = PaintColorCode.getAllColors();
        for(int i = 0; i<paintColorCodes.size(); i++){
            System.out.println((i+1)+". "+paintColorCodes.get(i).name());
        }
        int colorgroupInput = InputChecker.validate(sc.nextLine(), 1, paintColorCodes.size());
        PaintColorCode colorgroupEn = paintColorCodes.get(colorgroupInput-1);

        System.out.println("등록할 팔레트 이미지를 asset 폴더 안에 넣어주십시오.");
        System.out.println("jpg, png 형식만 가능합니다.");
        System.out.println("이미지를 폴더에 넣으셨다면 enter, 생략하려면 q 를 입력해주세요.");

        String imgInput = sc.nextLine();
        String prodImg = "";
        if(imgInput.equals("q")){
            prodImg = "";
        }else{
            prodImg = moveAndCleanAsset.saveImg(0);
        }

        Paint paint = new Paint(brandIdx, colorEn, colorgroupEn, prodImg, member.getMemberPk());

        boolean isSuccess = paintRepository.registPaint(paint);
        if(isSuccess){
            return ResponseDto.success();
        } else {
            return ResponseDto.fail("알 수 없는 이유로 페인트 등록에 실패했습니다.\n");
        }
    }

    /**
     * 페인트 ID 를 받아 내 팔레트에 추가하기
     * @throws Exception
     */
    public void addPaintToPalette() {

        Paint paint = null;
        Palette palette = null;

        while(true) {
            System.out.println("추가할 페인트 번호 입력하세요. 0 입력 시 종료");
            int input = InputChecker.validate(sc.nextLine());
            if(input ==0){
                System.out.println("입력 취소");
                return;
            } else if(input == -1){
                continue;
            }

            paint = paintRepository.findById(input).orElse(null);

            if (paint == null) {
                System.out.println("그런 페인트 id 는 존재하지 않습니다.");
            } else {
                break;
            }
        }

        List<Palette> palettes = paletteRepository.findByMemberIdx(member.getMemberPk()).orElse(Collections.emptyList());
        if(palettes.isEmpty()) {
            System.out.println("팔레트가 없습니다.");
            return;
        }
        System.out.println("내 팔레트 목록");
        palettes.forEach(Palette::printInfo);

        while(true) {
            System.out.println("\n페인트를 추가할 팔레트 번호를 입력해주세요. 0 입력 시 종료");
            int input = InputChecker.validate(sc.nextLine());
            if(input == 0){
                System.out.println("입력 취소");
                return;
            } else if (input == -1) {
                continue;
            }  else {
                // 페인트 넣기
                if(palPaintRepository.save(new PalPaint(input, paint.getPaintPk()))){
                    System.out.println("페인트 내 팔레트에 등록 성공\n");
                } else {
                    System.out.println("알 수 없는 이유로 페인트를 내 팔레트에 등록하기 실패\n");
                }
                break;
            }
        }
    }

    public void paintImgViewer(int idx){
        Paint paint = paintRepository.findById(idx).orElse(null);

        if(paint != null) {
            ImgViewer.viewer(paint.getProdImg());
        } else {
            System.out.println("해당하는 팔레트 번호가 없습니다.");
        }
    }
}
