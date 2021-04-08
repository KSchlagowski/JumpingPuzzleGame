package FileOperator;

import Helpers.Level0;
import Models.LevelContent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

public class FileWriter {
    ArrayList<LevelContent> levelsList;
    String path;

    public FileWriter(String path){
        this.path = path;
    }

    public void writeFile(ArrayList<LevelContent> levelsList){
        Vector<LevelContent> v = new Vector<>(levelsList);

        System.out.println("wielkosc do zapisu "+v.size());

        File f = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(v);
            oos.close();
            System.out.println("data written successfully");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void appendToFile(LevelContent levelContent){ //dodanie planszy do pliku
        FileReader fileReader = new FileReader(path);
        levelsList = fileReader.readFile();
        levelsList.add(levelContent);
        writeFile(levelsList);
    }

    public void writeFirstLevelToFile(){ //zapis poziomu 0 do pliku
        levelsList = new ArrayList<>();
        Level0 lvl0 = new Level0();
        levelsList.add(new LevelContent(lvl0.levelNumber, lvl0.boardSize, lvl0.solution, lvl0.buttonsText));
        writeFile(levelsList);
    }

    public void writeQuicksave(LevelContent levelContent){ //zapis aktualnego stanu rozgrywki
        levelsList = new ArrayList<>();
        levelsList.add(levelContent);
        writeFile(levelsList);
    }
}