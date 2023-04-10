package com.example.levelhealth;

public class Day {
    private int id;
    private String weekDay;
    private String monthDay;

    public Day(int id, String weekDay, String monthDay) {
        this.id = id;
        this.weekDay = weekDay;
        this.monthDay = monthDay;
    }

    public int getId() {
        return id;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getMonthDay() {
        return monthDay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public void setMouthDay(String monthDay) {
        this.monthDay = monthDay;
    }
}
