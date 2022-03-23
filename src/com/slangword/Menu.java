package com.slangword;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Menu extends JFrame{
    private JPanel contentPane;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton historyButton;
    private JButton addWordButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton exitButton;
    private JList<String> listWord;
    private JTextField definition;

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
        String [] listValue = new String[data.size()];
        AtomicInteger i = new AtomicInteger();
        data.forEach((key, value) -> {
            listKey[i.get()] = key;
            listValue[i.get()]= value;
            i.getAndIncrement();
        });
        listWord.setListData(listKey);


        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSearch();
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
                onSelectWord(listValue[listWord.getSelectedIndex()]);
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
    private void onSearch(){

    }
    private void onExit(){

    }

}
