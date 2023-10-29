package com.example.myapplication.entity;

import java.io.Serializable;

public class Steacherinfo implements Serializable {
    private String Stname; //主讲教师姓名
    private int Stid; //主讲教师id
    private String Stpassword; //账号密码

    public Steacherinfo(String Stname, int Stid, String Stpassword){
        this.Stname = Stname;
        this.Stid = Stid;
        this.Stpassword = Stpassword;
    }

    public String getStname() {
        return Stname;
    }

    public void setStname(String stname) {
        Stname = stname;
    }

    public int getStid() {
        return Stid;
    }

    public void setStid(int stid) {
        Stid = stid;
    }

    public String getStpassword() {
        return Stpassword;
    }

    public void setStpassword(String stpassword) {
        Stpassword = stpassword;
    }

    public Steacherinfo(){
    }
}
