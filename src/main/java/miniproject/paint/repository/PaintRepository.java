package miniproject.paint.repository;

import miniproject.common.code.PaintColorCode;
import miniproject.paint.dao.Paint;
import miniproject.paint.dto.PaintDto;

import java.util.List;
import java.util.Optional;

public interface PaintRepository {

    /**
     * 페인트 전체 조회 로직
     * @return
     * @throws Exception
     */
    Optional<List<PaintDto>> findAll(String search, List<PaintColorCode> codeList);

    /**
     * 페인트 id 로 단일조회
     * @param id
     * @return
     * @throws Exception
     */
    Optional<Paint> findById(int id);

    /**
     * 물감 등록 로직. 관리자만 접근 가능해야한다.
     * @param paint
     * @return
     */
    boolean registPaint(Paint paint);

    /**
     * id 로 페인트 존재 유무 확인
     * @param id
     * @return
     */
    boolean isPaintExist(int id);


}
