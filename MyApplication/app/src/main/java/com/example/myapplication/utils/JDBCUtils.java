package com.example.myapplication.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 连接数据库的辅助类
 */
public class JDBCUtils {
    private static final String TAG = "mysql-myapplication-JDBCUtils";
    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动
    private static String dbName = "hm";// 数据库名称
    private static String url = "jdbc:mysql://118.202.10.115:3306/hm?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone = GMT";
    private static String user = "ws";// 用户名
    private static String password = "123456";// 密码

    public static Connection connection; //连接对象
    public static Statement stmt; //命令集
    public static PreparedStatement pStmt; //预编译命令集
    public static ResultSet rs; //结果集

    //取得连接
    public static Connection getConn(){

        connection = null;
        try{
            Class.forName(driver);// 动态加载类
            String ip = "118.202.10.115";// 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://172.18.17.36:3306/hmwk?&useSSL=false&characterEncoding=utf-8", "ws", "123456");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    //关闭数据库操作对象
    public static void closeAll(){
      try {
          if (rs != null) {
              rs.close();
              rs = null;
          }
          if (stmt != null) {
              stmt.close();
              stmt = null;
          }
          if (pStmt != null) {
              pStmt.close();
              pStmt = null;
          }
          if (connection != null) {
              connection.close();
              connection = null;
          }
      }catch (Exception ex){
          ex.printStackTrace();
      }
    }

}