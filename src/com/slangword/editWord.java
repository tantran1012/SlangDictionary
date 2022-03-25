package com.slangword;

import javax.swing.*;
import java.awt.event.*;

public class editWord extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField wordField;
    private JTextArea definitionField;

    public editWord(slangWord Dictionary, String word, String definition) {
        setContentPane(contentPane);
        setModal(true);
        setTitle("Chỉnh sửa từ");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(Dictionary, word);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        wordField.setText(word);
        definitionField.setText(definition);
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

    private void onOK(slangWord Dictionary, String oldWord) {
        // add your code here
        int type;
        if (wordField.getText().isEmpty())
            type = 0;
        else if(definitionField.getText().isEmpty())
            type = 1;
        else
            type = 3;
        Notification ok = new Notification(type);
        ok.pack();
        ok.setLocationRelativeTo(this);
        ok.setVisible(true);
        if (type == 3){
            Dictionary.editSlang(oldWord, wordField.getText(),definitionField.getText());
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
