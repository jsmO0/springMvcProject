package com.shuming;

import org.omg.CORBA.DATA_CONVERSION;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DatabasesHandlerImpl implements DatabaseHandler {
    @Override
    //在mysql数据库中创建数据表
    public Integer createTable(String createTableSql) {
        int isOk = -1;
        Connection conn = DatabaseHandler.getConnection();
        PreparedStatement preparedStatement = null;
        //String sql = " create table articlePath(id int not null auto_increment primary key,Sname varchar(40) not null );";
            try {
        preparedStatement = conn.prepareStatement(createTableSql);
           isOk =  preparedStatement.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn,preparedStatement);
        }
        return isOk;
    }
    //向点赞人数表中插入数据
    public Integer insertIntoTable(String username){
        int isOk = 0;
        Connection conn = DatabaseHandler.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into likeTable(username) value(?);";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            isOk = preparedStatement.executeUpdate();
        } catch (SQLException e1){
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn,preparedStatement);
        }
        return isOk;
    }
    //查询ID
    public Integer selectFromLikeTable(String username){
        int id =0;
        Connection conn = DatabaseHandler.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select id from likeTable where username = ?;";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
               id = resultSet.getInt("id");
            }
        } catch (SQLException e1){
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn,preparedStatement,resultSet);
        }
        return id;
    }

    @Override
    //向文章存储路径的数据表中插入数据
    public Integer addArticlePath(String articlePath) {
        int isOk = 0;
        Connection conn = DatabaseHandler.getConnection();
        String sql = "insert into articlePath(Sname) value(?);";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,articlePath);
            isOk = preparedStatement.executeUpdate();
        } catch (SQLException e1){
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn,preparedStatement);
        }
        return isOk;
    }

    @Override
    public Integer updateArticlePath(String username) {
        return null;
    }

    //进行登录验证前先进行用户名的检验
    public ArrayList<String> queryAllUsername(String tableName){
        ArrayList<String> usernameList = new ArrayList<>();
        Connection conn = DatabaseHandler.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select username from " + tableName+";";
        try{
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                usernameList.add(resultSet.getString("username"));
            }
        } catch (SQLException e1){
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn,preparedStatement,resultSet);
        }
        return usernameList;
    }

    //登录验证，根据用户名来进行数据库中的密码匹配
    public Users queryOneUser(String username, Users user){
        Connection conn = DatabaseHandler.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select password from users where username = ?;";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e1){
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn,preparedStatement,resultSet);
        }

        return user;
    }

    @Override
    //根据ID查询某个指定的articlePath
    public ArticlePath queryOneArticlePath(int id ,ArticlePath articlePath) {
        articlePath.setId(id);
        Connection conn = DatabaseHandler.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "select Sname from articlePath where id = ?;";
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);
           resultSet =  preparedStatement.executeQuery();
           if(resultSet.next()){
               articlePath.setSname(resultSet.getString("Sname"));
           }
        } catch (SQLException e1){
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn,preparedStatement,resultSet);
        }
        return articlePath;
    }
    //查询表中所有的path
    public ArrayList<ArticlePath> queryAllArticlePath() {
        ArrayList<ArticlePath> articlePathsList = new ArrayList<>();
        Connection conn = DatabaseHandler.getConnection();
        PreparedStatement preparedStatement = null;
        ArticlePath articlePath = null;
        ResultSet resultSet = null;
        String sql = "select *from articlePath;";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                articlePath = new ArticlePath();
                articlePath.setId(resultSet.getInt("id"));
                articlePath.setSname(resultSet.getString("Sname"));
                articlePathsList.add(articlePath);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            DatabaseHandler.closeConnect(conn, preparedStatement, resultSet);
        }
        return articlePathsList;
    }
}
