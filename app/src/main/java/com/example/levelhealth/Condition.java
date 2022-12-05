package com.example.levelhealth;

public class Condition {

    public String day;
    public int mood;
    public int sleep;
    public int headache;
    public int user_id;

    public Condition() {}

    public Condition(String date, int mood, int sleep, int headache, int user_id) {
        this.day = date;
        this.mood = mood;
        this.sleep = sleep;
        this.headache = headache;
        this.user_id = user_id;
    }

    public String unwrap() {
        return "Condition{date="+day+
                ", mood="+mood+
                ", sleep="+sleep+
                ", headache="+headache+
                ", user_id="+user_id+"}";
    }

    public String getDate() {
        return day;
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

    public int getUser_id() {
        return user_id;
    }

    public void setDate(String date) {
        this.day = date;
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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}