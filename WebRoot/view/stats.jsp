<%@ page language="java" import="java.util.*,com.weipai.model.Manager" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int powerId = ((Manager)session.getAttribute("manager")).getPowerId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-cn">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title>投注统计</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/jquery.treegrid.css">
    <link rel="stylesheet" href="../css/gm.css">
    <!--[if lt IE 9]>
    <script src="../js/html5shiv.min.js"></script>
    <script src="../js/respond.min.js"></script>
    <![endif]-->
</head>
<body onload="getAccount(1,5,'<%=basePath%>',${sessionScope.manager.powerId==1})">
<div class="container-fluid">

  <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
      <div class="navbar-header">
          <button id="navBtn" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">
              代理管理
          </a>
      </div>
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav" id="navlist">
              <li><a href="#info">代理信息</a></li>
              <li><a href="#agent">我的代理</a></li>
              <li><a href="#vip">我的会员</a></li>
              <li><a href="#detail">账单明细</a></li>
              <li><a href="#count">统计信息</a></li>
              <li><a href="#note">提现流水</a></li>
              <li  class="active"><a href="#stats">投注统计</a></li>
              <li><a href="#cathectic">投注明细</a></li>
              
              <%if(powerId==1){ %>
              <li><a href="#exchange">兑换明细</a></li>
              <li><a href="#notice">公告管理</a></li>
              <li><a href="#config">系统配置</a></li>
              <li><a href="#handle">异常处理</a></li>
              <%} %>
              
          </ul>
      </div>
          <button type="button" class="btn btn-danger logout" onclick="logout()">退出登录</button>
      </div>
  </nav>
   <div class="content">
    <div id="stats" class="active">
        <h2>投注统计</h2>

        <div class="panel panel-default">
            <div class="panel-body">
                <div class="clear time">
                    <ul class="nav nav-pills pull-right">
                    	<li><a href="#" onclick="getAccount(1,0,'<%=basePath%>',${sessionScope.manager.powerId==1})">全部投注</a><li>
                        <li><a href="#" onclick="getAccount(1,4,'<%=basePath%>',${sessionScope.manager.powerId==1})">上月投注</a><li>
                        <li><a href="#" onclick="getAccount(1,3,'<%=basePath%>',${sessionScope.manager.powerId==1})">本月投注</a><li>
                        <li><a href="#" onclick="getAccount(1,2,'<%=basePath%>',${sessionScope.manager.powerId==1})">上周投注</a><li>
                        <li><a href="#" onclick="getAccount(1,1,'<%=basePath%>',${sessionScope.manager.powerId==1})">本周投注</a><li>
                        <li><a href="#" onclick="getAccount(1,6,'<%=basePath%>',${sessionScope.manager.powerId==1})">昨天投注</a><li>
                        <li  class="active"><a href="#" onclick="getAccount(1,5,'<%=basePath%>',${sessionScope.manager.powerId==1})">今天投注</a><li>
                    </ul>
                </div>
                <h4 id="total">投注金额汇总(￥)：<b></b> 元</h4>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover mainTbl tree">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>代理编码</th>
                            <th>手机号</th>
                            <th>微信</th>
                            <th>邀请码</th>
                            <th>游戏用户ID</th>
                            <th>会员数</th>
                            <th>投注金额(元)</th>
                            <th>代理级别</th>
                        </tr>
                        </thead>
                        <tbody >
                        
                        </tbody>
                    </table>
                </div>
                <div class="page">
                <ul id="agent-pages"></ul>
                    <p>共 <b>1</b> 条记录</p>
                </div>
            </div>


        </div>

        <div id="agent-pages"></div>
    </div>


   </div>
</div>
<script src="../js/jquery-1.11.3.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap-paginator.js"></script>
<script src="../js/bootlint.js"></script>
<script type="text/javascript" src="../js/jquery.treegrid.min.js"></script>
<script src="../js/gm.js"></script>
<script src="../js/stats.js"></script>
<script type="text/javascript">
//var canSubmit = true;

function logout(){
	  $.ajax({	
	   type: "POST",
	   url: "<%=basePath%>controller/manager/logout",
	   data: "",
	   success: function data(data){
	        sessionStorage.clear();
	   			window.location = "<%=basePath%>login.jsp";
	   	}
	});
}


//
</script>
</body>
</html>