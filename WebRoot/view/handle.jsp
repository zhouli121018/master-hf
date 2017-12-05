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
    <title>异常处理</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/gm.css">

    <!--[if lt IE 9]>
    <script src="../js/html5shiv.min.js"></script>
    <script src="../js/respond.min.js"></script>
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
              <li><a href="#exchange">兑换明细</a></li>
              <li><a href="#notice">公告管理</a></li>
              <li><a href="#config">系统配置</a></li>
              <li class="active"><a href="#handle">异常处理</a></li>
              <%} %>
          </ul>
      </div>
          <button type="button" class="btn btn-danger logout" onclick="logout()">退出登录</button>
      </div>
  </nav>
   <div class="content">



    <div id="handle" class="active">
        <h2>异常处理</h2>
        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-inline form-btn">
                    <input type="number" min="1" class="search" id="serialNum" placeholder="请输入开奖期号查询"/>
                    <input type="button" class="btn btn-default" onclick="getPaylog()" value="查询"/>
                    <br><br>
                    <input type="number" min="1" class="search" id="inSerialNum" placeholder="请输入开奖期号"/>
                    <input type="text" class="search" id="lotResult" placeholder="请输入开奖结果"/>
                    <input type="button" class="btn btn-warning" id="insertLotResult" value="录入开奖结果并处理投注信息"/>
                   
                </form>
                <div class="table-responsive">
                    <table id="cathecticTbl" class="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th colspan="10" style="text-align:center;">基本信息</th>
                            <th colspan="2" style="text-align:center;">直属代理</th>
                            <th colspan="2" style="text-align:center;"> 上一级代理</th>
                        </tr>
                        <tr>
                            <th>投注人游戏ID</th>
                            <th>昵称</th>
                            <th>期号</th>
                            <th>投注红钻</th>
                            <th>投注时间</th>
                            <th>投注</th>
                            <th>投注结果</th>
                            <th>开奖号码</th>
                            <th>卡面值</th>
                            <th>卡张数</th>
                            
                            <th>代理邀请码</th>
                            <th>代理级别</th>
                            <th>代理邀请码</th>
                            <th>代理级别</th>
                        </tr>
                        </thead>
                        <tbody>
                        
                        
                        </tbody>
                    </table>
                </div>
                <div class="page">
                <ul id="cathectic-pages">
                </ul>
                    <p>共 <b id="totalRecords">0</b> 条记录</p>
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
<script src="../js/handle.js"></script>
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

$('#insertLotResult').on('click',function(){
	var inSerialNum = $('#inSerialNum').val();
	var lotResult = $('#lotResult').val();
	if(inSerialNum==""||lotResult==""){
		alert("请输入正确的信息！");
		$('#inSerialNum').focus();
		return;
	}
	var reg = /^[0-9],[0-9],[0-9]$/;
	if(!reg.test(lotResult)){
		alert('请输入正确的格式！如  1,1,1 ');
		$('#lotResult').focus();
		return;
	}
	$.ajax({
		url:"<%=basePath%>controller/manager/insertLotResult",
		data:{serialNum:inSerialNum,lotResult:lotResult},
		success:function(data){
			console.log(data);
			var res = JSON.parse(data);
			if(res.res>0){
				alert('操作成功！');
				getDetail(1,0,'<%=basePath%>',inSerialNum);
			}else{
				alert('操作未成功！请重新操作！');
				$('#inSerialNum').focus();
			}
		}
	})
	
})
function getPaylog(){
	var serialNum = $("#serialNum").val();
	if(serialNum==""){
		$("#serialNum").focus();
		return;
	}
	getDetail(1,0,'<%=basePath%>',serialNum);
}

</script>
</body>
</html>