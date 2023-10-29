package com.example.myapplication.Dao;

import com.example.myapplication.entity.Studentinfo;
import com.example.myapplication.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生数据库操作类
 */
public class StudentDao extends JDBCUtils {

    //查询所有学生信息
    public List<Studentinfo> getstudentList() {
        List<Studentinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from student";
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                Studentinfo item = new Studentinfo();
                item.setSid(rs.getInt("Sid"));
                item.setSpassword(rs.getString("Spassword"));
                item.setSname(rs.getString("Sname"));

                list.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    /**
     * 按学生账号和密码查询学生信息
     * @param Sid 学生账号
     * @param Spassword 密码
     * @return item 实例
     */
    public Studentinfo getStudentBySidAndSpassword(int Sid, String Spassword){
        Studentinfo item = null;
        try{
            getConn();
            System.out.println(Sid);
            String sql = "select * from student where Sid=? and Spassword=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Sid);
            pStmt.setString(2,Spassword);
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Studentinfo();
                item.setSid(Sid);
                item.setSpassword(Spassword);
                item.setSname(rs.getString("Sname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    public Studentinfo getStudentBySid(int Sid){
        Studentinfo item = null;
        try{
            getConn();
            String sql = "select * from student where Sid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Sid);
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Studentinfo();
                item.setSid(rs.getInt("Sid"));
                item.setSpassword(rs.getString("Spassword"));
                item.setSname(rs.getString("Sname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    /**
     * 添加学生信息
     * @param item 要添加的学生
     * @return iRow 影响的行数
     */
    public int addStudent(Studentinfo item){
        int iRow = 0;
        try{
            getConn();
            String sql = "insert into student(Sname, Sid, Spassword) values(?, ?, ?)";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,item.getSname());
            pStmt.setInt(2,item.getSid());
            pStmt.setString(3,item.getSpassword());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 修改学生信息
     * @param item 要添加的学生
     * @return iRow 影响的行数
     */
    public int editStudent(Studentinfo item){
        int iRow = 0;
        try{
            getConn();
            String sql = "update student set Spassword=? where Sid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,item.getSpassword());
            pStmt.setInt(2,item.getSid());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 删除学生信息
     * @param Sid 要添加的学生id
     * @return iRow 影响的行数
     */
    public int delStudent(int Sid){
        int iRow = 0;
        try{
            getConn();
            String sql = "delete from student where id=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Sid);
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }
}
