package miniproject.common.service;

import java.io.File;
import java.io.IOException;

public class DefaultService {

    public void dbSet() throws IOException{
        File file1 = new File("C:\\temp\\pfdb/member.dat");
        File file2 = new File("C:\\temp\\pfdb/paint.dat");
        File file3 = new File("C:\\temp\\pfdb/palette.dat");

        if(!file1.exists()) file1.createNewFile();
        if(!file2.exists()) file2.createNewFile();
        if(!file3.exists()) file3.createNewFile();
    }


}
