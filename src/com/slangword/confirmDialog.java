package com.slangword;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class confirmDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel wordField;
    private JLabel confirmText;

    //type 0: xóa
    //type 1: reset
    public confirmDialog(slangWord Dictionary, String word, int type) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);
        setTitle("Xác nhận");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (type == 0)
                    onOKDelete(Dictionary, word);
                else if (type == 1)
                    onOKReset(Dictionary);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        if (type == 0) {
            confirmText.setText("Bạn muốn xóa từ này");
            wordField.setText(word);
        }
        else if (type == 1) {
            confirmText.setText("Bạn muốn reset từ điển không?");
        }

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

    private void onOKReset(slangWord dictionary) {
        try {
            dictionary.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Notification ok = new Notification(5);
        ok.pack();
        ok.setLocationRelativeTo(this);
        ok.setVisible(true);
        dispose();
    }

    private void onOKDelete(slangWord Dictionary, String word) {
        // add your code here
        Dictionary.deleteSlang(word);
        Notification ok = new Notification(4);
        ok.pack();
        ok.setLocationRelativeTo(this);
        ok.setVisible(true);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
