package com.shuming.mycontrollers;

import com.shuming.ArticlePath;
import com.shuming.DatabaseHandler;
import com.shuming.DatabasesHandlerImpl;
import com.shuming.Users;
import com.shuming.handler.HandlerData;
import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Controller
public class MyHandlerController {

    //Jquery AJAX实现异步验证登录
    @RequestMapping( value = "/welcome.do")
    @ResponseBody
    public String handlerLogin(HttpSession session,String username, String password){

        session.setAttribute("username",username);
        DatabaseHandler databaseHandler = new DatabasesHandlerImpl();
        String userTable = "users";
        ArrayList<String> usernameList = databaseHandler.queryAllUsername(userTable);
        Users user = new Users();
        if(usernameList.contains(username)){
             user = databaseHandler.queryOneUser(username,user);
             boolean b = user.getPassword().equals(password);
             if (b){
                 return "true";
             }
        }
        return "false";
    }

    @RequestMapping("/index.do")
    public String toWelcome(){
        return "welcome";
    }

    //Read article
    @RequestMapping(value = "/showArticle.do")
    public String showArticle(HttpServletRequest request, HttpServletResponse response){
        DatabaseHandler databaseHandler = new DatabasesHandlerImpl();
        ArrayList<ArticlePath> articlePathsList = databaseHandler.queryAllArticlePath();
        HandlerData handlerData = new HandlerData();
        ArrayList<StringBuilder> contentList = new ArrayList<>();
        for (ArticlePath articlePath : articlePathsList){
            contentList.add(handlerData.getArticleContent(articlePath));
        }
        request.setAttribute("contentList", contentList);
        return "showArticle";
    }

    //利用AJAX实现点赞,并限制每个ID只能点赞一次
    @RequestMapping(value = "/like.do", method = RequestMethod.POST)
    @ResponseBody
    public String handleLike(String username){
           HandlerData handlerData = new HandlerData();
           String result =  handlerData.getNumberOfLikeUser(username);
           return result;
    }

}
