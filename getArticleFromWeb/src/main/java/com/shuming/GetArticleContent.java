package com.shuming;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetArticleContent {

    private final String articleContentPatternString  = "&nbsp;&nbsp;&nbsp;&nbsp;(.*?)<br />";
    private final String titlePatternString = "<h2><font color=\"#dc143c\">(.*?)</font></h2>";
    //对外暴露一个公共接口供dao层调用
    public ArrayList<String> supportContent(String mainPageUrl){
        return getContent(mainPageUrl);
    }
    //私有方法整合获取文章内容的功能
    private ArrayList<String> getContent(String mainPageUrl){

        String htmlResult = getHtmlInfo(mainPageUrl);
        ArrayList<String> urlList = getArticleUrl(htmlResult);
        ArrayList<String> pathList = getArticleTitleAndContent(urlList);
        return pathList;
    }
    //获取每章节的URL来获取每章节的Title和内容
    public ArrayList<String> getArticleTitleAndContent(ArrayList<String> urlList){
        ArrayList<String> pathList = new ArrayList<>();
        String article = null;
        Pattern articleContentPattern = Pattern.compile(articleContentPatternString);
        Pattern titlePattern = Pattern.compile(titlePatternString);
        StringBuilder builder = new StringBuilder();
        int fileName = 1;
        for (String s : urlList){
            article = getHtmlInfo(s);
            Matcher titleMatcher = titlePattern.matcher(article);
            Matcher articleContentMatcher = articleContentPattern.matcher(article);
            while  (titleMatcher.find()){
                String title = titleMatcher.group(1);
                String articleContentValue = null;
                builder.append(title+ "\r\n"+"\r\n");
                while(articleContentMatcher.find()){
                    builder.append(articleContentMatcher.group(1)+"\r\n");
                }
                articleContentValue = builder.toString().trim();
                String filePath = "C:\\Blog\\xiaoShuo\\"+ String.valueOf(fileName) + ".txt";
                pathList.add(filePath);
                File outputFile = new File(filePath);
                //下载小说到本地目录下面
                downloadToLocalFile(articleContentValue, outputFile);
                builder.delete(0, builder.length());
            }
            fileName++;
        }
           return pathList;
    }
    //获取文章各章节的URL
    public ArrayList<String> getArticleUrl(String htmlResult){
        String[] handlerResult = htmlResult.split("\r\n");
        ArrayList<String> urlNumberList = new ArrayList<>();
        ArrayList<String> urlList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        //Pattern pattern = Pattern.compile("^<td width=\"25%\"><a href=\"(190884.html)\">1.从你的全世界路过</a></td>$");
        Pattern getArticleUrl = Pattern.compile("href=\"(\\d+).html\"");
        for (String s : handlerResult){
            Matcher matcher = getArticleUrl.matcher(s);
            while (matcher.find()){
                urlNumberList.add(matcher.group(1));
            }
        }
        for (String s : urlNumberList){
            urlList.add(stringBuilder.append("https://www.kanunu8.com/book2/10748/").append(s).append(".html").toString().trim());
            stringBuilder.delete(0,stringBuilder.length());
        }
        return urlList;

    }
    //将小说内容写入本地文件
    public void downloadToLocalFile(String contentOutput, File outputFile){

        BufferedWriter writer = null;
        try{
            writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(contentOutput);
            writer.flush();
        }catch (IOException e1){
            e1.printStackTrace();
        }catch (Exception e2){
            e2.printStackTrace();
        }finally {
            if (writer != null){
                try{
                    writer.close();
                } catch (IOException e1){
                    e1.printStackTrace();
                } catch (Exception e2){
                    e2.printStackTrace();
                }
            }
        }

    }
    //获取HTML页面
    public String getHtmlInfo(String url){
        String result = null;
        //创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建get请求
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            int i = response.getStatusLine().getStatusCode();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity, "gb2312");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
                if (httpClient != null)
                    httpClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
