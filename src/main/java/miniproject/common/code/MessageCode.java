package miniproject.common.code;

public enum MessageCode {
    WRONG("잘못된 번호 입력입니다. 다시 입력해주세요.");
    private final String message;
    MessageCode(String message) { this.message = message; }
    public String getMessage() { return message; }
}
