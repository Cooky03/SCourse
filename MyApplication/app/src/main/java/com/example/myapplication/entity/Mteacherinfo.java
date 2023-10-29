package com.example.myapplication.entity;

import java.io.Serializable;

public class Mteacherinfo implements Serializable {
    private String Mtname; //主讲教师姓名
    private int Mtid; //主讲教师id
    private String Mtpassword; //账号密码

    public Mteacherinfo(String Mtname, int Mtid, String Mtpassword){
        this.Mtname = Mtname;
        this.Mtid = Mtid;
        this.Mtpassword = Mtpassword;
    }

    public String getMtname() {
        return Mtname;
    }

    public void setMtname(String mtname) {
        Mtname = mtname;
    }

    public int getMtid() {
        return Mtid;
    }

    public void setMtid(int mtid) {
        Mtid = mtid;
    }

    public String getMtpassword() {
        return Mtpassword;
    }

    public void setMtpassword(String mtpassword) {
        Mtpassword = mtpassword;
    }

    public Mteacherinfo(){
    }
}
