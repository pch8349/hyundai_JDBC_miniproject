package miniproject.common.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PaintColorCode {
    RED, ORANGE, YELLOW, GREEN, BLUE, NAVY, PURPLE, PINK, WHITE, BLACK, GRAY, BROWN, IVORY;

    public static PaintColorCode fromString(String color) {
        return PaintColorCode.valueOf(color.trim().toUpperCase());
    }

    public static List<PaintColorCode> getAllColors() {
        return new ArrayList<PaintColorCode>(Arrays.asList(values()));
    }

    public static void printInfo(){
        List<PaintColorCode> codes = new ArrayList<PaintColorCode>(Arrays.asList(values()));
        for(int i = 0; i<codes.size(); i++){
            System.out.println((i+1)+". "+codes.get(i).name());
        }
        System.out.println();
    }
}
