package miniproject.palette.dto;

import lombok.Getter;
import lombok.Setter;
import miniproject.member.dto.MemberLightDto;
import miniproject.paint.dto.PaintDto;

import java.util.List;

@Getter
@Setter
public class PaletteDto {
    private int palPk;
    private MemberLightDto member;
    private String palName;
    private String imgSrc;
    private int likeCount;
    private List<PaintDto> paintList;

    public PaletteDto() {
    }

    public PaletteDto(int palPk, MemberLightDto member, String palName, String imgSrc, int likeCount, List<PaintDto> paintList) {
        this.palPk = palPk;
        this.member = member;
        this.palName = palName;
        this.imgSrc = imgSrc;
        this.likeCount = likeCount;
        this.paintList = paintList;
    }

    public void printInfo(){
        System.out.print(palPk+". 팔레트명: "+palName+", 좋아요: "+ likeCount +", 팔레트 소유자: "+member.getNickname()+", 페인트 목록 : { ");
        for(PaintDto paint : paintList){
            System.out.print(paint.getPaintPk()+"."+paint.getColorEn()+" ");
        }
        System.out.println("}");
    }
}
