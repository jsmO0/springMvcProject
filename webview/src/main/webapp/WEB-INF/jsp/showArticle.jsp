<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: euihajs
  Date: 3/18/2020
  Time: 2:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        .article{text-align:center}
    </style>

    <script src="js/jquery-3.4.1.js"></script>

   <script>
       function addLikeNumber(){
           var username = $("#h2").text().toString();
           $.ajax(
               {
                   type: "POST",
                   url: "like.do",
                   data: {"username": username},
                   dataType: "text",
                   success: function (result) {
                       if(result == "exit"){
                           alert("you have already like this article.")
                       }
                       else{
                           $("#likeNumber").text(result);
                       }
                   }
               }
           )
       }
   </script>
</head>
<body>
Please read the article:
<h2 id="h2"><%=session.getAttribute("username") %></h2>
<% ArrayList<StringBuilder> contentList =  (ArrayList<StringBuilder>) request.getAttribute("contentList");
   %>
<div class="article">
   <textarea readonly  rows="45" cols="100" > <%
        for (StringBuilder builder : contentList){
            %>
          <%=builder.toString().trim()%>
    <%
        }
        %>
   </textarea><br>
    <div id="like">
    <button id="button1" onclick="addLikeNumber()" >like</button>
    <h4 id="likeNumber"></h4>
    </div>
</div>
</body>
</html>
