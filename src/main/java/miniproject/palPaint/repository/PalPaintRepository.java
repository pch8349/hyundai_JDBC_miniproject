package miniproject.palPaint.repository;

import miniproject.palPaint.dao.PalPaint;

import java.util.List;

public interface PalPaintRepository {

    /**
     * 팔레트 내 페인트 목록 저장 메서드
     * @param palPaint
     * @return
     */
    boolean save(PalPaint palPaint);

    /**
     *
     * @return
     */
    boolean delete(PalPaint palPaint);
}
