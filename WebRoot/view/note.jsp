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
    <title>提现流水</title>
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
              <li class="active"><a href="#note">提现流水</a></li>
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
    <div id="note" class="active">
        <div class="note-detail">
            <h2>提现流水</h2>
            <div class="row">
                <div class="col-md-6 col-xs-12">
                    <ul class="nav nav-pills">
                        <li  class="active"><a onclick="getTixianlog(1,0)">全部提现</a></li>
                        <li><a onclick="getTixianlog(1,4)">上月提现</a></li>
                        <li><a onclick="getTixianlog(1,3)">本月提现</a></li>
                        <li><a onclick="getTixianlog(1,2)">上周提现</a></li>
                        <li><a onclick="getTixianlog(1,1)">本周提现</a></li>
                        <li><a onclick="getTixianlog(1,6)">昨天提现</a></li>
                        <li><a onclick="getTixianlog(1,5)">今天提现</a></li>
                    </ul>
                </div>
                <div class="col-md-6 col-xs-12" id="searchButton">
                    <input type="number" id="inviteCode" min="1" placeholder="请输入代理邀请码" style="height:35px;margin-bottom:2px"/>
                    <input type="button" value="查询代理提现" onclick="getTixianlog(1,0)" class="btn btn-default"/>

                </div>
            </div>
            
            <div class="panel panel-default">
                <div class="panel-body">
                    <p id="tixianSum">提现总金额：<b>0</b> 元</p>
                    <table  class="table table-bordered table-striped table-hover table-responsive">
                        <thead>
                        <tr>
                            <th>提现账号</th>
                            <th>提现金额(￥)</th>
                            <th>是否处理</th>
                            <th>提现时间</th>
                        </tr>
                        </thead>
                        <tbody id="tixianlogTbody">
                        </tbody>
                    </table>
                </div>
                <div>
                <ul id="note-pages"></ul>
                <p>共 <b id="totalNum">1</b> 条记录</p>
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

function getTixianlog(pageIndex,type){
	var inviteCode = $("#inviteCode").val();
	$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/withdrawlogs?pageNum="+pageIndex+"&payType=1&type="+type+"&inviteCode="+inviteCode,
		   data: "",
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
			  // console.log(returns);
				var param = returns.paylogs;
				var totalPages = returns.totalNum/10+1;
				$("#totalNum").html(returns.totalNum);
				$("#tixianlogTbody").html("");
		        $.each(param, function (i, item) {
		        	var td = "<td>"+param[i].managerid+"</td>";
		        	td+="<td>"+param[i].money+"</td>";
					/* td+="<td>****</td>"; */
		        	if(param[i].status==1){
						td+="<td>已完成</td>";
					}else{
						td+="<td>提现失败</td>";
					}
					
					td+="<td>"+param[i].dateString+"</td>";
					//添加每行数据
					$("#tixianlogTbody").append("<tr>"+td+"</tr>");
		          }); 
		        $("#tixianSum b").html(returns.tixianSum);
		        if(totalPages>1){
		        	var options = {
		                    currentPage: pageIndex,
		                    totalPages: totalPages,
		                    bootstrapMajorVersion: 3,
		                    onPageChanged: function(e,oldPage,newPage){
		                    	getTixianlog(newPage,type);
		                    }
		                }

		                $('#note-pages').bootstrapPaginator(options);
		        }else{
		        	 $('#note-pages').html("");
		        }
		        
		        $("td").each(function(){
		        	if($(this).html()=='undefined'){
		        		$(this).html("----");
		        	}
		          });
			}
		});
}
$(function(){
	getTixianlog(1,0);
})


</script>
</body>
</html>