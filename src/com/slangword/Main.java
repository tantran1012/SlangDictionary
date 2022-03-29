package com.slangword;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Menu showUI = new Menu();
        showUI.pack();
        showUI.setLocationRelativeTo(null);
        showUI.setVisible(true);
    }
}
