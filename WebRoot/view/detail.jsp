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
    <title>账单明细</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/gm.css">

    <!--[if lt IE 9]>
    <script src="../js/html5shiv.min.js"></script>
    <script src="../js/respond.min.js"></script>
    <![endif]-->
</head>
<body >
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
              <li class="active"><a href="#detail">账单明细</a></li>
              <li><a href="#count">统计信息</a></li>
              <li><a href="#note">提现流水</a></li>
              <li><a href="#stats">投注统计</a></li>
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



    <div id="detail" class="active">
        <h2>账单明细</h2>
        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-inline form-btn" name="searchForm" id="searchForm">
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">开始时间</div>
                            <input type="date" class="form-control beginTime" id="startTime" placeholder="开始时间"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">结束时间</div>
                            <input type="date" class="form-control overTime" id="endTime" placeholder="结束时间(不包含这天)"/>
                        </div>
                    </div>
                    <input type="number" class="search" id="inviteCode" min="1" placeholder="请输入代理邀请码查询"/>
                    <input type="number" class="search" id="uuid" min="1" placeholder="请输入用户ID"/>
                    <select id="gameId">
                       <option value="0">全部充值</option>
                       <option value="2">挖乐28充值</option>
                       <option value="1">牛牛充值</option>
                       <option value="1">清远麻将充值</option>
                    </select>
                    <input type="button" class="btn btn-primary" onclick="getPaylog()"  value="查询"/>
                </form>
<%--                 <%if(powerId==1){ %> --%>
                <h4 id="totalMoney">充值总金额：<b>0</b> 元</h4>
                <h4 id="totalBonus">分成收益：<b>0</b> 元</h4>
<%--                 <%} %> --%>
                <div class="table-responsive">
                    <table id="detailTbl" class="table table-bordered table-striped table-hover">
                        <thead>
<!--                         <tr class="text-center"> -->
<!--                             <th colspan="6" class="text-center">基本信息</th> -->
<!--                             <th colspan="2">直属代理</th> -->
                            
<!--                         </tr> -->
                        <tr>
<!--                             <th>充值ID</th> -->
<!--                             <th>昵称</th> -->
<!--                             <th>充值金额</th> -->
<!--                             <th>购钻数量</th> -->
<!--                             <th>充值时间</th> -->
<!--                             <th>充值结果</th> -->
<!--                             <th>代理邀请码</th> -->
<!--                             <th>代理级别</th> -->
                            	<th>充值ID</th>
		                        <th>充值昵称</th>
		                        <th>充值金额</th>
		                        <th>充值时间</th>
		                        <th>分成金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        
                        
                        </tbody>
                    </table>
                </div>
                <div class="page">
                <ul id="detail-pages">
                </ul>
                    <p>共 <b id="totalRecords"></b> 条记录</p>
                </div>
            </div>
        </div>
    </div>


   </div>
</div>

<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<script src="../js/jquery-1.11.3.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap-paginator.js"></script>
<script src="../js/bootlint.js"></script>
<script src="../js/gm.js"></script>
<script src="../js/detail.js"></script>
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

function getPaylog(){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var inviteCode = $("#inviteCode").val();
	var uuid = $("#uuid").val();
	var gameId=$("#gameId").val();
	getDetail(1,0,'<%=basePath%>',startTime,endTime,inviteCode,uuid,gameId);
}
getPaylog();
</script>
</body>
</html>