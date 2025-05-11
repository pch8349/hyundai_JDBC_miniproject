package miniproject.palette.dao;

import lombok.Getter;
import lombok.Setter;
import miniproject.paint.dao.Paint;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Palette implements Serializable {
    private int palPk;
    private int memberIdx;
    private String palName;
    private String imgSrc;
    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public Palette(int memberIdx, String palName) {
        this.memberIdx = memberIdx;
        this.palName = palName;
    }

    public Palette(int memberIdx, String palName, String imgSrc) {
        this.memberIdx = memberIdx;
        this.palName = palName;
        this.imgSrc = imgSrc;
    }

    public Palette(int palPk, int memberIdx, String palName, String imgSrc, LocalDateTime createDate, LocalDateTime editDate) {
        this.palPk = palPk;
        this.memberIdx = memberIdx;
        this.palName = palName;
        this.imgSrc = imgSrc;
        this.createDate = createDate;
        this.editDate = editDate;
    }
}
