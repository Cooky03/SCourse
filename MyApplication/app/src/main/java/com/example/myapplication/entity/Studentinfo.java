package com.example.myapplication.entity;

import java.io.Serializable;

/**
 * 用户信息实体类
 */
public class Studentinfo implements Serializable {
    private String Sname; //学生姓名
    private int Sid; //学生账号
    private String Spassword; //账号密码

    public Studentinfo(String Sname, int Sid, String Spassword){
        this.Sname = Sname;
        this.Sid = Sid;
        this.Spassword = Spassword;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public String getSpassword() {
        return Spassword;
    }

    public void setSpassword(String spassword) {
        Spassword = spassword;
    }


    public Studentinfo(){
    }


}
