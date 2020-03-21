package com.shuming;

import java.sql.*;
import java.util.ArrayList;

public interface DatabaseHandler {
    static final String DBDRIVER = "com.mysql.jdbc.Driver";
    static final String DBURL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASSWORD = "435991368";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DBDRIVER);
            conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }
    public Integer createTable(String createTableSql);
    //查询点赞表职工已经存在的username
    public Integer insertIntoTable(String username);
    //根据username查询ID
    public Integer selectFromLikeTable(String username);
    //增加用户
    public Integer addArticlePath(String articlePath);
    //根据用户名来更新用户信息
    public Integer updateArticlePath(String username);
    //根据ID查询指定的articlePath
    public ArticlePath queryOneArticlePath(int id, ArticlePath articlePath);
    //查询表中的所有信息
    public ArrayList<ArticlePath> queryAllArticlePath();

    //很久用户正确的用户名来查询用户密码，来验证登录
    public Users queryOneUser(String username, Users user);

    //验证用户名的正确性
    public ArrayList<String> queryAllUsername(String tableName);

    //关闭连接资源
    public static void closeConnect(Connection conn, PreparedStatement pstat, ResultSet res) {
        try {
            res.close();
            pstat.close();
            conn.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    //关闭连接资源
    public static void closeConnect(Connection conn, PreparedStatement pstat) {
        try {
            pstat.close();
            conn.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }
}
