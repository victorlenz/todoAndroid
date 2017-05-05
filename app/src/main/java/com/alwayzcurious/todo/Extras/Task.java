package com.alwayzcurious.todo.Extras;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vivek on 4/10/2017.
 */

public class Task {

    int dateDay;
    int dateMonth;
    int dateYear,preTaskRepetitionMinutes;
    String identity,title,description,location;
    int id;
    int timeHr;
    int timeMin;
    int timeSec;
    int preTaskFrequency;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPreTaskFrequency() {
        return preTaskFrequency;
    }

    public void setPreTaskFrequency(int preTaskFrequency) {
        this.preTaskFrequency = preTaskFrequency;
    }

    public String getTitle() {

        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public int getTimeSec() {

        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public int getTimeHr() {
        return timeHr;
    }

    public void setTimeHr(int timeHr) {
        this.timeHr = timeHr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getPreTaskRepetitionMinutes() {
        return preTaskRepetitionMinutes;
    }

    public void setPreTaskRepetitionMinutes(int preTaskRepetitionMinutes) {
        this.preTaskRepetitionMinutes = preTaskRepetitionMinutes;
    }

    public int getDateYear() {
        return dateYear;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public int getDateDay() {
        return dateDay;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
