package miniproject.palLike.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PalLike {
    private int paletteLikePk;
    private int memberIdx;
    private int palIdx;
    private LocalDateTime createDate;

    public PalLike(int memberIdx, int palIdx) {
        this.memberIdx = memberIdx;
        this.palIdx = palIdx;
    }

    public PalLike(int paletteLikePk, int memberIdx, int palIdx, LocalDateTime createDate) {
        this.paletteLikePk = paletteLikePk;
        this.memberIdx = memberIdx;
        this.palIdx = palIdx;
        this.createDate = createDate;
    }
}
