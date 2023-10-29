package com.example.myapplication.entity;

import java.io.Serializable;

public class Applyinfo implements Serializable {
    private int Sid;
    private int Cid;
    private String Process;
    private String Reason;
    private String Stname;
    private String Cname;
    private String Sname;

    private int Score;

    public String getStname() {
        return Stname;
    }

    public void setStname(String stname) {
        Stname = stname;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public int getCid() {
        return Cid;
    }

    public void setCid(int cid) {
        Cid = cid;
    }

    public String getProcess() {
        return Process;
    }

    public void setProcess(String process) {
        Process = process;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }



    public Applyinfo(int Sid, int Cid, String Process, String Reason){
        this.Sid = Sid;
        this.Cid = Cid;
        this.Process = Process;
        this.Reason = Reason;
    }

    public Applyinfo(){
    }
}
