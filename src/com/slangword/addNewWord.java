package com.slangword;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class addNewWord extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField wordField;
    private JTextArea definitionField;

    public addNewWord(slangWord Dictionary) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Thêm từ mới");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(Dictionary);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(slangWord Dictionary) {
        // add your code here
        int type;
        if (wordField.getText().isEmpty())
            type = 0;
        else if(definitionField.getText().isEmpty())
            type = 1;
        else
            type = 2;
        Notification ok = new Notification(type);
        ok.pack();
        ok.setLocationRelativeTo(this);
        ok.setVisible(true);
        if (type == 2){
            try {
                Dictionary.addWord(wordField.getText(),definitionField.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
