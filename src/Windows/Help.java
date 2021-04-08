package Windows;

import javax.swing.*;
import java.awt.*;

public class Help extends JFrame {
    JPanel panel = new JPanel();
    JTextArea textField = new JTextArea();

    String textFieldContent = "Zaczynając od pola z literą S i skacząc w następnych ruchach na kolejne pola, " +
            "należy obejść wszystkie pola (goszcząc na każdym tylko raz) i zakończyć skoki na gwiazdce. " +
            "Każda strzałka wskazuje kierunek skoku. W rozwiązaniu obok strzałek powinny znaleźć się liczby oznaczające kolejne skoki. " +
            "W tej grze guzik startu S służy również jako zapis podczas rozgrywki jak również podczas tworzenia własnej " +
            "planszy. Guzik mety * czyści planszę. Cofać swój wybór można również klikając w ostatnio wybrane pole. " +
            "Miłej zabawy! Autor: Kamil Schlagowski.";

    public Help(){
        panel.setLayout(new GridLayout(2,3,20,20));
        initialize();
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setText(textFieldContent);
        panel.add(textField);
        add(panel);
    }

    private void initialize(){
        setLocation(550,400);
        setSize(600,400);
        setVisible(true);
    }
}
