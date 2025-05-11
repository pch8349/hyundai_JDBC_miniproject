package miniproject;

import miniproject.common.code.StateCode;
import miniproject.common.view.MainView;
import miniproject.member.dto.MemberDto;

public class PaintFinderApp {

    private final int EXIT = StateCode.EXIT.getCode();
    private final int LOGIN = StateCode.LOGIN.getCode();
    private final int MAINMENU = StateCode.MAINMENU.getCode();

    private final MainView mainView = new MainView();

    public void begin() throws Exception {

        // 시스템 흐름 상태 관리 변수
        int runner = 1;

        while(true){
            if(runner == LOGIN) {
                MemberDto memberDto = mainView.loginView();
                App.member = memberDto.getMember();
                runner = memberDto.getState();
            } else if (runner == MAINMENU) {
                runner = mainView.mainMenuView(App.member);
            } else if(runner == EXIT){
                break;
            }
        }
    }
}