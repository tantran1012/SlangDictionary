package com.slangword;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class Menu extends JFrame{
    private JPanel contentPane;
    private JTextField searchTextField;
    private JButton historyButton;
    private JButton addWordButton;
    private JButton editButton;
    private JButton deleteButton;
    private JList<String> listWord;
    private JTextArea definition;
    private JPanel toolsPane;
    private JButton backButton;
    private JPanel backPane;
    private JButton resetButton;
    private JButton randomWordButton;
    private JPanel toolsPane2;
    private JButton quiz1Button;
    private JButton quiz2Button;

    public Menu(){
        setContentPane(contentPane);
        setTitle("Tra Slang Word");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        slangWord Dictionary = new slangWord();
        try {
            Dictionary.readSlang();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vector<String> listKey = getList(Dictionary);
        listWord.setListData(listKey);

        //Ô tìm kiếm, kết quả sẽ thay đổi khi nhập dữ liệu vào ô tìm kiếm
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearch(Dictionary, searchTextField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearch(Dictionary, searchTextField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearch(Dictionary, searchTextField.getText());
            }
        });

        //Danh sách slang word được thêm vô list để hiển thị ra ngoài
        listWord.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                definition.setText(Dictionary.getSlang(listWord.getSelectedValue()));
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                if (!searchTextField.getText().isEmpty())
                    Dictionary.addToHistory(listWord.getSelectedValue());
                if (listWord.getSelectedValue() == null){
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }

            }
        });

        ///////////////Các listener của các button///////////////////
        //Lịch sử tìm kiếm
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listWord.setListData(Dictionary.getSlangHistory());
                toolsPane.setVisible(false);
                backPane.setVisible(true);
                toolsPane2.setVisible(false);
            }
        });

        //Thêm từ mới
        addWordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewWord dialog = new addNewWord(Dictionary);
                dialog.pack();
                dialog.setLocationRelativeTo(contentPane);
                dialog.setVisible(true);
                listWord.setListData(getList(Dictionary));
            }
        });

        //Chỉnh sửa từ được chọn
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editWord dialog = new editWord(Dictionary, listWord.getSelectedValue(), Dictionary.getSlang(listWord.getSelectedValue()));
                dialog.pack();
                dialog.setLocationRelativeTo(contentPane);
                dialog.setVisible(true);
                listWord.setListData(getList(Dictionary));
            }
        });

        //Xóa từ được chọn
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmDialog dialog = new confirmDialog(Dictionary, listWord.getSelectedValue(), 0);
                dialog.pack();
                dialog.setLocationRelativeTo(contentPane);
                dialog.setVisible(true);
                listWord.setListData(getList(Dictionary));
            }
        });

        //Trở về (hiển thị khi đang xem lịch sử tìm kiếm)
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText().isEmpty())
                    listWord.setListData(listKey);
                else
                    onSearch(Dictionary, searchTextField.getText());
                backPane.setVisible(false);
                toolsPane.setVisible(true);
                toolsPane2.setVisible(true);
            }
        });

        //Reset slang word
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmDialog dialog = new confirmDialog(Dictionary, null, 1);
                dialog.pack();
                dialog.setLocationRelativeTo(contentPane);
                dialog.setVisible(true);
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                listWord.setListData(getList(Dictionary));
            }
        });

        //Random một slang word
        randomWordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomWord dialog = new randomWord(Dictionary);
                dialog.pack();
                dialog.setLocationRelativeTo(contentPane);
                dialog.setVisible(true);
            }
        });

        //Đố vui 1
        quiz1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                funQuiz quizz = new funQuiz(Dictionary, 0);
                quizz.pack();
                quizz.setLocationRelativeTo(contentPane);
                quizz.setSize(new Dimension(800,380));
                quizz.setVisible(true);
            }
        });

        //Đố vui 2
        quiz2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                funQuiz quizz = new funQuiz(Dictionary, 1);
                quizz.pack();
                quizz.setLocationRelativeTo(contentPane);
                quizz.setSize(new Dimension(800,380));
                quizz.setVisible(true);
            }
        });
    }

    //Lấy danh sách các key
    public Vector<String> getList(slangWord Dic){
        return new Vector<>(Dic.getSlangWord().keySet());
    }

    //Tìm kiếm
    private void onSearch(slangWord Dic, String text){
        Vector<String> listW = new Vector<>();
        Dic.getSlangWord().forEach((key, value) -> {
            if(key.toLowerCase().contains(text.toLowerCase()))
                listW.add(key);
            else if (value.toLowerCase().contains(text.toLowerCase()))
                listW.add(key);
        });
        listWord.setListData(listW);
    }
}
