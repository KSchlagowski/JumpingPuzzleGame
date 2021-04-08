package Menus;

import Board.NewLevelMaker;
import FileOperator.FileReader;
import FileOperator.FileSelector;
import Models.LevelContent;
import Windows.Help;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JFrame{
    JPanel panel = new JPanel();

    JButton playButton = new JButton("Graj"),
            editButton = new JButton("Stwórz nową planszę"),
            fileButton = new JButton("Wybierz plik zapisu"),
            helpButton = new JButton("Pomoc");

    String path = "levels.txt";

    ArrayList<LevelContent> levelsList;

    public MainMenu(){
        panel.setLayout(new GridLayout(2,3,20,20));
        initialize();
        addButtons();
        add(panel);
    }

    private void initialize(){
        setLocation(550,400);
        setSize(600,400);
        setVisible(true);
    }

    private void addButtons(){
        addButtonsActions();
        panel.add(playButton);
        panel.add(editButton);
        panel.add(fileButton);
        panel.add(helpButton);
    }

    private void addButtonsActions(){
        playButton.setBackground(Color.green);
        playButton.addActionListener(ae -> {
            new LevelSelectMenu(path);
        });
        editButton.addActionListener(ae -> new NewLevelMaker(path));
        fileButton.addActionListener(ae -> {
            try {
                path = new FileSelector().getFilePath();
                System.out.println("Path changed to " + path);
                FileReader fileReader = new FileReader(path);
                levelsList = fileReader.readFile();
            } catch (Exception ignored) {
            }
        });
        helpButton.addActionListener(ae -> new Help());
    }
}
