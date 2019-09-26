package com.example.dhhs;

public class Club {
    String name, day, time, room, advisor, description;

    public Club(String mName, String mDay, String mTime, String mRoom, String mAdvisor, String mDescription){
        name = mName;
        day = mDay;
        time = mTime;
        room = mRoom;
        advisor = mAdvisor;
        description = mDescription;
    }

    public String toString(){
        return "Name: " + name + ", Day: " + day + ", Time: " + time + ", Room: " + room + ", Advisor: " + advisor + "Description: " + description + "\n";
    }

    public void setName(String q){
        name = q;
    }

    public String getName(){
        return name;
    }

    public String getDay(){
        return day;
    }

    public String getTime(){
        return time;
    }

    public String getRoom(){
        return room;
    }

    public String getAdvisor(){
        return advisor;
    }

    public String getDescription(){
        return description;
    }

    public void setDay(String q){
        name = q;
    }

    public void setTime(String q){
        name = q;
    }
    public void setRoom(String q){
        name = q;
    }

    public void setAdvisor(String q){
        name = q;
    }

    public String setDescription(){
        return name;
    }

}
