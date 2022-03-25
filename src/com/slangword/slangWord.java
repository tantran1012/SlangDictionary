package com.slangword;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class slangWord {
    private final LinkedHashMap<String, String> slangWord = new LinkedHashMap<>();
    private final LinkedHashSet<String> SlangHistory = new LinkedHashSet<>();

    public LinkedHashMap<String, String> getSlangWord() {
        return slangWord;
    }

    public Vector<String> getSlangHistory() {
        return new Vector<>(SlangHistory);
    }

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
                AtomicReference<String> line = new AtomicReference<>(key + "`" + value);
                line.set(line.get().replace("\n","|"));
                try {
                    i.getAndIncrement();
                    slangDic.write(line.get());
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
        result[1] = result[1].replace("| ", "\n");
        result[1] = result[1].replace("|", "\n");
        pushSlang(result[0], result[1]);
    }

    public String getSlang(String key){
        return slangWord.get(key);
    }

    public void addToHistory(String Str){
        SlangHistory.add(Str);
    }


    public void addWord(String word, String definition) throws IOException {
        pushSlang(word, definition);
        BufferedWriter br = new BufferedWriter(new FileWriter("slang.txt",true));
        br.newLine();
        AtomicReference<String> line = new AtomicReference<>(word + "`" + definition);
        line.set(line.get().replace("\n","|"));
        br.write(line.get());
        br.close();
    }

    public void editSlang(String oldWord, String word, String definition){
        slangWord.remove(oldWord);
        pushSlang(word, definition);
        try {
            writeSlang();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
