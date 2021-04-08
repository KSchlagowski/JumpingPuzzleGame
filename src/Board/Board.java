package Board;

import Models.History;
import Models.LevelContent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Board extends JFrame{
    JPanel panel = new JPanel();

    int currentNumber = 0;
    int boardSize;

    boolean[][] availableButtons;
    int[][] answers;
    int[][] solution;
    JButton[][] buttons;
    String[][] buttonsText;
    ArrayList<History> history = new ArrayList<>();

    public Board(LevelContent levelContent) { //gra od nowa
        initialize();
        this.solution = levelContent.solution;
        this.buttonsText = levelContent.buttonsText;
        this.boardSize = levelContent.boardSize;
        availableButtons = new boolean[boardSize][boardSize];
        answers = new int[boardSize][boardSize];
        buttons = new JButton[boardSize][boardSize];
        panel.setLayout(new GridLayout(boardSize,boardSize));

        makeBoard(false);
        add(panel);
    }

    public Board(LevelContent levelContent, int i){ //gra od zapisu
        initialize();
        this.boardSize = levelContent.boardSize;
        this.currentNumber = levelContent.currentNumber;
        this.solution = levelContent.solution;
        this.buttonsText = levelContent.buttonsText;
        this.availableButtons = levelContent.availableButtons;
        this.answers = levelContent.answers;
        this.history = levelContent.history;

        buttons = new JButton[boardSize][boardSize];
        makeBoard(false);
        panel.setLayout(new GridLayout(boardSize,boardSize));
        add(panel);
        loadProgress(levelContent.buttonsTextToSave);
    }

    private void initialize(){
        setLocation(550,400);
        setSize(600,400);
        setVisible(true);

    }

    public void makeBoard(boolean makeOnlyAvailableButtons){ //stworzenie planszy z guzikow oraz ustawienie ich jako "dostępne"/"available" do naciśnięcia
        for (int i = 0; i < boardSize; i++ ){
            for (int j = 0; j < boardSize; j++){
                if (!makeOnlyAvailableButtons) {
                    buttons[i][j] = new JButton();
                    (buttons[i][j]).addActionListener(new Element(i, j));
                    panel.add(buttons[i][j]);
                }
                buttons[i][j].setText(buttonsText[i][j]);
                availableButtons[i][j] = (i != 0 || j != 0) && (i != boardSize-1 || j != boardSize-1);
            }
        }
    }

    public int checkAnswers(){
        int answersCorrectness = 1;
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                if (answers[i][j] != solution[i][j]){
                    answersCorrectness = 0;
                }
            }
        }

        return answersCorrectness;
    }

    public void colorButtons(int isSolutionCorrect){
        for (int i = 0; i < boardSize; i++ ){
            for (int j = 0; j < boardSize; j++){
                if (isSolutionCorrect == 1) {
                    buttons[i][j].setBackground(Color.green);
                }
                else if (isSolutionCorrect == 0){
                    buttons[i][j].setBackground(Color.red);
                }
                else{
                    buttons[i][j].setBackground(new JButton().getBackground());
                }
            }
        }
    }

    public int giveNextNumber(){
        if (currentNumber != boardSize*boardSize-3){
            currentNumber = currentNumber+1;
            return currentNumber;
        }
        else{
            return boardSize*boardSize-2;
        }
    }

    public void saveProgress(){ //bezpośrednie pobranie tekstu z guzików, oraz zapisanie razem z resztą potrzebnych parametrów
        String[][] buttonsTextToSave = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++ ) {
            for (int j = 0; j < boardSize; j++) {
                buttonsTextToSave[i][j] = buttons[i][j].getText();
            }
        }
        LevelContent levelSave = new LevelContent(boardSize, currentNumber, solution, buttonsText, availableButtons, answers, history, buttonsTextToSave);
        levelSave.levelContentQuicksave(levelSave);
    }

    public void loadProgress(String[][] buttonsTextToLoad){
        for (int i = 0; i < boardSize; i++ ) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j].setText(buttonsTextToLoad[i][j]);
            }
        }
        System.out.println("Progress loaded.");
    }


    class Element implements ActionListener{ //logika przycisków
        int i;
        int j;

        public Element(int i, int j){
            this.i = i;
            this.j = j;
        }

        public void actionPerformed(ActionEvent e) {
            if (availableButtons[i][j]){ //kazdy guzik wczesniej nienacisniety nie liczac S i *
                int nextNumber = giveNextNumber(); //dodanie odpowiedzi
                buttons[i][j].setText(nextNumber + " " + buttons[i][j].getText());
                answers[i][j] = nextNumber;
                availableButtons[i][j] = false;
                history.add(new History(i,j,nextNumber));

                if (nextNumber == boardSize*boardSize-2){ //sprawdzenie poprawnosci rozwiazania podczas nacisniecia ostatniego wolnego przycisku
                    colorButtons(checkAnswers());
                }
            }
            else if (!availableButtons[i][j] && i == (history.get(history.size()-1)).i && j == (history.get(history.size()-1)).j){ //cofanie sie w rozwiazaniu
                currentNumber -= 1;
                buttons[i][j].setText(buttons[i][j].getText().replaceAll("\\d","")); //usuniecie liczby
                answers[i][j] = 0;
                availableButtons[i][j] = true;
                history.remove(history.size()-1);
            }

            if (i == boardSize-1 && j == boardSize-1){ //guzik * czyszczacy plansze
                makeBoard(true);
                history = new ArrayList<>();
                answers = new int[boardSize][boardSize];
                currentNumber = 0;
                colorButtons(-1);
            }
            else if (i == 0 && j == 0){ //guzik S do zapisu
                saveProgress();
                System.out.println("Progress saved.");
            }
        }
    }
}