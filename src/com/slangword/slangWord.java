package com.slangword;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class slangWord {
    private final HashMap<String, String> slangWord = new HashMap<>();

    private void pushSlang(String Key, String Mean){
        slangWord.put(Key, Mean);
    }

    public void readSlang() throws IOException {
        try {
            BufferedReader slangDic = new BufferedReader(new FileReader("slang.txt"));
            String Line;
            while (true) {
			    Line = slangDic.readLine();
			    if (Line==null)
				    break;
            stringSolve(Line);
            }
            slangDic.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void stringSolve(String str){
        String [] result = str.split(Pattern.quote("`"));
        pushSlang(result[0], result[1]);
    }

    public void getSlang(String key){
        System.out.println(slangWord.get(key));
    }

    public void findSlang(){
        System.out.println("Nhap tu can tra:");
        Scanner readWord = new Scanner(System.in);
        String key = readWord.nextLine();
        if (slangWord.containsKey(key)) {
            getSlang(key);
        }
        else{
            System.out.print("Khong tim thay tu nay");
        }
        readWord.close();
    }
}
