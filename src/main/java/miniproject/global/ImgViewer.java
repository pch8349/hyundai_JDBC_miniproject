package miniproject.global;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImgViewer {

    /**
     * 이미지 경로 입력 시 해당 경로의 파일 사진 뷰어로 여는 메서드
     * @param path
     */
    public static void viewer(String path){
        if(path == null || path.equals("")){
            System.out.println();
        } else {
            File imageFile = new File(path);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(imageFile);
                } catch (IOException e) {
                    System.out.println("\n 이미지 파일이 잘못되었습니다.\n");
                    e.printStackTrace();
                }
            } else {
                System.out.println("\nDesktop API를 사용할 수 없습니다.");
            }
        }
    }
}
