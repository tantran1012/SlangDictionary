package com.slangword;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class slangWord {
    private final LinkedHashMap<String, String> slangWord = new LinkedHashMap<>();  //Lưu danh sách các slang Word
    private final LinkedHashSet<String> SlangHistory = new LinkedHashSet<>(); //Lưu lịch sử tìm kiếm

    //Lấy cả danh sách slang Word
    public LinkedHashMap<String, String> getSlangWord() {
        return slangWord;
    }

    //lấy lịch sử tìm kiếm, kết quả xuất ra Vector<>
    public Vector<String> getSlangHistory() {
        return new Vector<>(SlangHistory);
    }

    //thêm từ mới vào slang word
    private void pushSlang(String Key, String Mean){
        slangWord.put(Key, Mean);
    }

    //đọc slang Word từ file
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

    //ghi slang word vào file
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

    //xử lý chuỗi khi đọc từ file
    private void stringSolve(String str){
        String [] result = str.split(Pattern.quote("`"));
        result[1] = result[1].replace("| ", "\n");
        result[1] = result[1].replace("|", "\n");
        pushSlang(result[0], result[1]);
    }

    //Lấy một slang word theo key đưa vào
    public String getSlang(String key){
        return slangWord.get(key);
    }

    //thêm một từ Str vào lịch sử tìm kiếm
    public void addToHistory(String Str){
        SlangHistory.add(Str);
    }

    //Thêm từ mới vào danh sách slang word
    //Từ mới thêm sẽ nằm ở dưới cùng
    public void addWord(String word, String definition) throws IOException {
        pushSlang(word.toUpperCase(), definition);
        BufferedWriter br = new BufferedWriter(new FileWriter("slang.txt",true));
        br.newLine();
        AtomicReference<String> line = new AtomicReference<>(word.toUpperCase() + "`" + definition);
        line.set(line.get().replace("\n","|"));
        br.write(line.get());
        br.close();
    }

    //Chỉnh sửa một slang word
    //Từ vừa sửa sẽ nằm ở dưới cùng
    public void editSlang(String oldWord, String word, String definition){
        slangWord.remove(oldWord);
        pushSlang(word.toUpperCase(), definition);
        try {
            writeSlang();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Xóa slang word
    public void deleteSlang(String word){
        slangWord.remove(word);
        try {
            writeSlang();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //reset slang word, lấy từ file slangReset.txt copy sang slang.txt
    public void reset() throws IOException {
        BufferedReader slangReset = new BufferedReader(new FileReader("slangReset.txt"));
        BufferedWriter slangDic = new BufferedWriter(new FileWriter("slang.txt"));
        String lineFirst = slangReset.readLine();
        slangDic.write(lineFirst);
        while (true) {
            String line = slangReset.readLine();
            if (line == null)
                break;
            slangDic.newLine();
            slangDic.write(line);
        }
        slangReset.close();
        slangDic.close();
        slangWord.clear();
        readSlang();
    }

    //Lấy từ ngẫu nhiên trong danh sách slang word
    public String randomWord(){
        Vector<String> listKey = new Vector<>(slangWord.keySet());
        Random r = new Random();
        return listKey.get(r.nextInt(slangWord.size()));
    }
}
