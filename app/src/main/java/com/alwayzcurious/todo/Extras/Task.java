package com.alwayzcurious.todo.Extras;

/**
 * Created by vivek on 4/10/2017.
 */

public class Task{

    int dateDay;
    int dateMonth;
    int dateYear,postTaskRepetition,preTaskRepetition;
    String identity;

    public int getPostTaskRepetition() {
        return postTaskRepetition;
    }

    public boolean isPreTaskRepetiton() {

        return isPreTaskRepetiton;
    }

    public void setIsPreTaskRepetiton(boolean preTaskRepetiton) {
        isPreTaskRepetiton = preTaskRepetiton;
    }

    public int getIsPostTaskRepetition() {
        return postTaskRepetition;
    }

    public void setPostTaskRepetition(int postTaskRepetition) {
        this.postTaskRepetition = postTaskRepetition;
    }

    public int getPreTaskRepetition() {
        return preTaskRepetition;
    }

    public void setPreTaskRepetition(int preTaskRepetition) {
        this.preTaskRepetition = preTaskRepetition;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    boolean isPreTaskRepetiton;

    public boolean isPostTaskRepition() {
        return isPostTaskRepition;
    }

    public void setIsPostTaskRepition(boolean postTaskRepition) {
        isPostTaskRepition = postTaskRepition;
    }

    boolean isPostTaskRepition;

    public int getDateDay() {
        return dateDay;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public int getDateYear() {
        return dateYear;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTimeHr() {
        return timeHr;
    }

    public void setTimeHr(int timeHr) {
        this.timeHr = timeHr;
    }

    public int getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    int timeHr;
    int timeMin;
    int timeSec;


    String location,title,description;
    
}
