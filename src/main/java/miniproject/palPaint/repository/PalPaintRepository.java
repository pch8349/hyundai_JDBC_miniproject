package miniproject.palPaint.repository;

import miniproject.paint.dto.PaintDto;
import miniproject.palPaint.dao.PalPaint;
import miniproject.palPaint.dto.PalPaintDto;

import java.util.List;
import java.util.Optional;

public interface PalPaintRepository {

    /**
     * 팔레트 내 페인트 목록 저장 메서드
     * @param palPaint
     * @return
     */
    boolean save(PalPaint palPaint);

    /**
     * palPaintPk로 palPaint 목록 삭제
     * @param palPaintPk
     * @return
     */
    boolean deleteByPalPaintPk(int palPaintPk);

    /**
     * 팔레트 내 페인트 목록 반환용
     * @param palIdx
     * @return
     */
    Optional<List<PalPaintDto>> findByPalIdx(int palIdx);
}
