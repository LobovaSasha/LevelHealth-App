package com.example.levelhealth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Condition {

    public int mood;
    public int sleep;
    public int headache;

    public int idx = -1;

    public Condition() {}

    public Condition(int mood, int sleep, int headache) {
        this.mood = mood;
        this.sleep = sleep;
        this.headache = headache;
    }

    public String unwrap() {
        return "Condition{idx="+idx+
                ", mood="+mood+
                ", sleep="+sleep+
                ", headache="+headache+"}";
    }

    public int getMood() {
        return mood;
    }

    public int getSleep() {
        return sleep;
    }

    public int getHeadache() {
        return headache;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public void setHeadache(int headache) {
        this.headache = headache;
    }

}