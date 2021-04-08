package Menus;

import Board.Board;
import FileOperator.FileReader;
import FileOperator.FileWriter;
import Models.LevelContent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LevelSelectMenu extends JFrame{
    JPanel panel = new JPanel();

    String path;

    JButton[] levelSelectionButtons;

    FileReader fileReader;
    ArrayList<LevelContent> levelsList;

    public LevelSelectMenu(String path){
        this.path = path;
        fileReader = new FileReader(path);
        levelsList = fileReader.readFile();
        levelSelectionButtons = new JButton[levelsList.size()+3];

        initialize();
        addButtons();
        add(panel);
    }

    private void initialize(){
        setLocation(550,400);
        setSize(600,400);
        setVisible(true);

        if (levelsList.isEmpty()){
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.writeFirstLevelToFile();
        }
    }

    private void addButtons(){
        int i = 1;
        levelSelectionButtons[0] = new JButton("Wczytaj");
        levelSelectionButtons[0].addActionListener(new LoadButton());
        panel.add(levelSelectionButtons[0]);

        for (LevelContent lvl : levelsList){
            levelSelectionButtons[i] = new JButton("Level " + lvl.levelNumber);
            levelSelectionButtons[i].addActionListener(ae -> new Board(lvl));
            panel.add(levelSelectionButtons[i]);

            i++;
        }
    }

    class LoadButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FileReader fileReader = new FileReader("quicksave.txt");
            levelsList = fileReader.readFile();
            new Board(levelsList.get(0), 1);
        }
    }
}
