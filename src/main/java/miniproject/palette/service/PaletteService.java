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
import miniproject.palPaint.repository.PalPaintRepository;
import miniproject.palPaint.repository.PalPaintRepositoryImpl;
import miniproject.palette.dao.Palette;
import miniproject.palette.dto.PaletteDto;
import miniproject.palette.repository.PaletteRepository;
import miniproject.palette.repository.PaletteRepositoryImpl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PaletteService {
    private final String WRONGMESSAGE = MessageCode.WRONG.getMessage();

    private final String paletteImgPath = "C:/temp/images";

    private final PaletteRepository paletteRepository = new PaletteRepositoryImpl();
    private final MemberRepository memberRepository = new MemberRepositoryImpl();
    private final PaintRepository paintRepository = new PaintRepositoryImpl();
    private final PalPaintRepository palPaintRepository = new PalPaintRepositoryImpl();
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
     * @param member
     * @throws Exception
     */
    public void changePaletteLike(Member member) throws Exception{

        Scanner sc = new Scanner(System.in);

        System.out.print("좋아요 할 팔레트 id 입력");
        int id = Integer.parseInt(sc.nextLine());

//        List<Palette> palettes = paletteRepository.findAll();
//
//        if(palettes == null || palettes.size() == 0) {
//            System.out.println("등록된 팔레트가 없습니다.");
//            return;
//        }

        // 모든 팔레트 목록 중, 입력한 id 에 해당하는 팔레트가 있는지 확인
//        Palette likePal = palettes.stream().filter(p->p.getId() == id).findFirst().orElse(null);

//        if(likePal == null){
//            System.out.println(WRONGMESSAGE);
//
//            return;
//        } else {
//            if(member.getLikes().contains(id)) {
//                System.out.println("팔레트 id " + id+" 에 좋아요를 취소했습니다.");
//                member.getLikes().remove(Integer.valueOf(id));
//
//                // Palette dao 에 좋아요 1개 제거
//                likePal.setLike(likePal.getLike()-1);
//            } else {
//                System.out.println("팔레트 id " + id+" 에 좋아요를 눌렀습니다.");
//                List<Integer> likes = member.getLikes();
//                likes.add(id);
//                member.setLikes(likes);
//
//                // Palette dao 에 좋아요 1개 추가
//                likePal.setLike(likePal.getLike()+1);
//            }
//        }
//
//        File file = new File(paletteDBPath);
//        // 파일이 존재하지 않거나 비어 있으면 false 반환
//        if (!file.exists() || file.length() == 0) {
//            return;
//        }
//
//        // 좋아요 누른 항목 DB 반영하기
//        // 마이팔레트
//        paletteRepository.insertOrUpdate(likePal);
//
//        // 유저
//        memberRepository.insertOrUpdate(member);


    }

    /**
     * 내 팔레트 목록 조회
     * @param member
     * @throws Exception
     */
    public void findMyPalette(Member member) throws Exception {

        System.out.println("-----내 팔레트 목록-----");

//        paletteRepository.findByMember(member).stream().forEach(Palette::printInfo);

        System.out.println("-----내 팔레트 목록 조회 종료-----");
    }

    /**
     * 로그인한 member 가 좋아요 누른 팔레트 목록 조회
     * @param member
     * @throws Exception
     */
    public void findMyLikePaletteList(Member member) throws Exception {

        System.out.println("-----내가 좋아요 누른 팔레트 목록-----");

//        paletteRepository.findByMemberLike(member).stream().forEach(Palette::printInfo);

        System.out.println("-----내가 좋아요 누른 팔레트 목록 조회 종료-----");
    }

    /**
     * 팔레트 수정
     * @param member
     * @throws Exception
     */
    public void editPalette(Member member) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("-----팔레트 수정 페이지-----");

        System.out.println("내 팔레트 목록");
//        paletteRepository.findByMember(member).stream().forEach(Palette::printInfo);

        Palette palette = null;

        while(true) {

            System.out.println("수정할 내 팔레트 번호를 입력하세요. 취소하려면 q 를 입력하세요.");
            String input = sc.nextLine();

            if(input.equals("q")) break;

            palette = paletteRepository.findById(Integer.parseInt(input));

            if(palette == null) {
                System.out.println(WRONGMESSAGE);
                continue;
            }
//            if(!palette.getMemberId().equals(member.getId())) {
//                System.out.println("아이디가 다름"+palette.getMemberId()+" : "+member.getId());
//                continue;
//            }
//
//            // 팔레트 정보 출력
//            System.out.println("\n-----"+palette.getId()+"-----");
//            palette.printInfo();

            // 팔레트 정보 수정
            System.out.println("변경할 정보의 번호를 입력하세요.");
            System.out.println("1. 이름, 2. imgUrl, 3. 페인트 목록, 0. 뒤로가기");
            int choice = Integer.parseInt(sc.nextLine());
//            if(choice == 1){
//                System.out.println("새로운 이름을 입력하세요.");
//                System.out.println("이름 변경을 취소하려면 q 를 입력하세요");
//                String nameInput = sc.nextLine();
//
//                if(nameInput.equals("q")){
//                    break;
//                } else {
//                    palette.setName(nameInput);
//                    System.out.println("이름 변경이 완료되었습니다.");
//                    paletteRepository.insertOrUpdate(palette);
//                }
//            } else if(choice == 2){
//                System.out.println("새로운 이미지를 src/asset 에 업로드한 후 엔터를 누르세요.");
//                System.out.println("이미지 변경을 취소하려면 q 를 입력하세요");
//                String imgInput = sc.nextLine();
//
//                if(imgInput.equals("q")){
//                    break;
//                } else {
//                    String imgUrl = moveAndCleanAsset.saveImg(imgInput);
//                    palette.setImgUrl(imgUrl);
//                    paletteRepository.insertOrUpdate(palette);
//                    System.out.println("이미지 변경이 완료되었습니다.");
//                }
//            } else if(choice == 3){
//                System.out.println("1. 페인트 삭제, 2. 페인트 추가, 3. 뒤로가기");
//                int paintMenuInput = Integer.parseInt(sc.nextLine());
//
//                if(paintMenuInput == 1) {
//
//                    System.out.println("삭제할 페인트 번호를 입력하세요.");
//                    int delPaintInput = Integer.parseInt(sc.nextLine());
//                    boolean b = palette.getPaints().removeIf(paint -> paint.getId() == delPaintInput);
//                    if(b) {
//                        System.out.println("페인트 id "+delPaintInput+" 삭제 완료");
//                        paletteRepository.insertOrUpdate(palette);
//                    } else {
//                        System.out.println(WRONGMESSAGE);
//                    }
//
//                } else if(paintMenuInput == 2){
//
//                    System.out.println("추가할 페인트 번호를 입력하세요.");
//                    int newPaintInput = Integer.parseInt(sc.nextLine());
//                    if(palette.getPaints().stream().noneMatch(p -> p.getId() == newPaintInput)) {
//                        Paint paint = paintRepository.findById(newPaintInput);
//                        if(paint == null){
//                            System.out.println("그런 페인트는 없습니다.");
//                        } else {
//                            List<Paint> lists = palette.getPaints();
//                            lists.add(paint);
//                            palette.setPaints(lists);
//                            System.out.println("페인트 id "+newPaintInput+" 추가 완료");
//                            paletteRepository.insertOrUpdate(palette);
//                        }
//                    } else {
//                        System.out.println("이미 팔레트에 존재하는 페인트입니다.");
//                    }
//
//                } else if(paintMenuInput == 3){
//
//                } else {
//                    System.out.println(WRONGMESSAGE);
//                }
//            } else if(choice == EXIT){
//                break;
//            } else {
//                System.out.println(WRONGMESSAGE);
//            }
//
        }

        paletteRepository.insertOrUpdate(palette);

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
        Integer num = paletteRepository.save(palette).orElse(null);
        if(num == null) {
            System.out.println("팔레트 저장 실패.\n");
        } else {
            // 팔레트 내 페인트 목록 저장
            for(int i : paintList) {
                if(!palPaintRepository.save(num, i)){
                    System.out.println(i+"번 물감 저장 실패");
                }
            }
        }

        System.out.println("-----팔레트 "+name+" 저장 완료-----");
    }

    /**
     * 팔레트 상세조회(사진 앱 오픈됨)
     */
    public void findPaletteDetailById() {
        Scanner sc = new Scanner(System.in);

        System.out.println("조회할 팔레트 번호를 입력하세요.");
        int id = InputChecker.validate(sc.nextLine());

        if(id == -1) {
            return;
        }

        PaletteDto palette = paletteRepository.findByPalettePk(id).orElse(null);

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
