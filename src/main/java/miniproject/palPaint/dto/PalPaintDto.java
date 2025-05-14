package miniproject.palPaint.dto;

import lombok.Getter;
import miniproject.brand.code.BrandCode;
import miniproject.common.code.PaintColorCode;

@Getter
public class PalPaintDto {
    private int palPaintPk;
    private int palIdx;
    private int paintIdx;
    private String colorEn;
    private PaintColorCode colorgroupEn;
    private BrandCode brandCode;

    public PalPaintDto() {
    }

    public PalPaintDto(int palPaintPk, int palIdx, int paintIdx, String colorEn, PaintColorCode colorgroupEn, BrandCode brandCode) {
        this.palPaintPk = palPaintPk;
        this.palIdx = palIdx;
        this.paintIdx = paintIdx;
        this.colorEn = colorEn;
        this.colorgroupEn = colorgroupEn;
        this.brandCode = brandCode;
    }

    public void printInfo(){
        System.out.println(palPaintPk+". "+colorEn+", 색분류:"+colorgroupEn.name()+", 브랜드:"+brandCode.name());
    }
}
