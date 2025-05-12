package miniproject.palLike.repository;

import miniproject.palLike.dao.PalLike;

public interface PalLikeRepository {

    /**
     * 팔레트 좋아요를 memberIdx, palIdx로 조회해 삭제
     * @param palLike
     * @return
     */
    boolean deleteByMemberIdxAndPalIdx(PalLike palLike);

    /**
     * 팔레트 좋아요를 memberIdx, palIdx로 생성
     * @param palLike
     * @return
     */
    boolean save(PalLike palLike);

    /**
     * 팔레트 좋아요를 눌렀는지 아닌지 memberIdx, palIdx로 조회
     * @param palLike
     * @return true 시 좋아요 존재, false 시 좋아요 없음
     */
    boolean isExistByPalLike(PalLike palLike);
}
