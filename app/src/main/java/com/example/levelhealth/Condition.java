package com.example.levelhealth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Condition {

    public String date;
    public int mood;
    public int sleep;
    public int headache;

    public int idx = -1;

    public Condition() {}

    public Condition(String date, int mood, int sleep, int headache) {
        this.date = date;
        this.mood = mood;
        this.sleep = sleep;
        this.headache = headache;
    }

    public String unwrap() {
        return "Condition{date="+date+
                ", mood="+mood+
                ", sleep="+sleep+
                ", headache="+headache+"}";
    }

    public String day() {
        SimpleDateFormat formatter = new SimpleDateFormat("E");
        return formatter.format(date());
    }

    public Date date() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return formatter.parse(date);
        }
        catch (Exception e) {
            return new Date();
        }
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}