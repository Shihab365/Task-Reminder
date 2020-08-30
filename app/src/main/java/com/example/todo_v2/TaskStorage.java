package com.example.todo_v2;

public class TaskStorage {
    String ID,taskday,taskmonth,taskname,tasktime,cirColor,alarmDate,alarmTime2;
    String alarmID;

    public TaskStorage(String ID,String taskday, String taskmonth, String taskname, String tasktime,String cirColor,
                       String alarmDate,String alarmTime2,String alarmID) {
        this.taskday = taskday;
        this.taskmonth = taskmonth;
        this.taskname = taskname;
        this.tasktime = tasktime;
        this.ID=ID;
        this.cirColor=cirColor;
        this.alarmDate=alarmDate;
        this.alarmTime2=alarmTime2;
        this.alarmID=alarmID;
    }
    public TaskStorage(){}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTaskday() {
        return taskday;
    }

    public void setTaskday(String taskday) {
        this.taskday = taskday;
    }

    public String getTaskmonth() {
        return taskmonth;
    }

    public void setTaskmonth(String taskmonth) {
        this.taskmonth = taskmonth;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTasktime() {
        return tasktime;
    }

    public void setTasktime(String tasktime) {
        this.tasktime = tasktime;
    }

    public String getCirColor() {
        return cirColor;
    }

    public void setCirColor(String cirColor) {
        this.cirColor = cirColor;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmTime2() {
        return alarmTime2;
    }

    public void setAlarmTime2(String alarmTime2) {
        this.alarmTime2 = alarmTime2;
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }
}
