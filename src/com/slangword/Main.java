package com.slangword;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Menu showUI = new Menu();
        showUI.pack();
        showUI.setLocationRelativeTo(null);
        showUI.setVisible(true);
    }
}
