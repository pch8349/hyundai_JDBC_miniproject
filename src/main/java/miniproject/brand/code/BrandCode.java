package miniproject.brand.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum BrandCode {
    LIQUITEX(1, "리퀴텍스"),
    GOLDEN(2, "골덴"),
    WINSOR_NEWTON(3, "윈저앤뉴턴"),
    OLDHOLLAND(4, "올드홀랜드");  // 예시, 숫자 값은 DB와 반드시 일치해야 함

    private final int id;
    private final String korName;

    BrandCode(int id, String korName) {
        this.id = id;
        this.korName = korName;
    }

    public int getId() {
        return id;
    }

    public String getKorName(){
        return korName;
    }

    public static BrandCode fromId(int id) {
        for (BrandCode b : values()) {
            if (b.id == id) return b;
        }
        throw new IllegalArgumentException("Unknown brand ID: " + id);
    }

    public static List<BrandCode> getAllBrandCodes() {
        return new ArrayList<BrandCode>(Arrays.asList(values()));
    }
}
