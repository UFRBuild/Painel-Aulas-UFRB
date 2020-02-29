package com.ufrbuild.mh4x0f.painelufrb.ui.activity.main.schedule.models;

import java.util.Calendar;

public class Week {
    private String dayName;
    private boolean status;
    private int day;

    public Week(String dayName,int day, boolean status) {
        this.dayName = dayName;
        this.status = status;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }


    public int getCurrentDayWeek(){
        // 2 is segunda and 7 is sabado
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public boolean isCurrentDay(SemanaEnum day) {
        if (this.getCurrentDayWeek() == day.getValor()){
            return true;
        }
        return false;
    }
}
