package com.example.levelhealth;

public class Day {
    private int id;
    private String weekDay;
    private String mouthDay;

    public Day(int id, String weekDay, String mouthDay) {
        this.id = id;
        this.weekDay = weekDay;
        this.mouthDay = mouthDay;
    }

    public int getId() {
        return id;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getMouthDay() {
        return mouthDay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public void setMouthDay(String mouthDay) {
        this.mouthDay = mouthDay;
    }
}
