package miniproject.palPaint.repository;

import java.util.List;

public interface PalPaintRepository {

    /**
     * 팔레트 내 페인트 목록 저장 메서드
     * @param palIdx
     * @return
     */
    boolean save(int palIdx, int paintIdx);
}
