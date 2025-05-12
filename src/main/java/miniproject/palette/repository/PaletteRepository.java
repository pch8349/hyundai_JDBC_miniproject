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

    /**
     * 팔레트 pk 로 특정 팔레트 찾기
     * @param pk
     * @return PaletteDto
     */
    Optional<PaletteDto> findPaletteDtoByPalettePk(int pk);

    /**
     * 멤버 pk 가 가지고 있는 팔레트 목록 조회
     * @param memberPk
     * @return
     */
    Optional<List<PaletteDto>> findByMemberPk(int memberPk);

    /**
     * 로그인 한 유저가 누른 좋아요 팔레트 목록 출력
     * @param memberPk
     * @return
     */
    Optional<List<PaletteDto>> findByMemberLike(int memberPk);

    /**
     * 로그인 한 유저의 팔레트 목록 출력
     * @param memberIdx
     * @return
     */
    Optional<List<Palette>> findByMemberIdx(int memberIdx);

    /**
     * 팔레트 저장
     * @param palette
     * @return
     */
    Optional<Integer> save(Palette palette);

    /**
     * 팔레트 존재 여부 파악
     * @param pk
     * @return true/false
     */
    boolean isExistByPk(int pk);

    /**
     * DB 원본 데이터 팔레트 데이터 읽어오기
     * @param pk
     * @return
     */
    Optional<Palette> findByPk(int pk);

    /**
     * 팔레트 정보를 전체 업데이터 해버리기
     * @param palette
     * @return
     */
    boolean updatePalette(Palette palette);
}
