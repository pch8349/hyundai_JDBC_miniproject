package miniproject.brand.code;

public enum BrandCode {
    LIQUITEX(1),
    GOLDEN(2),
    WINSOR_NEWTON(3),
    OLDHOLLAND(4);  // 예시, 숫자 값은 DB와 반드시 일치해야 함

    private final int id;

    BrandCode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BrandCode fromId(int id) {
        for (BrandCode b : values()) {
            if (b.id == id) return b;
        }
        throw new IllegalArgumentException("Unknown brand ID: " + id);
    }
}
