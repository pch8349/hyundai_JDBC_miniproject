package miniproject.global;

public class InputChecker {

    /**
     * 메뉴 입력값 정합성 체크 로직
     * @param input
     * @param min
     * @param max
     * @return 문자열 데이터 정수 변환값
     */
    public static int validate(String input, int min, int max){
        if(!input.matches("\\d+")) {
            System.out.println("잘못된 입력입니다\n\n");
            return -1;
        } else{
            int inputInt = Integer.parseInt(input);
            if(inputInt > max || inputInt < min){
                System.out.println("잘못된 입력입니다\n\n");
                return -1;
            }
            return inputInt;
        }
    }
}
