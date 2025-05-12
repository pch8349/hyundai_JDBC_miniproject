package miniproject.palette.repository;

import miniproject.member.dao.Member;
import miniproject.palette.dao.Palette;
import miniproject.palette.dto.PaletteDto;

import java.util.List;
import java.util.Optional;

public interface PaletteRepository {

    /**
     * 팔레트 전체 조회
     * @return
     */
    Optional<List<PaletteDto>> findAll();

    /***
     * 팔레트 pk 로 이미지 주소 찾기
     * @param pk
     * @return PaletteDto
     */
    Optional<PaletteDto> findByPalettePk(int pk);

    /**
     * 팔레트 정보 변경/신규작성
     * @param palette
     * @throws Exception
     */
    void insertOrUpdate(Palette palette) throws Exception;

    /**
     * 로그인 한 유저가 누른 좋아요 팔레트 목록 출력
     * @param member
     * @return
     * @throws Exception
     */
    List<Palette> findByMemberLike(Member member) throws Exception;

    /**
     * 로그인 한 유저의 팔레트 목록 출력
     * @param memberIdx
     * @return
     */
    Optional<List<Palette>> findByMemberIdx(int memberIdx);

    /**
     * 입력된 팔레트 중 가장 높은 id 를 출력. 없으면 0 출력
     * @return
     * @throws Exception
     */
    int findLastId() throws Exception;

    /**
     * 팔레트 id 로 단일 팔레트 조회
     * @param id
     * @return
     * @throws Exception
     */
    Palette findById(int id) throws Exception;

    Optional<Integer> save(Palette palette);
}
