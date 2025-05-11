package miniproject.palette.repository;


import miniproject.member.dao.Member;
import miniproject.palette.dao.Palette;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaletteRepositoryImpl implements PaletteRepository {

    private final String paletteDBPath = "C:/temp/pfdb/palette.dat";

    @Override
    public List<Palette> findAll() throws Exception {
        List<Palette> palettes = new ArrayList<Palette>();

        File file = new File(paletteDBPath);
        // 파일이 존재하지 않거나 비어 있으면 false 반환
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try{
            while(true){
                Palette p = (Palette) ois.readObject();
                palettes.add(p);
            }
        } catch (EOFException e){
        } finally{
            ois.close(); fis.close();
        }
        return palettes;
    }

    @Override
    public void insertOrUpdate(Palette palette) throws Exception{
        File file = new File(paletteDBPath);

        List<Palette> palettes = new ArrayList<>();

        // 기존 객체 읽기
        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        Palette p = (Palette) ois.readObject();
                        palettes.add(p);
                    } catch (EOFException e) {
                        break;
                    }
                }
            }
        }

        boolean updated = false;

        for (int i = 0; i < palettes.size(); i++) {
//            if (palette.getId() == palettes.get(i).getId()) {
//                palettes.set(i, palette); // 교체
//                updated = true;
//                break;
//            }
        }
        if (!updated) {
            palettes.add(palette); // 없으면 새로 추가
        }

        // 3. 전체 덮어쓰기
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Palette pal : palettes) {
                oos.writeObject(pal);
            }
        }
    }

    @Override
    public List<Palette> findByMemberLike(Member member) throws Exception {

        List<Palette> palettes = new ArrayList<>();

        File file = new File(paletteDBPath);
        // 파일이 존재하지 않거나 비어 있으면 false 반환
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Palette palette = (Palette) ois.readObject();
//                if(member.getLikes().contains(palette.getId())){
//                    palettes.add(palette);
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }

        return palettes;
    }

    @Override
    public List<Palette> findByMember(Member member) throws Exception {
        List<Palette> palettes = new ArrayList<>();

        File file = new File(paletteDBPath);
        // 파일이 존재하지 않거나 비어 있으면 false 반환
        if (!file.exists() || file.length() == 0) {
            return null;
        }


        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Palette palette = (Palette) ois.readObject();
//                if(palette.getMemberId().equals(member.getId())){
//                    palettes.add(palette);
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }

        return palettes;
    }

    @Override
    public int findLastId() throws Exception {
        File file = new File(paletteDBPath);

        int maxId = 0;

        if (!file.exists() || file.length() == 0) {
            return maxId;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Palette palette = (Palette) ois.readObject();
//                if(palette.getId() > maxId){
//                    maxId = palette.getId();
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }

        return maxId;
    }

    @Override
    public Palette findById(int id) throws Exception {
        File file = new File(paletteDBPath);

        Palette palette = null;

        if (!file.exists() || file.length() == 0) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try{
            while(true){
                Palette p = (Palette) ois.readObject();
//                if(p.getId() == id){
//                    palette = p;
//                    break;
//                }
            }
        } catch (EOFException e) {
        } finally {
            ois.close();
            fis.close();
        }

        return palette;
    }
}