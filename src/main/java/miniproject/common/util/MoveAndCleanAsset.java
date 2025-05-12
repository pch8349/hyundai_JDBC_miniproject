package miniproject.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class MoveAndCleanAsset {

    private final String[] ImgPath = {"C:\\temp\\paint", "C:\\temp\\palette"};

    /**
     * 이미지 파일 String 으로 변환
     * @param dir 0은 물감, 1은 팔레트
     * @return
     */
    public String saveImg(int dir){
        Path assetDir = Path.of("C:\\Users\\pch\\git\\JDBC\\hyundai_JDBC_miniproject\\src\\asset");
        Path targetDir = Path.of(ImgPath[dir]);

        try {
            // asset 폴더 내의 모든 파일 목록 (디렉토리 제외)
            List<Path> files = Files.list(assetDir)
                    .filter(Files::isRegularFile)
                    .toList();

            // 파일이 정확히 1개인지 확인
            if (files.size() != 1) {
                System.out.println("asset 폴더에는 하나의 파일만 존재해야 합니다. 현재 파일 수: " + files.size());
                return "";
            }

            Path file = files.get(0);
            String fileName = file.getFileName().toString().toLowerCase();

            // 확장자 확인
            if (!(fileName.endsWith(".jpg") || fileName.endsWith(".png"))) {
                System.out.println("파일 확장자는 .jpg 또는 .png 여야 합니다. 현재: " + fileName);
                return "";
            }

            // 대상 디렉토리 생성
            Files.createDirectories(targetDir);

            // 파일 복사
            Path targetPath = targetDir.resolve(file.getFileName());
            Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);

            // asset 폴더 내 파일 삭제
            for (Path p : files) {
                Files.delete(p);
            }

            return targetPath.toString();

        } catch (IOException | IllegalStateException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "";
        }

    }
}
