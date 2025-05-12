package miniproject.paint.dto;

import lombok.Getter;
import lombok.Setter;
import miniproject.brand.code.BrandCode;
import miniproject.common.code.PaintColorCode;

@Getter
@Setter
public class PaintDto {
    private int paintPk;
    private BrandCode brandCode;
    private String colorEn;
    private PaintColorCode colorgroupEn;
    private String prodImg;

    public PaintDto() {
    }

    public PaintDto(int paintPk, String colorEn){
        this.paintPk = paintPk;
        this.colorEn = colorEn;
    }

    public PaintDto(int paintPk, BrandCode brandCode, String colorEn, PaintColorCode colorgroupEn, String prodImg) {
        this.paintPk = paintPk;
        this.brandCode = brandCode;
        this.colorEn = colorEn;
        this.colorgroupEn = colorgroupEn;
        this.prodImg = prodImg;
    }

    public void printInfo(){
        System.out.println(paintPk+". 색상구분 : "+colorgroupEn.toString()+", 물감명 : "+colorEn + ", 브랜드명 : "+brandCode.toString());
    }
}