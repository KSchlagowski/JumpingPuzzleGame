package FileOperator;

import Models.LevelContent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Vector;

public class FileReader {
    ArrayList<LevelContent> levelsList = new ArrayList<>();
    String path;

    public FileReader(String path){
        this.path = path;
    }

    public ArrayList<LevelContent> readFile(){
        File f = new File(path);
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Vector<LevelContent> deserializeLevelContent = (Vector<LevelContent>)ois.readObject();
            ois.close();

            levelsList.addAll(deserializeLevelContent);

        } catch (ClassNotFoundException | IOException ex) {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.writeFirstLevelToFile();
        }

        return levelsList;
    }
}