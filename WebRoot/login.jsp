<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title></title>
    <!--<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">-->
    <link rel="stylesheet" href="css/gm.css">

    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    function changeCode() {  
        $('#kaptchaImage').hide().attr('src', './kaptcha/getKaptchaImage.do?' + Math.floor(Math.random()*100) ).fadeIn();  
        event.cancelBubble=true;  
    }  

	function login(){
		sessionStorage.clear();
		$.ajax({	
		
			   type: "POST",
			   url: "<%=basePath%>controller/manager/login",
			   data: "username="+$("#username").val()+"&password="+$("#password").val()+"&yqm="+$("#yqm").val(),
			   success: function data(data){
						var param = eval('('+data+')');
						if(param.mess == "0"||param.mess =="7"){
							if(param.mess == "0"){
								sessionStorage['powerId']=1;
							}
							if(param.roomCard){
								sessionStorage['roomCard']=param.roomCard;
							}
							sessionStorage['basePath']="<%=basePath%>";
							window.location = "<%=basePath%>view/info.jsp";
							
				} else {
					alert("登录失败，账户或密码或者验证码有误");
				}
			}
		});
	}
	
	function forgetPwd(){
		alert("忘记密码请联系超级管理员!  tel:13888888888");
	}
	
</script>
</head>
<body>
<div class="container-fluid">
   <div id="login">
       <h2>代理管理平台</h2>
       <form>
           <div class="item-box">
               <div class="logo">
               <img src="img/logo.png"  />
               </div>

           <div class="item">
               <i class="icon-user"></i>
               <input type="text" name="userName" autofocus id="username" placeholder="请填写用户名"/>

           </div>
           <div class="item">
               <i class="icon-pwd"></i>
               <input type="password" id="password" placeholder="请输入密码"/>
           </div>
           <div class="item">
               <i class="icon-test"></i>
               <input type="text" id="yqm" placeholder="请输入验证码"/>
               <a href="#" title="换一张" onclick="changeCode()">换一张</a>
           </div>
               <div class="img-box">
                   <img  id="kaptchaImage" class="testCode" src="./kaptcha/getKaptchaImage.do" alt="点击切换" title="点击切换"  onclick="changeCode()"/>
               </div>
           </div>
           <div class="login-btn-panel">
               <input type="button" class="btn btn-success login-btn"  onclick="login()" value="登 录"/>
               <input type="button" class="btn  forget-pwd" onclick="forgetPwd()" value="忘记密码"/>
           </div>
       </form>
       <p>Copyright &copy;2016-2017,LongQing Inc, All rights Reserved </p>
   </div>
</div>

<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<script src="js/jquery-1.11.3.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bootstrap-paginator.js"></script>
<script src="js/bootlint.js"></script>
<script src="js/gm.js"></script>
</body>
</html>
