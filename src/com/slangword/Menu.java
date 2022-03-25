package com.slangword;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JLabel search;
    private JPanel toolsPane;
    private JButton backButton;
    private JPanel backPane;
    private JButton resetButton;

    public Menu(){
        setContentPane(contentPane);
        setTitle("Tra từ lóng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultLookAndFeelDecorated(true);
        slangWord Dictionary = new slangWord();
        try {
            Dictionary.readSlang();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vector<String> listKey = getList(Dictionary);
        listWord.setListData(listKey);
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

        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listWord.setListData(Dictionary.getSlangHistory());
                toolsPane.setVisible(false);
                backPane.setVisible(true);
            }
        });

        addWordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewWord dialog = new addNewWord(Dictionary);
                dialog.pack();
                dialog.setLocationRelativeTo(definition);
                dialog.setVisible(true);
                listWord.setListData(getList(Dictionary));
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editWord dialog = new editWord(Dictionary, listWord.getSelectedValue(), Dictionary.getSlang(listWord.getSelectedValue()));
                dialog.pack();
                dialog.setLocationRelativeTo(definition);
                dialog.setVisible(true);
                listWord.setListData(getList(Dictionary));
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmDialog dialog = new confirmDialog(Dictionary, listWord.getSelectedValue(), 0);
                dialog.pack();
                dialog.setLocationRelativeTo(definition);
                dialog.setVisible(true);
                listWord.setListData(getList(Dictionary));
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });


        listWord.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                definition.setText(Dictionary.getSlang(listWord.getSelectedValue()));
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                if (!searchTextField.getText().isEmpty())
                    Dictionary.addToHistory(listWord.getSelectedValue());
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText().isEmpty())
                    listWord.setListData(listKey);
                else
                    onSearch(Dictionary, searchTextField.getText());
                backPane.setVisible(false);
                toolsPane.setVisible(true);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmDialog dialog = new confirmDialog(Dictionary, null, 1);
                dialog.pack();
                dialog.setLocationRelativeTo(definition);
                dialog.setVisible(true);
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                listWord.setListData(getList(Dictionary));
            }
        });
    }

    public Vector<String> getList(slangWord Dic){
        return new Vector<>(Dic.getSlangWord().keySet());
    }

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
