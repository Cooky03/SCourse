package com.example.myapplication.entity;

import java.io.Serializable;

public class Courseinfo implements Serializable {
    private int Cid;
    private int Score;
    private int Stid;
    private int Mtid;
    private String Cname;
    private String Stname;

    public Courseinfo(String Cname, String Stname, int Cid, int Score, int Stid, int Mtid){
        this.Cname = Cname;
        this.Stname = Stname;
        this.Cid = Cid;
        this.Score = Score;
        this.Stid = Stid;
        this.Mtid = Mtid;
    }

    public int getCid() {
        return Cid;
    }

    public void setCid(int cid) {
        Cid = cid;
    }

    public String getStname() {
        return Stname;
    }

    public void setStname(String stname) {
        Stname = stname;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getStid() {
        return Stid;
    }

    public void setStid(int stid) {
        Stid = stid;
    }

    public int getMtid() {
        return Mtid;
    }

    public void setMtid(int mtid) {
        Mtid = mtid;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public Courseinfo(){
    }
}
