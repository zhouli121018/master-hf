<%@ page language="java" import="java.util.*,com.weipai.model.Manager" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int powerId = ((Manager)session.getAttribute("manager")).getPowerId();
%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title>兑换明细</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/gm.css">

    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container-fluid">
  <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
      <div class="navbar-header">
          <button id="navBtn" type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">代理管理</a>
      </div>
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav" id="navlist">
              <li><a href="#info">代理信息</a></li>
              <li><a href="#agent">我的代理</a></li>
              <li><a href="#vip">我的会员</a></li>
              <li><a href="#detail">账单明细</a></li>
              <li><a href="#count">统计信息</a></li>
              <li><a href="#note">提现流水</a></li>
              <li><a href="#stats">投注统计</a></li>
              <li><a href="#cathectic">投注明细</a></li>
              
              <%if(powerId==1){ %>
              <li class="active"><a href="#exchange">兑换明细</a></li>
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
    <div id="exchange" class="active">
        <div class="note-detail">
            <h2>兑换明细</h2>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form class="form-inline form-btn">
		                    <div class="form-group">
		                        <div class="input-group">
		                            <div class="input-group-addon">开始时间</div>
		                            <input type="date" class="form-control beginTime" id="startTime" placeholder="开始时间"/>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="input-group">
		                            <div class="input-group-addon">结束时间</div>
		                            <input type="date" class="form-control overTime" id="endTime" placeholder="结束时间"/>
		                        </div>
		                    </div>
		                    <input type="number" min="1" class="search" id="inviteCode" placeholder="请输入用户ID查询"/>
		                    <input type="button" class="btn btn-default" onclick="getPaylog(1)" value="查询"/>
                    </form>
                    <div class="table-responsive">
                    <table  class="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th>用户ID</th>
                            <th>昵 称</th>
                            <th>兑换金额</th>
                            <th>兑换时间</th>
                            <th>兑换序列号</th>
                            <th>兑换状态</th>
                        </tr>
                        </thead>
                        <tbody id="tixianlogTbody">
                        </tbody>
                    </table>
                    </div>
                    
                </div>
                <div>
                <ul id="note-pages"></ul>
                </div>
            </div>
            
        </div>

    </div>
   </div>
</div>
<script src="../js/jquery-1.11.3.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap-paginator.js"></script>
<script src="../js/bootlint.js"></script>
<script src="../js/gm.js"></script>
<script src="../js/exchange.js"></script>
<script type="text/javascript">

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



</script>
</body>
</html>