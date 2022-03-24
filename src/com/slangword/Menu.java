package com.slangword;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Menu extends JFrame{
    private JPanel contentPane;
    private JTextField searchTextField;
    private JButton historyButton;
    private JButton addWordButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton exitButton;
    private JList<String> listWord;
    private JTextArea definition;
    private JLabel search;

    public Menu(){
        setContentPane(contentPane);
        setTitle("Tra từ Lóng");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        slangWord Dictionary = new slangWord();
        try {
            Dictionary.readSlang();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LinkedHashMap<String, String> data = Dictionary.getSlangWord();
        String [] listKey = new String[data.size()];
        AtomicInteger i = new AtomicInteger();
        data.forEach((key, value) -> {
            listKey[i.get()] = key;
            i.getAndIncrement();
        });
        listWord.setListData(listKey);
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearch(Dictionary, searchTextField.getText(), listKey);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearch(Dictionary, searchTextField.getText(), listKey);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearch(Dictionary, searchTextField.getText(), listKey);
            }
        });

        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onHistory();
            }
        });

        addWordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddWord();
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onEdit();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        });

        listWord.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                onSelectWord(Dictionary.getSlang(listWord.getSelectedValue()));
            }
        });

    }

    private void onSelectWord(String Definition) {
        definition.setText(Definition);
    }

    private void onHistory(){

    }
    private void onAddWord(){

    }
    private void onEdit(){

    }
    private void onSearch(slangWord Dic, String text, String[] Default){
        Vector<String> listW = new Vector<>();
        if (Dic.getSlangWord().containsKey(text)) {
            listW.add(text);
        }
        else {
            AtomicInteger i = new AtomicInteger();
            Dic.getSlangWord().forEach((key, value) -> {
                if(value.toLowerCase().contains(text.toLowerCase())) {
                    listW.add(key);
                    i.getAndIncrement();
                }
            });
        }
        if(listW.isEmpty())
            listWord.setListData(Default);
        else
            listWord.setListData(listW);
    }
    private void onExit(){

    }

}
