package Helpers;

import Models.LevelContent;

public class Level0 {
    public int levelNumber = 0;
    public int boardSize = 3;
    public int[][] solution = new int[boardSize][boardSize];
    public String[][] buttonsText = new String[boardSize][boardSize];

    public Level0(){
        buttonsText[0][0] = ("S >");
        buttonsText[0][1] = ("V");
        buttonsText[0][2] = ("V");

        buttonsText[1][0] = (">");
        buttonsText[1][1] = ("^");
        buttonsText[1][2] = ("<");

        buttonsText[2][0] = (">");
        buttonsText[2][1] = ("<");
        buttonsText[2][2] = ("X");

        solution[0][1] = 5;
        solution[0][2] = 1;

        solution[1][0] = 3;
        solution[1][1] = 4;
        solution[1][2] = 2;

        solution[2][0] = 7;
        solution[2][1] = 6;
    }

    public LevelContent getFirstLevel(){
        return new LevelContent(levelNumber, boardSize, solution, buttonsText);
    }
}
