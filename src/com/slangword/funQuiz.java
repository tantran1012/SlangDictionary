package com.slangword;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class funQuiz extends JFrame{
    private JPanel quizPane;
    private JLabel word;
    private JButton answerA;
    private JButton answerB;
    private JButton answerC;
    private JButton answerD;
    private JButton backButton;
    private JLabel countTrue;
    private JLabel countFalse;
    private JLabel question;
    AtomicInteger demDung = new AtomicInteger();    //Số câu đúng
    AtomicInteger demSai = new AtomicInteger();     //Số câu sai
    String rightWord;
    String rightAnswer;
    Vector<String> wrongAnswer = new Vector<>();

    public funQuiz(slangWord dictionary, int type){
        setContentPane(quizPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        playGame(dictionary, type);
        setTitle("Đố vui");

        //////////////// Các listener cho các đáp án A, B, C, D ////////////////
        answerA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onClick(answerA, rightAnswer))
                    playGame(dictionary, type);
                else {
                    backToMain();
                }
            }
        });
        answerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onClick(answerB, rightAnswer))
                    playGame(dictionary, type);
                else {
                    backToMain();
                }
            }
        });
        answerC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onClick(answerC, rightAnswer))
                    playGame(dictionary, type);
                else {
                    backToMain();
                }
            }
        });
        answerD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onClick(answerD, rightAnswer))
                    playGame(dictionary, type);
                else {
                    backToMain();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMain();
            }
        });
    }

    //Trở về màn hình ban đầu
    private void backToMain()
    {
        dispose();
        Menu showUI = new Menu();
        showUI.pack();
        showUI.setLocationRelativeTo(null);
        showUI.setVisible(true);
    }

    //đặt đáp án sai
    private void setFalse(JButton nut, Vector<String> answer, int i){   ////nut là nút =)))) tiếng việt luôn
        ///stackoverflow, wrap text
        String text = "<html><body style='width: 160px; text-align:center'><p>"
                + answer.get(i).replace("\n", " | ") + "</p></body></html>";
        nut.setText(text);
        nut.setActionCommand(answer.get(i));
    }

    //đặt đáp án đúng
    private void setTrue(JButton nut, String answer){
        ///stackoverflow, wrap text
        String text = "<html><body style='width: 160px; text-align:center'><p>"
                + answer.replace("\n", " | ") + "</p></body></html>";
        nut.setText(text);
        nut.setActionCommand(answer);
    }

    private boolean onClick(JButton answer, String trueAnswer) {
        funQuizDialog dialog;
        if (answer.getActionCommand().equals(trueAnswer)){
            demDung.getAndIncrement();
            countTrue.setText(Integer.toString(demDung.get()));
            dialog = new funQuizDialog(trueAnswer, true);
            //tăng đúng
            //hiện bảng chơi tiếp hay không
        }
        else{
            demSai.getAndIncrement();
            countFalse.setText(Integer.toString(demSai.get()));
            dialog = new funQuizDialog(trueAnswer, false);
            //tăng sai
            //hiện đáp án
            //hiện bảng chơi tiếp hay không
        }
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        return dialog.isContinue();
    }

    //Tạo câu hỏi, bắt đầu chơi
    private void playGame(slangWord dictionary, int type){
        //type == 0, cho từ, chọn nghĩa
        if (type == 0) {
            question.setText("Từ sau đây có nghĩa là gì?");
            rightWord = dictionary.randomWord();
            rightAnswer = dictionary.getSlang(rightWord);
            AtomicInteger i = new AtomicInteger();
            wrongAnswer.clear();
            while (i.get() < 3){
                wrongAnswer.add(dictionary.getSlang(dictionary.randomWord()));
                while (wrongAnswer.get(i.get()).equals(rightAnswer)) {
                    wrongAnswer.set(i.get(), dictionary.getSlang(dictionary.randomWord()));
                }
                if (i.get() > 1) {
                    while (wrongAnswer.get(i.get()).equals(wrongAnswer.get(i.get() - 1))) {
                        wrongAnswer.set(i.get(), dictionary.getSlang(dictionary.randomWord()));
                    }
                }
                i.getAndIncrement();
            }
        }
        //Type == 1, cho nghĩa, chọn từ
        else if(type == 1) {
            question.setText("Từ/câu sau đây viết tắt là gì?");
            rightAnswer = dictionary.randomWord();
            rightWord = dictionary.getSlang(rightAnswer);
            AtomicInteger i = new AtomicInteger();
            wrongAnswer.clear();
            while (i.get() < 3) {
                wrongAnswer.add(dictionary.randomWord());
                while (wrongAnswer.get(i.get()).equals(rightAnswer)) {
                    wrongAnswer.set(i.get(), dictionary.randomWord());
                }
                if (i.get() > 1) {
                    while (wrongAnswer.get(i.get()).equals(wrongAnswer.get(i.get() - 1))) {
                        wrongAnswer.set(i.get(), dictionary.randomWord());
                    }
                }
                i.getAndIncrement();
            }
        }
        countTrue.setText(Integer.toString(demDung.get()));
        countFalse.setText(Integer.toString(demSai.get()));

        //tạo vector các button để dễ đưa vào vòng lặp.
        Vector<JButton> listAnswer = new Vector<>();
        listAnswer.add(answerA);
        listAnswer.add(answerB);
        listAnswer.add(answerC);
        listAnswer.add(answerD);

        //random vị trí đáp án đúng
        Random rand = new Random();
        int exactly = rand.nextInt(4);
        AtomicInteger count = new AtomicInteger();
        AtomicInteger wr = new AtomicInteger();
        for (JButton answer: listAnswer){
            if (count.get() == exactly){
                setTrue(answer, rightAnswer);
            }
            else {
                setFalse(answer, wrongAnswer, wr.get());
                wr.getAndIncrement();
            }
            count.getAndIncrement();
        }
        String text = "<html><body style='width: 100%; text-align:center'><p>"
                + rightWord.replace("\n", " | ") + "</p></body></html>";
        word.setText(text);
    }
}
