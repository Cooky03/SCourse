package com.example.myapplication.Dao;

import com.example.myapplication.entity.Applyinfo;
import com.example.myapplication.entity.Courseinfo;
import com.example.myapplication.entity.Steacherinfo;
import com.example.myapplication.utils.CommonUtils;
import com.example.myapplication.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class ApplyDao extends JDBCUtils {
    
    //查询所有选课申请
    public List<Applyinfo> getapplyList() {
        List<Applyinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from apply";
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                Applyinfo item = new Applyinfo();
                item.setSid(rs.getInt("Sid"));
                item.setCid(rs.getInt("Cid"));
                item.setProcess(rs.getString("Process"));
                item.setReason(rs.getString("Reason"));

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
     * 根据学生id查询选课申请
     * @param Sid 学生id
     * @return item 课程
     */
    public List<Applyinfo> getApplyBySid(int Sid){
        List<Applyinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from apply where Sid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1, String.valueOf(Sid));
            rs = pStmt.executeQuery();
            while (rs.next()){
                Applyinfo item = new Applyinfo();
                item.setSid(Sid);
                item.setCid(rs.getInt("Cid"));
                item.setProcess(rs.getString("Process"));
                item.setReason(rs.getString("Reason"));
                item.setStname(rs.getString("Stname"));
                item.setSname(rs.getString("Sname"));
                item.setCname(rs.getString("Cname"));
                item.setScore(rs.getInt("Score"));
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
     * 根据主讲教师id查询选课申请
     * @param Stid 主讲教师id
     * @return item 课程
     */
    public List<Applyinfo> getApplyByStid(int Stid){
        List<Applyinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from apply,course where course.Stid=? and apply.Cid = course.Cid and apply.Process = ?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1, String.valueOf(Stid));
            pStmt.setString(2, "主讲教师审批中");
            rs = pStmt.executeQuery();
            while (rs.next()){
                Applyinfo item = new Applyinfo();
                item.setSid(rs.getInt("Sid"));
                item.setCid(rs.getInt("Cid"));
                item.setProcess(rs.getString("Process"));
                item.setReason(rs.getString("Reason"));
                item.setStname(rs.getString("Stname"));
                item.setSname(rs.getString("Sname"));
                item.setCname(rs.getString("Cname"));
                item.setScore(rs.getInt("Score"));
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
     * 根据主讲教师id查询选课申请
     * @param Mtid 主讲教师id
     * @return item 课程
     */
    public List<Applyinfo> getApplyByMtid(int Mtid){
        List<Applyinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from apply,course where course.Mtid=? and apply.Cid = course.Cid and apply.Process = ?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1, String.valueOf(Mtid));
            pStmt.setString(2,"主管教师审批中");
            rs = pStmt.executeQuery();
            while (rs.next()){
                Applyinfo item = new Applyinfo();
                item.setSid(rs.getInt("Sid"));
                item.setCid(rs.getInt("Cid"));
                item.setProcess(rs.getString("Process"));
                item.setReason(rs.getString("Reason"));
                item.setStname(rs.getString("Stname"));
                item.setSname(rs.getString("Sname"));
                item.setCname(rs.getString("Cname"));
                item.setScore(rs.getInt("Score"));

                list.add(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

    public Applyinfo getApplyBySidAndCid(int Sid,int Cid){
        Applyinfo item = null;
        try{
            getConn();
            String sql = "select * from apply where Sid=? and Cid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1, String.valueOf(Sid));
            pStmt.setString(2, String.valueOf(Cid));
            rs = pStmt.executeQuery();
            if (rs.next()){
                item = new Applyinfo();
                item.setSid(rs.getInt("Sid"));
                item.setCid(rs.getInt("Cid"));
                item.setProcess(rs.getString("Process"));
                item.setReason(rs.getString("Reason"));
            }
            else return null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    public int addApply(Applyinfo item){
        int iRow = 0;
        try{
            getConn();
            String sql = "insert into apply(Sid,Cid,Process,Reason,Stname,Cname,Sname,Score) values(?, ?, ?, ?, ?, ?, ?, ?)";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,item.getSid());
            pStmt.setInt(2,item.getCid());
            pStmt.setString(3,item.getProcess());
            pStmt.setString(4,item.getReason());
            pStmt.setString(5, item.getStname());
            pStmt.setString(6,item.getCname());
            pStmt.setString(7,item.getSname());
            pStmt.setInt(8,item.getScore());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    public int editApply(Applyinfo item){
        int iRow = 0;
        try {
            getConn();
            String sql = "update apply set Process = ?, Reason = ? where Sid = ? and Cid = ?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,item.getProcess());
            pStmt.setString(2,item.getReason());
            pStmt.setInt(3,item.getSid());
            pStmt.setInt(4,item.getCid());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }
}
