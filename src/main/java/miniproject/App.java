package miniproject;

import miniproject.member.dao.Member;

public class App {

    public static Member member = null;

    public static void main(String[] args) throws Exception{

        PaintFinderApp app = new PaintFinderApp();
        System.out.println(System.getProperty("file.encoding"));  // UTF-8이 나와야 정상
        app.begin();
    }
}
