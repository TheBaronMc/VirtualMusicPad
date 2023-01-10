package com.example.virtualmusicpad;

import java.io.File;

public class Sample {
    String first;
    String second;
    String third;

    public Sample(){
        this.first=null;
        this.second=null;
        this.third=null;
    }

    public static File soundpacksFolder;

    public static void main(String[] args) {
        soundpacksFolder = new File("soundpacks");

        System.out.println(soundpacksFolder.getAbsolutePath() + File.separator);
        //System.out.println(soundpacksFolder.toPath());
    }
}
