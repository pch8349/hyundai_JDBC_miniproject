package miniproject.common.code;

public enum PaintColorCode {
    RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE, PINK, WHITE, BLACK, GRAY, BROWN, IVORY;

    public static PaintColorCode fromString(String color) {
        return PaintColorCode.valueOf(color.trim().toUpperCase());
    }
}
