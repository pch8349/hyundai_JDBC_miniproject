package miniproject.paint.dao;

import lombok.Getter;
import lombok.Setter;
import miniproject.common.code.PaintColorCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Paint implements Serializable {
    private int paintPk;
    private int brandPk;
    private String colorEn;
    private PaintColorCode colorgroupEn;
    private String prodImg;
    private LocalDateTime createDate;
    private int createMemberIdx;
    private LocalDateTime editDate;
    private int editMemberIdx;

    public Paint(int brandPk, String colorEn, PaintColorCode colorgroupEn, String prodImg, int createMemberIdx) {
        this.brandPk = brandPk;
        this.colorEn = colorEn;
        this.colorgroupEn = colorgroupEn;
        this.prodImg = prodImg;
        this.createMemberIdx = createMemberIdx;
    }

    public Paint(int paintPk, int brandPk, String colorEn, PaintColorCode colorgroupEn, String prodImg, LocalDateTime createDate, int createMemberIdx, LocalDateTime editDate, int editMemberIdx) {
        this.paintPk = paintPk;
        this.brandPk = brandPk;
        this.colorEn = colorEn;
        this.colorgroupEn = colorgroupEn;
        this.prodImg = prodImg;
        this.createDate = createDate;
        this.createMemberIdx = createMemberIdx;
        this.editDate = editDate;
        this.editMemberIdx = editMemberIdx;
    }
}