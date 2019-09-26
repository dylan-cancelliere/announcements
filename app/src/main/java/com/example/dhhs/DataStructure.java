package com.example.dhhs;

import java.util.ArrayList;

public class DataStructure{
    private String heading;
    private String info;

    public DataStructure(String $heading, String $info){

        heading = $heading;
        info = $info;
    }

    public String getHeading(){
        return heading;
    }
    public String getInfo(){
        return info;
    }

    public boolean contains(String q){
        for (int i = 0; i < info.length(); i++){
            if (info.substring(i,i+1).equals(q)) return true;
        }
        return false;
    }

    public void change(String q){
        info = q;
    }

    public void add(String q){
        info += "\n" + q;
    }

    public String toString(){
        return heading + '\n' + info;
    }
}
