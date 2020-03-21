package com.shuming.handler;

import com.shuming.ArticlePath;
import com.shuming.DatabaseHandler;
import com.shuming.DatabasesHandlerImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HandlerData {

    //处理点赞数据
    public String getNumberOfLikeUser(String username){
        DatabaseHandler databaseHandler = new DatabasesHandlerImpl();
        String likeTable  = "likeTable";
        ArrayList<String> likeUserList = databaseHandler.queryAllUsername(likeTable);
        if(!likeUserList.contains(username)){
            int result = databaseHandler.insertIntoTable(username);
            int id = 0;
            if (result != 0){
                id = databaseHandler.selectFromLikeTable(username);
            }
            return String.valueOf(id);
        }
        return "exit";

    }

    //处理文章path和内容
    public StringBuilder getArticleContent(ArticlePath articlePath){
        File articleFile = new File(articlePath.getSname());
        int id = articlePath.getId();
        ArrayList<StringBuilder> articleContentList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try{
            //解决读取文件时乱码的问题
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(articleFile), "UTF-8"));
            String articleContent = null;
            while ( null != (articleContent = bufferedReader.readLine())) {
                stringBuilder.append(articleContent + "\r\n");
            }
        } catch (FileNotFoundException e1){
            e1.printStackTrace();
        } catch (IOException e2){
            e2.printStackTrace();
        } catch (Exception e3){
            e3.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return stringBuilder;
    }
}
