package com.example.myapplication.Dao;

import com.example.myapplication.entity.Mteacherinfo;
import com.example.myapplication.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主管教师数据库操作类
 */
public class MteacherDao extends JDBCUtils {

    //查询所有主管教师信息
    public List<Mteacherinfo> getmteacherList() {
        List<Mteacherinfo> list = new ArrayList<>();
        try{
            getConn();
            String sql = "select * from mteacher";
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                Mteacherinfo item = new Mteacherinfo();
                item.setMtid(rs.getInt("Mtid"));
                item.setMtpassword(rs.getString("Mtpassword"));
                item.setMtname(rs.getString("Mtname"));

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
     * 按主管教师账号和密码查询主管教师信息
     * @param Mtid 主管教师账号
     * @param Mtpassword 密码
     * @return item 实例
     */
    public Mteacherinfo getMteacherByMtidAndMtpassword(int Mtid, String Mtpassword){
        Mteacherinfo item = null;
        try{
            getConn();
            String sql = "select * from mteacher where Mtid=? and Mtpassword=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Mtid);
            pStmt.setString(2,Mtpassword);
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Mteacherinfo();
                item.setMtid(Mtid);
                item.setMtpassword(Mtpassword);
                item.setMtname(rs.getString("Mtname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    /**
     * 按主管教师账号和密码查询主管教师信息
     * @param Mtid 主管教师账号
     * @return item 实例
     */
    public Mteacherinfo getMteacherByMtid(int Mtid){
        Mteacherinfo item = null;
        try{
            getConn();
            String sql = "select * from mteacher where Mtid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Mtid);
            rs = pStmt.executeQuery();
            while (rs.next()){
                item = new Mteacherinfo();
                item.setMtid(Mtid);
                item.setMtpassword(rs.getString("Mtpassword"));
                item.setMtname(rs.getString("Mtname"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return item;
    }

    /**
     * 添加主管教师信息
     * @param item 要添加的主管教师
     * @return iRow 影响的行数
     */
    public int addMteacher(Mteacherinfo item){
        int iRow = 0;
        try{
            getConn();
            String sql = "insert into mteacher(Mtname, Mtid, Mtpassword) values(?, ?, ?)";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,item.getMtname());
            pStmt.setInt(2,item.getMtid());
            pStmt.setString(3,item.getMtpassword());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 修改主管教师信息
     * @param item 要添加的主管教师
     * @return iRow 影响的行数
     */
    public int editMteacher(Mteacherinfo item){
        int iRow = 0;
        try{
            getConn();
            String sql = "update mteacher set Mtpassword=? where Mtid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,item.getMtpassword());
            pStmt.setInt(2,item.getMtid());
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }

    /**
     * 删除主管教师信息
     * @param Mtid 要添加的主管教师id
     * @return iRow 影响的行数
     */
    public int delMteacher(int Mtid){
        int iRow = 0;
        try{
            getConn();
            String sql = "delete from mteacher where Mtid=?";
            pStmt = connection.prepareStatement(sql);
            pStmt.setInt(1,Mtid);
            iRow = pStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return iRow;
    }
}
