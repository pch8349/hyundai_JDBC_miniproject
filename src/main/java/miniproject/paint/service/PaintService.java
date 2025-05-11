package miniproject.paint.service;

import miniproject.common.code.MessageCode;
import miniproject.common.code.PaintColorCode;
import miniproject.common.code.StateCode;
import miniproject.member.dao.Member;
import miniproject.paint.dao.Paint;
import miniproject.paint.repository.PaintRepository;
import miniproject.paint.repository.PaintRepositoryImpl;
import miniproject.palette.dao.Palette;
import miniproject.palette.repository.PaletteRepository;
import miniproject.palette.repository.PaletteRepositoryImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaintService {
    private final String WRONGMESSAGE = MessageCode.WRONG.getMessage();
    private final int EXIT = StateCode.EXIT.getCode();

    private final String paintDBPath = "C:/temp/pfdb/paint.dat";

    private final PaintRepository paintRepository = new PaintRepositoryImpl();
    private final PaletteRepository paletteRepository = new PaletteRepositoryImpl();

    /**
     * 페인트 검색어, 색 조건에 맞는 결과 모두 출력
     * @param search
     * @param codeList
     * @throws Exception
     */
    public void findAllPaint(String search, List<PaintColorCode> codeList) throws Exception{

        List<Paint> paints = paintRepository.findAll(search, codeList);

        System.out.println("\n-----------물감 검색------------");
//        paints.stream().forEach(Paint::printInfo);
    }

    /**
     * 페인트 검색 필터 설정
     * @return
     */
    public List<PaintColorCode> setPaintSearchFilter(){
        Scanner sc = new Scanner(System.in);
        PaintColorCode[] codeArr = {PaintColorCode.RED, PaintColorCode.ORANGE, PaintColorCode.YELLOW, PaintColorCode.GREEN, PaintColorCode.BLUE, PaintColorCode.NAVY, PaintColorCode.PURPLE, PaintColorCode.PINK, PaintColorCode.WHITE, PaintColorCode.BLACK, PaintColorCode.GRAY, PaintColorCode.BROWN, PaintColorCode.IVORY};
        List<PaintColorCode> codeList = new ArrayList<PaintColorCode>();


        while(true) {
            System.out.println("\n-----------------");
            System.out.println("검색하고 싶은 색상을 선택하십시오.");
            System.out.println("1.RED, 2.ORANGE, 3.YELLOW, 4.GREEN, 5.BLUE, " +
                    "6.NAVY, 7.PURPLE, 8.PINK, 9.WHITE, 10.BLACK, " +
                    "11.GRAY, 12.BROWN, 13.IVORY");
            System.out.println("0. 뒤로가기");
            int input = Integer.parseInt(sc.nextLine());

            if(input == EXIT) break;
            else if(input > 13) System.out.println(WRONGMESSAGE);

            if(codeList.stream().anyMatch(code -> code == codeArr[input])){
                codeList.stream().findFirst().ifPresent(codeList::remove);
            } else {
                codeList.add(codeArr[input - 1]);
            }

            System.out.println("현재 적용된 필터 목록");
            codeList.stream().map(PaintColorCode::toString).forEach(System.out::println);
        }

        return codeList;
    }

    /**
     * 페인트 데이터 txt 파일 .dat 파일로 변환해 넣기. 더미데이터 생성.
     * 파일 경로는 "C:/temp/pfdb/paint.txt" 가 되게 놓기
     * @throws Exception
     */
    public void paintDataWriter() throws Exception {
        File txtFile = new File("C:/temp/pfdb/paint.txt");
        File datFile = new File(paintDBPath);

        BufferedReader br = new BufferedReader(new FileReader(txtFile));

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datFile));

        String line;
        int id = 1;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 3) continue;

            String brand = parts[0].trim();
            String name = parts[1].trim();
            String colorStr = parts[2].trim();

            PaintColorCode color = PaintColorCode.fromString(colorStr);
//            Paint paint = new Paint(id++, name, brand, color);
//            oos.writeObject(paint);
        }

        System.out.println("페인트 더미데이터 새로 넣기 성공\n");

        oos.flush();
        oos.close();
        br.close();
    }

    /**
     * 페인트 ID 를 받아 내 팔레트에 추가하기
     * @param member
     * @throws Exception
     */
    public void addPaintToPalette(Member member) throws Exception{
        Scanner sc = new Scanner(System.in);

        Paint paint = null;
        Palette palette = null;

        while(true) {
            System.out.println("추가할 페인트 번호 입력하세요. q 입력 시 종료");
            String input = sc.nextLine();
            if(input.equals("q")){
                System.out.println("입력 취소");
                return;
            }

            paint = paintRepository.findById(Integer.parseInt(input));

            if (paint == null) {
                System.out.println("그런 페인트 id 는 존재하지 않습니다.");
            } else {
                break;
            }
        }

        List<Palette> palettes = paletteRepository.findByMember(member);
        if(palettes == null || palettes.size() == 0) {
            System.out.println("팔레트가 없습니다.");
            return;
        }
        System.out.println("내 팔레트 목록");
//        palettes.stream().forEach(Palette::printInfo);

        while(true) {
            System.out.println("\n페인트를 추가할 팔레트 번호를 입력해주세요. q 입력 시 종료");
            String paletteId = sc.nextLine();
            if(paletteId.equals("q")){
                System.out.println("입력 취소");
                return;
            }
//            if (!palettes.stream().anyMatch(p -> p.getId() == Integer.parseInt(paletteId))) {
//                System.out.println("그런 팔레트는 존재하지 않습니다.");
//            } else {
//                for (int i = 0; i < palettes.size(); i++) {
//                    if (palettes.get(i).getId() == Integer.parseInt(paletteId)) {
//                        List<Paint> list = palettes.get(i).getPaints();
//
//                        // 팔레트 내 페인트가 중복으로 존재하면 저장하지 않음
//                        Paint tempPaint = paint;
//                        if(list.stream().anyMatch(p -> p.getId() == tempPaint.getId())) {
//                            System.out.println("이미 존재하는 페인트는 저장할 수 없습니다.");
//                        } else{
//                            list.add(paint);
//                            palette = palettes.get(i);
//                            palette.setPaints(list);
//                            paletteRepository.insertOrUpdate(palette);
//                            System.out.println("팔레트에 페인트 추가 완료");
//                        }
//                        break;
//                    }
//                }
//                break;
//            }
        }
    }
}
