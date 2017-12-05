<%@ page language="java" import="java.util.*,com.weipai.model.Manager" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int powerId = ((Manager)session.getAttribute("manager")).getPowerId();
	int managerId = ((Manager)session.getAttribute("manager")).getId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-cn">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title>我的会员</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/gm.css">

    <!--[if lt IE 9]>
    <script src="../js/html5shiv.min.js"></script>
    <script src="../js/respond.min.js"></script>
    <![endif]-->
    <script>
       if(<%=powerId%>!=1){
    	   sessionStorage['managerId']=<%=managerId%>;
       }
       sessionStorage['powerId']=<%=powerId%>;
    </script>
</head>
<body onload="getUser(1,0,null,null,null)">
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
              <li class="active"><a href="#vip">我的会员</a></li>
              <li><a href="#detail">账单明细</a></li>
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

    <div id="vip" class="active">
        <h2>我的会员</h2>
        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-inline form-btn text-right">
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">开始时间</div>
                            <input type="date" class="form-control" id="startTime"  placeholder="开始时间"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                            <div class="input-group-addon">结束时间</div>
                            <input type="date" class="form-control" id="endTime" placeholder="结束时间"/>
                        </div>
                    </div>
                    <input type="text" class="search" id="nickName" placeholder="请输入玩家昵称"/>
                    <input type="number" min="1" class="search" id="accountUuid" placeholder="请输入玩家id"/>
                    <input type="button" class="btn btn-default" onclick="searchAccount()" value="查询"/>

                </form>
                <div class="table-responsive">
                    <table id="vipTbl" class="table table-bordered table-striped table-hover">
                        <thead>
                        <tr>
                            <th>账号ID</th>
                            <th>账号昵称</th>
                            <th>剩余蓝钻</th>
                            <th>剩余红钻</th>
                            <th>状态</th>
                            <th>注册时间</th>
                            <th>绑定的邀请码</th>
                            <th>编辑</th>
                            
                        </tr>
                        </thead>
                        <tbody id="userTbody">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div><ul id="vip-pages"></ul><p>共 <b id="totalNum">1</b> 条记录</p></div>
        <div id="vipDetail">

            <form name="addAgentForm">
                <button type="button" class="close"><span>&times;</span></button>
                <h4>会员信息</h4>
                <!--<div class="form-group">-->
                    <!--<label >姓名：</label>-->
                    <!--<input type="text" name="uName" class="form-control"  placeholder="请输入姓名">-->
                    <!--<p>请输入2-7位正确格式的姓名</p>-->
                <!--</div>-->
                <!--<div class="form-group">-->
                    <!--<label>手机号：</label>-->
                    <!--<input type="text" name="phoneNum" class="form-control" placeholder="请输入手机号">-->
                    <!--<p>手机号码格式不正确，请输入11位手机号码</p>-->
                <!--</div>-->
                <!--<div class="form-group">-->
                    <!--<label >微信号：</label>-->
                    <!--<input type="text" name="wxNum" class="form-control"  placeholder="请输入微信号">-->
                    <!--<p>微信格式不正确，请重新输入</p>-->
                <!--</div>-->
                <!--<div class="form-group">-->
                    <!--<label >游戏用户ID：</label>-->
                    <!--<input type="text" name="uId" class="form-control"  placeholder="请输入用户ID">-->
                    <!--<p>游戏用户ID格式不正确，请重新输入</p>-->
                <!--</div>-->
                <div id="vip-message"></div>

                <input type="button"  class="btn  btn-success sure" value="修 改"/>
                <input type="button" class="btn  btn-danger cancel" value="取 消"/>
            </form>
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
<script src="../js/vip.js"></script>
<script type="text/javascript">

function getUser(pageIndex,type,startTime,endTime,nickName){
	var isAdmin = ${sessionScope.manager.powerId==1};
	
	if(nickName==undefined){
		nickName=null;
	}
	$.ajax({	
		   url: "<%=basePath%>controller/manager/getAccounts?pageNum="+pageIndex+"&startTime="+startTime+"&endTime="+endTime+"&nickName="+nickName,
		   contentType: "application/x-www-form-urlencoded; charset=utf-8",
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
				var param = returns.users;
				var totalPages = parseInt(returns.totalNum/10)+1;
				$("#totalNum").html(returns.totalNum);
				$("#userTbody").html("");
		        $.each(param, function (i, item) {  
		        	var statusStr='';
		        	  if(param[i].status=="0"){
		        		  statusStr="<option value='1'>标记红名</option><option value='2'>禁用</option>";
		        	  }else if(param[i].status=="1"){
		        		  statusStr="<option value='0'>取消红名</option><option value='2'>禁用</option>";
		        	  }else if(param[i].status=="2"){
		        		  statusStr="<option value='0'>启用</option>";
		        	  }
		              var td = "<td>"+param[i].uuid+"</td>";
									td+="<td>"+param[i].nickname+"</td>";
									td+="<td>"+param[i].roomcard+"</td>";
									td+="<td>"+param[i].redCard+"</td>";
									if(param[i].status =="0"){
										td+="<td>正常</td>";
									}else if(param[i].status =="1"){
										td+="<td>红名</td>";
									}else if(param[i].status =="2"){
										td+="<td>注销</td>";
									}	
									td+="<td>"+param[i].createTimeCN+"</td>";
									td+="<td>"+param[i].managerUpId+"</td>";
									if(sessionStorage['powerId']==1||sessionStorage['powerId']==5){
										td+="<td class='editVip'><a class='btn btn-success btn-sm' href='#'>充值</a><span class='input-group'><select>"+statusStr+"</select><button  class='btn btn-warning updateStatus'>修 改</button></span></td>"
										
									}else{
										td+="<td class='editVip'><a class='btn btn-success btn-sm' href='#'>充值</a></td>";
									}
					//添加每行数据
					$("#userTbody").append("<tr>"+td+"</tr>");
		          }); 
		        if(totalPages>1){
		        	var options = {
		                    currentPage: pageIndex,
		                    totalPages: totalPages,
		                    bootstrapMajorVersion: 3,
		                    onPageChanged: function(e,oldPage,newPage){
		                    	getUser(newPage,0,startTime,endTime);
		                    }
		                }

		                $('#vip-pages').bootstrapPaginator(options);
		        }else{
		        	 $('#vip-pages').html("");
		        }
		        
		        $("td").each(function(){
		        	if($(this).html()=='undefined'){
		        		$(this).html("----");
		        	}
		          });
			}
		});
}



function searchAccount(){
	var accountUuid = $("#accountUuid").val();
	var isAdmin = ${sessionScope.manager.powerId==1};
	if(accountUuid != "请输入玩家id" && accountUuid != ""){
		$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/searchAccountByUuid",
		   data: "accountUuid="+accountUuid,
		   success: function data(data){
			   
		 			var param = eval('('+data+')');
		 			//超级管理员需要先移除列表
					if(param.status_code == "0"){
		 				$("#userTbody").html("");
		 				var statusStr='';
			        	  if(param.data.status=="0"){
			        		  statusStr="<option value='1'>标记红名</option><option value='2'>禁用</option>";
			        	  }else if(param.data.status=="1"){
			        		  statusStr="<option value='0'>取消红名</option><option value='2'>禁用</option>";
			        	  }else if(param.data.status=="2"){
			        		  statusStr="<option value='0'>启用</option>";
			        	  }
			        	  
					     var td = "<td>"+param.data.uuid+"</td>";
							    td+="<td>"+param.data.nickname+"</td>";
								td+="<td class='roomCard'>"+param.data.roomcard+"</td>";
								td+="<td>"+param.data.redCard+"</td>";
									if(param.data.status =="0"){
										td+="<td>正常</td>";
									}else if(param.data.status =="1"){
										td+="<td>红名</td>";
									}else if(param.data.status =="2"){
										td+="<td>注销</td>";
									}	
									td+="<td>"+param.data.createTimeCN+"</td>";
									td+="<td>"+param.data.managerUpId+"</td>";
									if(sessionStorage['powerId']==1||sessionStorage['powerId']==5){
										td+="<td class='editVip'><a class='btn btn-success btn-sm' href='#'>充值</a><span class='input-group'><select>"+statusStr+"</select><button  class='btn btn-warning updateStatus'>修 改</button></span></td>"
										
									}else{
										td+="<td class='editVip'><a class='btn btn-success btn-sm' href='#'>充值</a></td>";
									}
							//添加每行数据
							$("#userTbody").append("<tr>"+td+"</tr>");
							$("#userTbody td").each(function(i,dom){
								if($(dom).html()=='undefined'){
									$(dom).html("----");
								}
							})
					}
					else{
					 	alert(param.error);
					}
		   	}
		});
	}
	else{
		var nickName = $("#nickName").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		getUser(1,0,startTime,endTime,nickName);
	}
}


function updateA(){
	
}

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