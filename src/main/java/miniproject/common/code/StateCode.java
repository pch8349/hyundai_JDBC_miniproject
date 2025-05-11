package miniproject.common.code;

public enum StateCode {
    EXIT(0), LOGIN(1), MAINMENU(2);
    private final int code;
    StateCode(int code) { this.code = code; }
    public int getCode() { return code; }
}
