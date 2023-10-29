package com.example.myapplication.Dao;

import com.example.myapplication.entity.Courseinfo;
import com.example.myapplication.entity.Courseinfo;
import com.example.myapplication.entity.Mteacherinfo;
import com.example.myapplication.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class CourseDao extends JDBCUtils {
    //查询所有课程信息
    public List<Courseinfo> getcourseList() {
        List<Courseinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from course";
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                Courseinfo item = new Courseinfo();
                item.setStid(rs.getInt("Stid"));
                item.setMtid(rs.getInt("Mtid"));
                item.setCid(rs.getInt("Cid"));
                item.setScore(rs.getInt("Score"));
                item.setCname(rs.getString("Cname"));
                item.setStname(rs.getString("Stname"));
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
     * 根据课程名称查询课程
     * @param Cname 课程名
     * @return item 课程
     */
    public List<Courseinfo> getCourseByCname(String Cname){
        List<Courseinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from course where Cname like ?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,"%"+Cname+"%");
            rs = pStmt.executeQuery();
            while (rs.next()){
                Courseinfo item = new Courseinfo();
                item = new Courseinfo();
                item.setCname(rs.getString("Cname"));
                item.setStname(rs.getString("Stname"));
                item.setCid(rs.getInt("Cid"));
                item.setStid(rs.getInt("Stid"));
                item.setMtid(rs.getInt("Mtid"));
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
     * 根据课程id查询课程
     * @param Cid 课程id
     * @return item 课程
     */
    public Courseinfo getCourseByCid(int Cid){
        Courseinfo item = null;
        try{
            getConn();
            String sql = "select * from course where Cid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1, String.valueOf(Cid));
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Courseinfo();
                item.setCid(rs.getInt("Cid"));
                item.setCname(rs.getString("Cname"));
                item.setStid(rs.getInt("Stid"));
                item.setMtid(rs.getInt("Mtid"));
                item.setScore(rs.getInt("Score"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }


}
