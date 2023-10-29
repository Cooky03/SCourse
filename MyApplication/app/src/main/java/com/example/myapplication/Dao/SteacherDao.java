package com.example.myapplication.Dao;

import com.example.myapplication.entity.Steacherinfo;
import com.example.myapplication.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主讲教师数据库操作类
 */
public class SteacherDao extends JDBCUtils {

    //查询所有教师信息
    public List<Steacherinfo> getsteacherList() {
        List<Steacherinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from steacher";
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                Steacherinfo item = new Steacherinfo();
                item.setStid(rs.getInt("Stid"));
                item.setStpassword(rs.getString("Stpassword"));
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
     * 按主讲教师账号和密码查询主讲教师信息
     * @param Stid 主讲教师账号
     * @param Stpassword 密码
     * @return item 实例
     */
    public Steacherinfo getSteacherByStidAndStpassword(int Stid, String Stpassword){
        Steacherinfo item = null;
        try{
            getConn();
            String sql = "select * from steacher where Stid=? and Stpassword=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Stid);
            pStmt.setString(2,Stpassword);
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Steacherinfo();
                item.setStid(Stid);
                item.setStpassword(Stpassword);
                item.setStname(rs.getString("Stname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    /**
     * 按主讲教师账号和密码查询主讲教师信息
     * @param Stid 主讲教师账号
     * @return item 实例
     */
    public Steacherinfo getSteacherByStid(int Stid){
        Steacherinfo item = null;
        try{
            getConn();
            String sql = "select * from steacher where Stid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Stid);
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Steacherinfo();
                item.setStid(Stid);
                item.setStpassword(rs.getString("Stpassword"));
                item.setStname(rs.getString("Stname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    /**
     * 添加主讲教师信息
     * @param item 要添加的主讲教师
     * @return iRow 影响的行数
     */
    public int addSteacher(Steacherinfo item){
        int iRow = 0;
        try{
            getConn();
            String sql = "insert into steacher(Stname, Stid, Stpassword) values(?, ?, ?)";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,item.getStname());
            pStmt.setInt(2,item.getStid());
            pStmt.setString(3,item.getStpassword());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 修改主讲教师信息
     * @param item 要添加的主讲教师
     * @return iRow 影响的行数
     */
    public int editSteacher(Steacherinfo item){
        int iRow = 0;
        try{
            getConn();
            String sql = "update steacher set Stpassword=? where Stid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,item.getStpassword());
            pStmt.setInt(2,item.getStid());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 删除主讲教师信息
     * @param Stid 要添加的主讲教师id
     * @return iRow 影响的行数
     */
    public int delSteacher(int Stid){
        int iRow = 0;
        try{
            getConn();
            String sql = "delete from steacher where Stid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Stid);
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }
}
