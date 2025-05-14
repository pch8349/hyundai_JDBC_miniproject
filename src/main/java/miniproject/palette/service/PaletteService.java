package miniproject.palette.service;

import miniproject.App;
import miniproject.common.code.MessageCode;
import miniproject.common.code.StateCode;
import miniproject.common.util.MoveAndCleanAsset;
import miniproject.global.ImgViewer;
import miniproject.global.InputChecker;
import miniproject.member.dao.Member;
import miniproject.member.repository.MemberRepository;
import miniproject.member.repository.MemberRepositoryImpl;
import miniproject.paint.dao.Paint;
import miniproject.paint.repository.PaintRepository;
import miniproject.paint.repository.PaintRepositoryImpl;
import miniproject.palLike.dao.PalLike;
import miniproject.palLike.repository.PalLikeRepository;
import miniproject.palLike.repository.PalLikeRepositoryImpl;
import miniproject.palPaint.dao.PalPaint;
import miniproject.palPaint.dto.PalPaintDto;
import miniproject.palPaint.repository.PalPaintRepository;
import miniproject.palPaint.repository.PalPaintRepositoryImpl;
import miniproject.palette.dao.Palette;
import miniproject.palette.dto.PaletteDto;
import miniproject.palette.repository.PaletteRepository;
import miniproject.palette.repository.PaletteRepositoryImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PaletteService {
    private final String WRONGMESSAGE = MessageCode.WRONG.getMessage();


    private final PaletteRepository paletteRepository = new PaletteRepositoryImpl();
    private final PaintRepository paintRepository = new PaintRepositoryImpl();
    private final PalPaintRepository palPaintRepository = new PalPaintRepositoryImpl();
    private final PalLikeRepository palLikeRepository = new PalLikeRepositoryImpl();
    private final MoveAndCleanAsset moveAndCleanAsset = new MoveAndCleanAsset();

    private final Scanner sc = new Scanner(System.in);
    private final Member member = App.member;

    /**
     * 팔레트 전체 목록 조회 서비스
     * @throws Exception
     */
    public void findAllPalette(){

        List<PaletteDto> palettes = paletteRepository.findAll().orElse(Collections.emptyList());

        if(palettes.isEmpty()) {
            System.out.println("등록된 팔레트가 없습니다.");
            return;
        }

        palettes.forEach(PaletteDto::printInfo);

    }

    /**
     * 팔레트 좋아요/좋아요 취소 로직
     */
    public void changePaletteLike(){

        System.out.print("좋아요 할 팔레트 id 입력 : ");

        int id = InputChecker.validate(sc.nextLine());

        if(id == -1){
            return;
        }

        // 모든 팔레트 목록 중, 입력한 id 에 해당하는 팔레트가 있는지 확인
        boolean isExist = paletteRepository.isExistByPk(id);
        PalLike palLike = new PalLike(member.getMemberPk(), id);

        // 만약 입력한 팔레트가 존재하면
        if(isExist){
            // 좋아요가 이미 존재하면
            if(palLikeRepository.isExistByPalLike(palLike)){
                if(palLikeRepository.deleteByMemberIdxAndPalIdx(palLike)) {
                    System.out.println(id + "번 팔레트 좋아요를 취소했습니다. 💔\n");
                } else {
                    System.out.println(id + "번 팔레트 좋아요를 취소에 실패했습니다.\n");
                }
            } else { // 좋아요가 없으면 신규등록
                if(palLikeRepository.save(palLike)) {
                    System.out.println(id + "번 팔레트에 좋아요를 눌렀습니다. 💖\n");
                } else {
                    System.out.println(id + "번 팔레트에 좋아요 누르기에 실패했습니다.\n");
                }
            }

        } else {
            System.out.println("잘못된 팔레트 번호 입력입니다.\n");
        }

    }

    /**
     * 내 팔레트 목록 조회
     */
    public void findMyPalette(){

        System.out.println("-----내 팔레트 목록-----");

        List<PaletteDto> paletteDtos = paletteRepository.findByMemberPk(member.getMemberPk()).orElse(Collections.emptyList());
        paletteDtos.forEach(PaletteDto::printInfo);

        System.out.println("-----내 팔레트 목록 조회 종료-----");
    }

    /**
     * 로그인한 member 가 좋아요 누른 팔레트 목록 조회
     */
    public void findMyLikePaletteList() {

        System.out.println("-----내가 좋아요 누른 팔레트 목록-----");

        List<PaletteDto> paletteDtos = paletteRepository.findByMemberLike(member.getMemberPk()).orElse(Collections.emptyList());
        paletteDtos.forEach(PaletteDto::printInfo);

        System.out.println("-----내가 좋아요 누른 팔레트 목록 조회 종료-----");
    }

    /**
     * 팔레트 수정
     */
    public void editPalette() {
        Scanner sc = new Scanner(System.in);

        System.out.println("-----팔레트 수정 페이지-----");

        while (true) {

            System.out.println("내 팔레트 목록");
            List<PaletteDto> paletteDtos = paletteRepository.findByMemberPk(member.getMemberPk()).orElse(Collections.emptyList());
            paletteDtos.forEach(PaletteDto::printInfo);

            System.out.println("수정할 내 팔레트 번호를 입력하세요. 취소하려면 0 를 입력하세요.");
            int input = InputChecker.validate(sc.nextLine());

            if (input == 0) break;

            Palette palette = paletteRepository.findByPalettePkAndMemberIdx(input, member.getMemberPk()).orElse(null);

            if (palette == null) {
                System.out.println(WRONGMESSAGE);
                continue;
            }

            // 팔레트 정보 수정
            System.out.println("변경할 정보의 번호를 입력하세요.");
            System.out.println("1. 이름, 2. imgUrl, 3. 페인트 목록, 0. 뒤로가기");
            int choice = InputChecker.validate(sc.nextLine(), 0, 3);
            if (choice == -1) {
                continue;
            } else if (choice == 0) {
                break;
            } else if (choice == 1) {
                System.out.println("새로운 이름을 입력하세요.");
                System.out.println("이름 변경을 취소하려면 q 를 입력하세요");
                String nameInput = sc.nextLine();

                if (nameInput.equals("q")) {
                    break;
                } else {
                    palette.setPalName(nameInput);

                    if (paletteRepository.updatePalette(palette)) {
                        System.out.println("이름 변경이 완료되었습니다.\n");
                    } else {
                        System.out.println("팔레트 이름 변경에 실패했습니다.\n");
                    }
                }
            } else if (choice == 2) {
                System.out.println("새로운 이미지를 src/asset 에 업로드한 후 엔터를 누르세요.");
                System.out.println("이미지 변경을 취소하려면 q 를 입력하세요");
                String imgInput = sc.nextLine();

                if (imgInput.equals("q")) {
                    break;
                } else {
                    String imgUrl = moveAndCleanAsset.saveImg(1);
                    palette.setImgSrc(imgUrl);
                    if (paletteRepository.updatePalette(palette)) {
                        System.out.println("그림 변경이 완료되었습니다.\n");
                    } else {
                        System.out.println("팔레트 그림 변경에 실패했습니다.\n");
                    }
                }
            } else if (choice == 3) {
                System.out.println("1. 페인트 삭제, 2. 페인트 추가, 3. 뒤로가기");
                int paintMenuInput = InputChecker.validate(sc.nextLine(), 1, 3);

                if (paintMenuInput == -1) {
                    continue;
                } else if (paintMenuInput == 1) {

                    // 팔레트에 저장된 페인트 목록 출력
                    List<PalPaintDto> palPaintDtos = palPaintRepository.findByPalIdx(input).orElse(Collections.emptyList());
                    System.out.println(input+"번 팔레트의 물감 목록");
                    palPaintDtos.forEach(PalPaintDto::printInfo);

                    // 삭제할 페인트 번호 입력받기
                    System.out.print("삭제할 페인트 번호(맨앞의 숫자) 입력 : ");
                    int delPaintInput = InputChecker.validate(sc.nextLine());
                    if (delPaintInput == -1) {
                        continue;
                    } else {

                        // 삭제할 페인트 번호 입력 확인
                        boolean isInList = palPaintDtos.stream().anyMatch(dto -> dto.getPalPaintPk() == delPaintInput);
                        if(isInList){
                            // 페인트 삭제
                            if(palPaintRepository.deleteByPalPaintPk(delPaintInput)){
                                System.out.println("삭제 성공");
                            } else {
                                System.out.println("삭제 실패");
                            }

                        } else {
                            System.out.println("그런 번호는 없습니다.");
                        }
                    }
                } else if(paintMenuInput == 2){
                    System.out.print("추가할 페인트 번호를 입력하세요 : ");
                    int newPaintInput =InputChecker.validate(sc.nextLine());
                    if (newPaintInput == -1) continue;
                    Paint paint = paintRepository.findById(newPaintInput).orElse(null);

                    if(paint == null) System.out.println("그런 페인트는 없습니다.");
                    else {
                        palPaintRepository.save(new PalPaint(input, paint.getPaintPk()));
                    }

                } else if(paintMenuInput == 3){

                }

            }

        }
    }

    /**
     * 팔레트 신규 저장
     */
    public void createPalette() {
        System.out.println("-----팔레트 신규 등록-----");
        System.out.print("팔레트 이름 입력 : ");
        String name = sc.nextLine();

        System.out.println("등록할 팔레트 이미지를 asset 폴더 안에 넣어주십시오.");
        System.out.println("jpg, png 형식만 가능합니다.");
        System.out.println("이미지를 폴더에 넣으셨다면 enter, 생략하려면 q 를 입력해주세요.");

        String imgInput = sc.nextLine();
        String imgUrl = "";
        if(imgInput.equals("q")){
            imgUrl = "";
        }else{
            imgUrl = moveAndCleanAsset.saveImg(1);
        }

        System.out.println("팔레트에 사용된 페인트 목록 입력 (0 입력 시 물감 입력 종료)");
        List<Integer> paintList = new ArrayList<>();

        while (true){

            int input = InputChecker.validate(sc.nextLine());
            if(input == -1) {
                System.out.println("잘못된 페인트 번호 입력입니다. 다시 입력해주세요.");
            } else if(input == 0){
                break;
            } else {
                if(paintRepository.isPaintExist(input)){
                    paintList.add(input);
                }
            }
        }

        Palette palette = new Palette(member.getMemberPk(), name, imgUrl);

        // 팔레트 저장
        Integer palNum = paletteRepository.save(palette).orElse(null);
        if(palNum == null) {
            System.out.println("팔레트 저장 실패.\n");
        } else {
            // 팔레트 내 페인트 목록 저장
            for(int paintIdx : paintList) {
                if(!palPaintRepository.save(new PalPaint(palNum, paintIdx))){
                    System.out.println(paintIdx+"번 물감 저장 실패");
                }
            }
        }

        System.out.println("-----팔레트 "+name+" 저장 완료-----");
    }

    /**
     * 팔레트 상세조회(사진 앱 오픈됨)
     */
    public void findPaletteDetailById() {

        System.out.println("조회할 팔레트 번호를 입력하세요.");
        int id = InputChecker.validate(sc.nextLine());

        if(id == -1) {
            return;
        }

        PaletteDto palette = paletteRepository.findPaletteDtoByPalettePk(id).orElse(null);

        if(palette == null) {
            System.out.println("팔레트 ID "+id+" 가 존재하지 않습니다.");
            return;
        } else if (palette.getImgSrc().isEmpty()){
            System.out.println("이미지가 존재하지 않습니다.\n");
        } else {
            ImgViewer.viewer(palette.getImgSrc());
        }

        palette.printInfo();
    }
}
