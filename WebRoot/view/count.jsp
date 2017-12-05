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
    <title>统计信息</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/gm.css">

    <!--[if lt IE 9]>
    <script src="../js/html5shiv.min.js"></script>
    <script src="../js/respond.min.js"></script>
    <![endif]-->
</head>
<body onload="checkIsAdmin()">
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
              <li class="active"><a href="#count">统计信息</a></li>
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
    <div id="count" class="active">
        <div id="adminContent" class="divide-count">
            <h2>实时统计</h2>
            <div class="online">
                <p>玩家总数：<span id="totoalAccountCount"></span></p>
                <p>历史总房间个数：<span id="AllRoomsCount"></span></p>
                <p>消耗房卡总数：<span id="consumedRoomCardCount"></span></p>
                <p>在线房间数：<span id="onlineRoomsCount"></span></p>
                <p>今天新建房间数：<span id="todayRoomsCount"></span></p>
              <!--   <p>当前在线人数：<span id="onlineAccountCount"></span></p> -->
                <p>今天最高在线人数：<span id="topOnlineAccountCount"></span></p>
                <p>今日新增用户：<span id="newAccountCountToday"></span></p>
            </div>
        </div>
        <div id="proxyContent" class="divide-count">
            <h2>分成统计</h2>
            <div class="divide row">
                <div class="col-md-6 col-xs-12">
                    <ul class="nav nav-pills">
                    <li  class="active"><a href="#" onclick="getProxyMoney('<%=basePath%>',0)">全部充值</a></li>
                        <li><a href="#" onclick="getProxyMoney('<%=basePath%>',4)">上月充值</a></li>
                        <li><a href="#" onclick="getProxyMoney('<%=basePath%>',3)">本月充值</a></li>
                        <li><a href="#" onclick="getProxyMoney('<%=basePath%>',2)">上周充值</a></li>
                        <li><a href="#" onclick="getProxyMoney('<%=basePath%>',1)">本周充值</a></li>
                        <li><a href="#" onclick="getProxyMoney('<%=basePath%>',6)">昨天充值</a></li>
                        <li><a href="#" onclick="getProxyMoney('<%=basePath%>',5)">今天充值</a></li>
                    </ul>
                </div>
                <div class="col-md-5 col-xs-12" id="searchButton">
                    <input type="number" id="inviteCode" min="1" placeholder="请输入代理邀请码" class="form-control" />
                    <input type="button" value="查询代理收益" onclick="getProxyMoney('<%=basePath%>',0)" class="btn btn-default toTop"/>

                </div>


            </div>
            <div class="panel panel-default">
                <div class="panel-body">
                    <table  class="table table-bordered table-striped table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>充值统计</th>
                            <th id="sumtotal"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>下级用户充值：</td>
                            <td id="sumone"></td>
                        </tr>
                        <tr>
                            <td>下级代理充值：</td>
                            <td id="sumtwo"></td>
                        </tr>
                        <tr>
                            <td>下级用户投注：</td>
                            <td id="sumlotone"></td>
                        </tr>
                        <tr>
                            <td>下级代理投注：</td>
                            <td id="sumlottwo"></td>
                        </tr>
                        </tbody>
                    </table>
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
<script src="../js/count.js"></script>
<script>
$('#count').on('click','div.box a',function(e){
    e.preventDefault();
    $(this).addClass('active').siblings().removeClass('active');
    if($(this).attr("href")=="#mo"){
    	$("#count-pages1").show();
    	$("#countNumbers1").show();
    	$("#countNumbers2").hide();
    	$("#count-pages2").hide();
    	$("#count-pages3").hide();
    	$("#countNumbers3").hide();
    }
    else if($(this).attr("href")=="#user-count"){
    	$("#count-pages1").hide();
    	$("#countNumbers1").hide();
    	$("#count-pages2").show();
    	$("#countNumbers2").show();
    	$("#count-pages3").hide();
    	$("#countNumbers3").hide();
    }
    else if($(this).attr("href")=="#sum"){
    	$("#count-pages1").hide();
    	$("#countNumbers1").hide();
    	$("#count-pages2").hide();
    	$("#countNumbers2").hide();
    	$("#count-pages3").show();
    	$("#countNumbers3").show();

    }
    var id=$(this).attr('href');
    $(id).css('display','block').siblings().css('display','none');
});

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

function checkIsAdmin(){
	var isAdmin = ${sessionScope.manager.powerId==1};
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	if(isAdmin){
		$.ajax({	
			   type: "POST",
			   url: "<%=basePath%>controller/manager/getAllGameInfos",
			   data: "",
			   success: function data(data){
				   var param = eval('('+data+')');
				   $("#totoalAccountCount").html(param.totoalAccountCount);
				   $("#AllRoomsCount").html(param.AllRoomsCount);
				   $("#consumedRoomCardCount").html(param.consumedRoomCardCount);
				   $("#onlineRoomsCount").html(param.onlineRoomsCount);
				   $("#todayRoomsCount").html(param.todayRoomsCount);
				   //$("#onlineAccountCount").html(param.onlineAccountCount);
				   $("#topOnlineAccountCount").html(param.topOnlineAccountCount);
				   $("#newAccountCountToday").html(param.newAccountCountToday);
			   	}
			});
	}else{
		getProxyMoney('<%=basePath%>',0);
		$("#adminContent").html("");
		$("#searchButton").html("");
	}
}
</script>
</body>
</html>