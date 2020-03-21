<html>
<head>
    <style type="text/css">
        body{
            background-color: #AEDD81;
        }
        #div1 {

            position:absolute;
            top:50%;
            left:50%;
            margin:-150px 0 0 -200px;
            width:400px;
            height:300px;
        }
    </style>

    <script src="js/jquery-3.4.1.js"></script>
    <script>
        function checkLogin() {
            var username = $("#username").val().toString();
            var password = $("#password").val().toString();
            $.ajax(
                {
                    type : "POST",
                    url: "welcome.do",
                    data : {
                            "username" : username,
                            "password" : password
                    },
                    dataType:"text",
                    success : function (response) {
                        if(response == "false"){
                           alert("Please use the correct username and password.")
                        }
                        if(response == "true"){
                            window.location = "index.do";
                        }

                    }
                }
            )
        }
    </script>
</head>
<body>
<div id="div1">
    <h2>IDEA springmvc project</h2>
    <form  id="loginForm" action="welcome.do" method="post">
    username:<input type="text" name="username" id="username"><br><br>
    password: <input type="password" name = "password" id="password"><br><br>
    <button id="submit" onclick="checkLogin()">submit</button>
    </form>
</div>
</body>
</html>
