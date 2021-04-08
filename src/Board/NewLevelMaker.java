package Board;

import FileOperator.FileReader;
import FileOperator.FileWriter;
import Models.LevelContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NewLevelMaker extends JFrame{
    JPanel panel = new JPanel();

    int currentNumber = 0;
    int boardSize = 4;
    int previousX = -1;
    int previousY = -1;
    int levelNumber = 0;
    boolean isSolutionCorrect = true;
    boolean canBeSaved = false;
    String path;

    boolean[][] availableButtons = new boolean[boardSize][boardSize];
    int[][] answers = new int[boardSize][boardSize];
    int[][] solution = new int[boardSize][boardSize];
    JButton[][] buttons = new JButton[boardSize][boardSize];
    String[][] buttonsText = new String[boardSize][boardSize];

    FileReader fileReader;
    ArrayList<LevelContent> levelsList;

    public NewLevelMaker(String path){
        this.path = path;
        fileReader = new FileReader(path);
        levelsList = fileReader.readFile();
        levelNumber = levelsList.size();
        initialize();
        panel.setLayout(new GridLayout(boardSize,boardSize));
        makeBoard(false);

        add(panel);
    }

    private void initialize(){
        setLocation(550,400);
        setSize(600,400);
        setVisible(true);
    }

    public void makeBoard(boolean makeOnlyAvailableButtons){
        currentNumber = 0;
        for (int i = 0; i< boardSize; i++ ){
            for (int j = 0; j< boardSize; j++){
                if (!makeOnlyAvailableButtons) {
                    buttons[i][j] = new JButton();
                    (buttons[i][j]).addActionListener(new Element(i, j));

                    panel.add(buttons[i][j]);
                }
                buttons[i][j].setText("");
                availableButtons[i][j] = (i != 0 || j != 0) && (i != boardSize-1 || j != boardSize-1);
            }
        }
        buttons[0][0].setText("S");
        buttons[boardSize-1][boardSize-1].setText("*");
        buttons[boardSize-1][boardSize-1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                answers = new int[boardSize][boardSize];
            }
        });
        buttons[0][0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (canBeSaved){
                    setWholeButtonsText();

                    FileWriter fileWriter = new FileWriter(path);
                    fileWriter.appendToFile(new LevelContent(levelNumber, boardSize, solution, buttonsText));
                }
            }
        });
    }

    public int giveNextNumber(){
        if (currentNumber != boardSize*boardSize-3){
            currentNumber = currentNumber+1;
            return currentNumber;
        }
        else{
            currentNumber = boardSize*boardSize-2;
            return currentNumber;
        }
    }

    public void setArrow(int x, int y){ //logika wstawiania strzalek, tworzenie poprawnego rozwiazania
        String arrow = "";

        if (previousX == -1){
            if (x>0 && y == 0){
                arrow = "V";
            }
            else if (y>0 && x == 0){
                arrow = ">";
            }
            else{
                arrow = "XXX";
                isSolutionCorrect = false;
            }
            buttons[0][0].setText(buttons[0][0].getText() + " " + arrow);
        }
        else if (x < previousX && y == previousY){
            arrow = "^";
        }
        else if (x > previousX && y == previousY){
            arrow = "V";
        }
        else if (y < previousY && x == previousX){
            arrow = "<";
        }
        else if (y > previousY && x == previousX){
            arrow = ">";
        }
        else{
            arrow = "XXX";
            isSolutionCorrect = false;
        }

        if (previousX != -1){
            buttons[previousX][previousY].setText(buttons[previousX][previousY].getText() + arrow);
        }

        if (currentNumber == boardSize*boardSize-2){
            if (x == boardSize-1){
                arrow = ">";
            }
            else if (y == boardSize-1){
                arrow = "V";
            }

            buttons[x][y].setText(buttons[x][y].getText() + "" + arrow);
        }
    }

    public void setWholeButtonsText(){ //tworzenie buttonsText bez liczb
        String text = "";
        for (int i = 0; i < boardSize; i++ ){
            for (int j = 0; j < boardSize; j++){
                text = buttons[i][j].getText().replaceAll("\\d","");
                System.out.println(text);
                buttonsText[i][j] = text;
            }
        }
        setWholeSolution();
    }

    public void setWholeSolution(){ //tworzenie solution bez strzalek
        String numberString = "";
        int number = 0;
        for (int i = 0; i < boardSize; i++ ){
            for (int j = 0; j < boardSize; j++){
                numberString = buttons[i][j].getText().replaceAll("\\D",""); //tylko liczby
                try {
                    number = Integer.parseInt(numberString);
                } catch(NumberFormatException ignored){ }
                System.out.println(number);
                solution[i][j] = number;
            }
        }
        solution[boardSize-1][boardSize-1] = 0;
    }


    class Element implements ActionListener{ //logika przyciskow zblizona do tej z klasy Board
        int i;
        int j;

        public Element(int i, int j){
            this.i = i;
            this.j = j;
        }

        public void actionPerformed(ActionEvent e) {
            if (availableButtons[i][j]){
                int nextNumber = giveNextNumber();
                buttons[i][j].setText(nextNumber + " " + buttons[i][j].getText());
                answers[i][j] = nextNumber;
                availableButtons[i][j] = false;

                if (nextNumber == boardSize*boardSize-2){ //po wypelnieniu wszystkich pól mozna zapisac plansze
                    if (isSolutionCorrect){ //poprawnosc rozlozenia strzalek
                        canBeSaved = true;
                    }
                }

                if (i != previousX || j != previousY){ //wykorzystywane podczas stawiania strzalek
                    setArrow(i,j);
                    previousX = i;
                    previousY = j;
                    System.out.println("");
                }
            }

            if (i == boardSize-1 && j == boardSize-1){ //czyszczenie
                makeBoard(true);
                previousX = -1;
                previousY = -1;
                isSolutionCorrect = true;
            }
        }
    }
}
