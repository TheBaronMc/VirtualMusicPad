package com.example.virtualmusicpad;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Pattern {
    public String name;
    public static int index = 1;
    public HashMap<String, Soundpack> soundpacks = new HashMap<>();
    public Soundpack currentSoundpack;
    HashMap<String, String> recordedPattern;

    /*
    public Pattern(String xmlPath){
        XmlHandler.convertXmlToPatternObject(xmlPath);
    }*/

    public Pattern(){
        this.name="PATTERN "+index;
        index++;
        this.soundpacks = new HashMap<>();
        //this.soundpacksUsed = Collections.emptyList();
        //soundpacksUsed.add(HelloApplication.soundpacks.get(0));
        //this.currentSoundpack = soundpacksUsed.get(0);
        //this.currentSoundpack = new Soundpack("drums");
        this.recordedPattern = new HashMap<>();
    }
    /*
    public void moveSoundToRight(){}
    public void moveSoundToLeft(){}
    public void extendSound(){}
    public void shortenSound(){}*/
}
