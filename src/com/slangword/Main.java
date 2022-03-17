package com.slangword;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        slangWord sW = new slangWord();
        sW.readSlang();
//        sW.findSlang();
        sW.findKeyByMean();
    }
}
