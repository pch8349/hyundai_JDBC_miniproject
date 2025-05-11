package miniproject.paint.repository;

import miniproject.common.code.PaintColorCode;
import miniproject.paint.dao.Paint;

import java.util.List;

public interface PaintRepository {

    /**
     * 페인트 전체 조회 로직
     * @return
     * @throws Exception
     */
    List<Paint> findAll(String search, List<PaintColorCode> codeList) throws Exception;

    /**
     * 페인트 id 로 단일조회
     * @param id
     * @return
     * @throws Exception
     */
    Paint findById(int id) throws Exception;
}
