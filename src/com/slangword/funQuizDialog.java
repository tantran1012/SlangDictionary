package com.slangword;

import javax.swing.*;
import java.awt.event.*;

public class funQuizDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel thongBao;
    private JLabel dapAnDung;
    private boolean tiepTuc;

    public funQuizDialog(String trueAnswer, boolean answerType) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        if (answerType)
            setTitle("Chúc mừng");
        else
            setTitle("Sai rồi");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        if(answerType) {
            thongBao.setText("Bạn trả lời đúng rồi, xin chúc mừng!! đáp án chính là");
        }
        else{
            thongBao.setText("Bạn trả lời sai rồi, tiếc thật!! đáp án là");
        }

        dapAnDung.setText(trueAnswer);

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

    private void onOK() {
        // add your code here
        tiepTuc = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        tiepTuc = false;
        dispose();
    }

    public boolean isContinue(){
        return tiepTuc;
    }
}
