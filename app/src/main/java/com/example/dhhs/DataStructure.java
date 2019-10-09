package com.example.dhhs;

public class DataStructure{
    private String heading;     //Heading or title of a column in the excel doc
    private String info;        //Actual information related to the heading

    public DataStructure(String $heading, String $info){
        //Constructor which populates the data structure when a new data structure is initialized
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
