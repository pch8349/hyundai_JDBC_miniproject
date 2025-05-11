package miniproject.palPaint.dao;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PalPaint {
    private int palPaintPk;
    private int palIdx;
    private int paintIdx;
    private LocalDateTime createDate;

    public PalPaint(int palIdx, int paintIdx) {
        this.palIdx = palIdx;
        this.paintIdx = paintIdx;
    }

    public PalPaint(int palPaintPk, int palIdx, int paintIdx, LocalDateTime createDate) {
        this.palPaintPk = palPaintPk;
        this.palIdx = palIdx;
        this.paintIdx = paintIdx;
        this.createDate = createDate;
    }
}
