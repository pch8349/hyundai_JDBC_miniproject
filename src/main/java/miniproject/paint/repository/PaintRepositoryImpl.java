package miniproject.paint.repository;

import miniproject.common.code.PaintColorCode;
import miniproject.paint.dao.Paint;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class PaintRepositoryImpl implements PaintRepository {

    private final String paintDBPath = "C:/temp/pfdb/paint.dat";

    @Override
    public List<Paint> findAll(String search, List<PaintColorCode> codeList) throws Exception {
        File file = new File(paintDBPath);
        List<Paint> paints = new ArrayList<Paint>();

        // 파일이 존재하지 않거나 비어 있으면 false 반환
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Paint paint = (Paint) ois.readObject();

//                if(paint.getName().contains(search)){
//                    if(codeList == null){
//                        paints.add(paint);
//                    }
//                    else{
//                        if(codeList.stream().anyMatch(i -> i == paint.getColor())){
//                            paints.add(paint);
//                        }
//                    }
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }
        return paints;
    }

    @Override
    public Paint findById(int id) throws Exception {

        File file = new File(paintDBPath);
        Paint paint = null;

        // 파일이 존재하지 않거나 비어 있으면 false 반환
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Paint p = (Paint) ois.readObject();
//                if(p.getId() == id){
//                    paint = p;
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }
        return paint;
    }
}
