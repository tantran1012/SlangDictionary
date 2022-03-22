package com.slangword;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class slangWord {
    private final HashMap<String, String> slangWord = new HashMap<>();
    private final LinkedHashSet<String> SlangHistory = new LinkedHashSet<>();
    private final LinkedHashSet<String> DefinitionHistory = new LinkedHashSet<>();

    private void pushSlang(String Key, String Mean){
        slangWord.put(Key, Mean);
    }

    public void readSlang() throws IOException {
        try {
            BufferedReader slangDic = new BufferedReader(new FileReader("slang.txt"));
            while (true) {
			    String Line = slangDic.readLine();
			    if (Line==null)
				    break;
            stringSolve(Line);
            }
            slangDic.close();
        } catch (FileNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeSlang() throws IOException {
        try {
            BufferedWriter slangDic = new BufferedWriter(new FileWriter("slang.txt"));
            AtomicInteger i = new AtomicInteger();
            int size = slangWord.size();
            slangWord.forEach((key, value) -> {
                String Line = key + "`" + value;
                try {
                    i.getAndIncrement();
                    slangDic.write(Line);
                    if (i.get() < size)
                        slangDic.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            slangDic.close();
        } catch (FileNotFoundException e) {
            //Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void stringSolve(String str){
        String [] result = str.split(Pattern.quote("`"));
        pushSlang(result[0], result[1]);
    }

    public String getSlang(String key){
        return slangWord.get(key);
    }

    public void findSlang(){
        System.out.print("Nhap tu can tra:");
        Scanner readWord = new Scanner(System.in);
        String key = readWord.nextLine();
        if (slangWord.containsKey(key)) {
            System.out.println(getSlang(key));
            addToHistory(key, SlangHistory);
        }
        else{
            System.out.print("Khong tim thay tu nay");
        }
        readWord.close();
    }

    public void findKeyByMean(){
        System.out.print("Nhap tu can tra:");
        Scanner readWord = new Scanner(System.in);
        String definition = readWord.nextLine();
        AtomicReference<Boolean> isExist = new AtomicReference<>(false);
        System.out.println("Danh sach cac tu chua \"" + definition + "\":");
        slangWord.forEach((key, value) -> {
            if(value.contains(definition)) {
                System.out.println(key + " : " + value);
                addToHistory(definition, DefinitionHistory);
                isExist.set(true);
            }
        });
        if (!isExist.get())
            System.out.println("Khong tim thay tu nay");
        readWord.close();
    }

    private void addToHistory(String Str, LinkedHashSet<String> history){
        history.add(Str);
    }

    private void viewHistory(LinkedHashSet<String> history){
        System.out.println("Cac tu da tra:");
        for (String word : history) {
            System.out.println(word);
        }
    }

    public void chooseHistory(){
        System.out.println("Chon lich su tra");
        System.out.println("1. Lich su tra tu");
        System.out.println("2. Lich su tra dinh nghia");
        Scanner sc = new Scanner(System.in);
        int choose = sc.nextInt();
        if (choose == 1)
            viewHistory(SlangHistory);
        else if (choose == 2)
            viewHistory(DefinitionHistory);
        else
            System.out.println("vui long nhap dung thao tac");
    }

    private void writeHistory(LinkedHashSet<String> history, String type){
        //this function is using for saving history to file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(type));
            AtomicInteger i = new AtomicInteger();
            int size = history.size();
            for (String str : history){
                bw.write(str);
                i.getAndIncrement();
                if (i.get() < size)
                    bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readHistoryFromFile(){
        //this function is using for reading history from file
        try {
            BufferedReader SlangHis = new BufferedReader(new FileReader("SlangHistory.txt"));
            BufferedReader DefinitionHis = new BufferedReader(new FileReader("DefinitionHistory.txt"));
            while (true) {
                String SH = SlangHis.readLine();
                if (SH==null)
                    break;
                SlangHistory.add(SH);
            }
            while (true) {
                String DH = DefinitionHis.readLine();
                if (DH==null)
                    break;
                SlangHistory.add(DH);
            }
            SlangHis.close();
            DefinitionHis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSlang() throws IOException {
        String Word, Definition;
        Scanner scan = new Scanner(System.in);
        System.out.print("Nhap tu:");
        Word = scan.nextLine();
        System.out.print("Nhap dinh nghia:");
        Definition = scan.nextLine();
        addWord(Word, Definition);
        pushSlang(Word,Definition);
        scan.close();
    }

    private void addWord(String word, String definition) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter("slang.txt",true));
        br.newLine();
        String line = word + "`" + definition;
        br.write(line);
        br.close();
    }

    public void editSlang(){
        System.out.print("Nhap tu can chinh sua: ");
        Scanner scan = new Scanner(System.in);
        String slg, definition;
        String word = scan.nextLine();
        if (slangWord.containsKey(word)) {
            System.out.println(word + ": " + getSlang(word));
            System.out.print("Nhap noi dung slang muon sua:");
            slg = scan.nextLine();
            System.out.print("Nhap noi dung dinh nghia muon sua:");
            definition = scan.nextLine();
            slangWord.remove(word);
            slangWord.put(slg,definition);
        }
        else {
            System.out.println("Khong co tu nay trong tu dien");
        }
        scan.close();
        try {
            writeSlang();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
