package Models;

import FileOperator.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class LevelContent implements Serializable { //model tego co zawiera plansza
    public int levelNumber;
    public int boardSize;
    public int currentNumber;

    public boolean[][] availableButtons;
    public int[][] solution;
    public int[][] answers;
    public String[][] buttonsText;
    public String[][] buttonsTextToSave;
    public ArrayList<History> history;

    public LevelContent(int boardSize, int currentNumber, int[][] solution, String[][] buttonsText, boolean[][] availableButtons, int[][] answers, ArrayList<History> history, String[][] buttonsTextToSave){ //quicksave
        this.boardSize = boardSize;
        this.currentNumber = currentNumber;
        this.solution = solution;
        this.buttonsText = buttonsText;
        this.availableButtons = availableButtons;
        this.answers = answers;
        this.history = history;
        this.buttonsTextToSave = buttonsTextToSave;
    }

    public LevelContent(int levelNumber, int boardSize, int[][] solution, String[][] buttonsText){
        this.levelNumber = levelNumber;
        this.boardSize = boardSize;
        this.solution = solution;
        this.buttonsText = buttonsText;
    }

    public void levelContentQuicksave(LevelContent levelSave){
        FileWriter fileWriter = new FileWriter("quicksave.txt");
        fileWriter.writeQuicksave(levelSave);
    }

    public LevelContent(){ }
}
