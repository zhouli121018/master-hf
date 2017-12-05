<%@ page language="java" import="java.util.*,com.weipai.model.Manager" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int powerId = ((Manager)session.getAttribute("manager")).getPowerId();
	int rootManager = ((Manager)session.getAttribute("manager")).getRootManager();
	int inviteCode = ((Manager)session.getAttribute("manager")).getInviteCode();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-cn">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title>我的代理</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/jquery.treegrid.css">
    <link rel="stylesheet" href="../css/gm.css">
    <!--[if lt IE 9]>
    <script src="../js/html5shiv.min.js"></script>
    <script src="../js/respond.min.js"></script>
    <![endif]-->
    <script>
      sessionStorage['powerId']=<%=powerId%>;
      sessionStorage['rootManager']=<%=rootManager%>;
      sessionStorage['inviteCode']=<%=inviteCode%>;
    </script>
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
              <li  class="active"><a href="#agent">我的代理</a></li>
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
              <li><a href="#handle">异常处理</a></li>
              <%} %>
              
          </ul>
      </div>
          <button type="button" class="btn btn-danger logout" onclick="logout()">退出登录</button>
      </div>
  </nav>
   <div class="content">
    <div id="agent" class="active">
        <h2>我的代理</h2>
        <%if(powerId==1||powerId==5||powerId==4){ %>
        <button type="button" class="btn  btn-success">
            <span class="glyphicon glyphicon-plus"></span>
            新增代理
        </button>
		<%} %>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="clear time">
                    <ul class="nav nav-pills pull-right">
                    	<li><a href="#" onclick="getAccount(1,0,'<%=basePath%>',${sessionScope.manager.powerId==1})">全部充值</a><li>
                        <li ><a href="#" onclick="getAccount(1,4,'<%=basePath%>',${sessionScope.manager.powerId==1})">上月充值</a><li>
                        <li><a href="#" onclick="getAccount(1,3,'<%=basePath%>',${sessionScope.manager.powerId==1})">本月充值</a><li>
                        <li><a href="#" onclick="getAccount(1,2,'<%=basePath%>',${sessionScope.manager.powerId==1})">上周充值</a><li>
                        <li><a href="#" onclick="getAccount(1,1,'<%=basePath%>',${sessionScope.manager.powerId==1})">本周充值</a><li>
                        <li><a href="#" onclick="getAccount(1,6,'<%=basePath%>',${sessionScope.manager.powerId==1})">昨天充值</a><li>
                        <li  class="active"><a href="#" onclick="getAccount(1,5,'<%=basePath%>',${sessionScope.manager.powerId==1})">今天充值</a><li>
                    </ul>
                </div>
                
                
                <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-inline form-btn">
                    <input type="text" autofocus class="search" id="inputName" placeholder="请输入代理姓名"/>
                    <input type="number" class="search" id="inputInviteCode" min="1" placeholder="请输入代理邀请码"/>
                    <input type="number" class="search" id="inputParentInviteCode" min="1" placeholder="请输入上级代理邀请码"/>
                    <input type="number" class="search" id="inputUuid" min="1" placeholder="请输入关联用户ID"/>
                    <select id="inputPowerId"><option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option></select>
                    <input type="button" class="btn btn-primary" onclick="getAccount(1,5,'<%=basePath%>',${sessionScope.manager.powerId==1})" value="查询" id="search"/>
                	
                </form>
                
                
            </div>


        </div>
                
                
                
                <div class="table-responsive">

                    <table class="table table-bordered table-striped table-hover mainTbl tree">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>代理编码</th>
                            <th>手机号</th>
                            <th>微信</th>
                            <th>QQ号码</th>
                            <th>邀请码</th>
                            <th>游戏ID</th>
                            <th>创建时间</th>
                            <th>上次登录</th>
                            <th>会员数</th>
                            <th>金额</th>
                            <th>钻石数量</th>
                            <th>分成比例</th>
                            <%if(powerId>1){ %>
                            <th>上级名字</th>
                            <th>上级邀请码</th>
                            <%} %>
                            <th>是否开代理</th>
                            <th>代理级别</th>
                            <th>编辑</th>
                            
                        </tr>
                        </thead>
                        <tbody >
                        
                        </tbody>
                    </table>
                </div>
                <div class="page">
                <ul id="agent-pages"></ul>
                    <p>共 <b>1</b> 条记录</p>
                    <!--<div class="row">-->
                        <!--<div class="col-xs-4 col-sm-3 col-md-2">会员总数：<span>0</span></div>-->
                        <!--<div class="col-xs-8 col-sm-9 col-md-10">黄金代理总数量：<span>3</span></div>-->
                    <!--</div>-->

                </div>
            </div>


        </div>

        <div id="agent-pages"></div>

        <div id="add-message">
            <form name="addAgentForm">
                <button type="button" class="close"><span>&times;</span></button>
                <h4>新增代理信息</h4>
                <div id="addAgentMessage"></div>
                <input type="button"  class="btn  btn-success sureAdd" onclick="saveManagerInfo(this)" value="增加代理"/>
                <input type="button" class="btn  btn-danger cancel" value="取 消"/>

            </form>
        </div>
        <div id="agentDetail">
            <form>
                <button type="button" class="close"><span>&times;</span></button>
                <h4>代理信息</h4>
                <div id="agent-message"></div>
                <input type="button"  class="btn  btn-success sure"  value="修 改"/>
                <input type="button" class="btn  btn-danger cancel" value="取 消"/>
            </form>
        </div>
         <div id="charge">
                <form>
                    <button type="button" class="close"><span>&times;</span></button>
                    <h4>代理充值信息</h4>
                    <div class="chargeDetail">
                        <div class="form-group">
                            <label>代理姓名：</label>
                            <input type="text"  disabled class="uname form-control">
                        </div>
                        <div class="form-group">
                            <label>代理编号：</label>
                            <input type="text" name="num" disabled class="form-control">
                            <input type="hidden" name="num"  class="form-control">
                        </div>
                        <div class="form-group">
                            <label>充钻石：</label>
                            <input type="number" name="payCardNum"  class="form-control">
                            <p>请输入正确的充钻数量</p>
                        </div>
                    </div>
                    <input type="button" class="btn  btn-success sure" value="确 定"/>
                    <input type="button" class="btn  btn-danger cancel" value="取 消"/>
                </form>
            </div>
    </div>


   </div>
</div>
<script src="../js/jquery-1.11.3.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap-paginator.js"></script>
<script src="../js/bootlint.js"></script>
<script type="text/javascript" src="../js/jquery.treegrid.min.js"></script>
<script src="../js/gm.js"></script>
<script src="../js/agent1.js"></script>
<script type="text/javascript">
//var canSubmit = true;

function deleteAgent(obj){
	var delId=$(obj).parents('tr').children()[1].innerHTML;
    //console.log(delId);
    $.ajax({
	   url: "<%=basePath%>controller/manager/deleteProxyAccount",
	   data: "id="+delId,
	   success: function data(data){
	 			var param = eval('('+data+')');
				console.log(param);
	   	}
    })
}

function validCode(){
	var res = true;
	var mid = $("#mid").val();
	var inviteCode = $("#inviteCode").val();
	if(typeof(mid)=="undefined")
		mid="";
	$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/inviteCodeValid?inviteCode="+inviteCode+"&id="+mid,
		   data: "",
		   async: false,
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
				var total = returns.valid;
				res = total;
				if(total){
					//$("#validInfo").html("该邀请码可用");
				    $("#checkCanSubmit").val(true);
				    $("#inviteCode").addClass('success').removeClass('has-err');
				    return true;
				}
					
				else{
					//$("#validInfo").html("该邀请码不可用");
					$("#checkCanSubmit").val(false);
					alert('该邀请码不可用');
					$("#inviteCode").addClass('has-err').removeClass('success');
					//$("#inviteCode").focus();
					return false;
				}
			}
		});
}
function validUuid(){
	var res=true;
	var uuid = $("#uuid").val();
	$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/validUuid?uuid="+uuid,
		   data: "",
		   async: false,
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
				var total = returns.valid;
				//console.log(total);
				if(total){
					//return true;
					//$("#validInfo").html("该邀请码可用");
				    $("#checkCanSubmit").val(true);
				    $("#uuid").addClass('success').removeClass('has-err');
				}
				else{
					//$("#validInfo").html("该邀请码不可用");
					$("#checkCanSubmit").val(false);
					$("#uuid").addClass('has-err').removeClass('success');
					alert('此用户ID不存在或此用户已是代理,请重新输入');
					//return false;
				}
				
				 res = total;
			}
	});
	return res;
}
function getParentInvite(){
	var uuid = $("#uuid").val();
	$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/getParentInvite?uuid="+uuid,
		   data: "",
		   async: false,
		   success: function data(data){
			    var returns =  eval('(' + data + ')');
			    //console.log(returns);
				var total = returns.inviteCode;
				var powerId = returns.powerId;
				$("#parentInviteCode").val(total);
				if(powerId==5){
					var html=`<option value="0">--请选择--</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`;
					$("#powerId").html(html);
				}else if(powerId==4){
					var html=`<option value="0">--请选择--</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`;
					$("#powerId").html(html);
				}else if(powerId==3){
					var html=`<option value="0">--请选择--</option><option value="2">黄金代理</option>`;
					$("#powerId").html(html);
				}else  if(powerId==1){
					var html=`<option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`;
					$("#powerId").html(html);
					
				}
// 				var isAdmin = ${sessionScope.manager.powerId==1};
// 				if(!isAdmin)
// 					$("#parentInviteCode").attr("disabled","disabled");
			}
		});
}
function getParentInviteByChild(ci){
	$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/getParentInviteByChild?inviteCode="+ci,
		   data: "",
		   async: false,
		   success: function data(data){
			    var returns =  eval('(' + data + ')');
			    //console.log(returns);
				var total = returns.inviteCode;
				var powerId = returns.powerId;
				$("#agent-message #parentInviteCode").val(total);
// 				if(powerId==5){
// 					var html=`<option value="0">--请选择--</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`;
// 					$("#powerId").html(html);
// 				}else if(powerId==4){
// 					var html=`<option value="0">--请选择--</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`;
// 					$("#powerId").html(html);
// 				}else if(powerId==3){
// 					var html=`<option value="0">--请选择--</option><option value="2">黄金代理</option>`;
// 					$("#powerId").html(html);
// 				}else  if(powerId==1){
// 					var html=`<option value="0">--请选择--</option><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option>`;
// 					$("#powerId").html(html);
					
// 				}
			}
		});
}

function saveManagerInfo(o){
		var weixin = $("#weixin").val();
		var qq = $("#QQ").val();
		var name = $("#name").val();
		var telephone = $("#telephone").val();
		var uuid = $("#uuid").val();
		var rebate = $("#rebate").val();
		var powerId = $("#powerId").val();
		var inviteCode = $("#inviteCode").val();
		var parentInviteCode = $("#parentInviteCode").val();
		var mid="";
		var rootManager = $("input[name='rootManager']:checked").val();
		/*
		if(typeof(weixin)=="undefined")
			weixin="";
		if(typeof(qq)=="undefined")
			qq="";
		if(typeof(name)=="undefined")
			name="";
		if(typeof(telephone)=="undefined")
			telephone="";
		if(typeof(uuid)=="undefined")
			uuid="";
		if(typeof(powerId)=="undefined")
			powerId="";
		if(typeof(inviteCode)=="undefined")
			inviteCode="";
		if(typeof(parentInviteCode)=="undefined")
			parentInviteCode="";
		*/
		
		var isCheckOk=true;
		function checkRequired(id){
			if($(id).val().length==0){
				$(id).focus();
				$(id).removeClass('success');
	            $(id).addClass('has-err').next().addClass('error');
				isCheckOk=false;
				return false;
			}else{
				return true;
			}
		}
		checkRequired("#name");
		if(!checkRequired("#name")){
			return;
		}
		var nreg=/^([\u4e00-\u9fa5]){2,4}$/;
		if(!nreg.test($("#name").val())){
				$("#name").focus();
	        	$("#name").removeClass("success");
	        	$("#name").addClass('has-err').next().addClass('error');
	        	isCheckOk=false;
	            return;
		}
		
		var rebetreg=/^0\.\d{1,2}$/;
		if($("#rebate").val()!=""&& (!rebetreg.test($("#rebate").val()))){
				$("#rebate").focus();
	        	$("#rebate").removeClass("success");
	        	$("#rebate").addClass('has-err').next().addClass('error');
	        	isCheckOk=false;
	            return;
		}else{
			$("#rebate").removeClass("has-err").next().removeClass('error');
        	$("#rebate").addClass('success');
		}
		
		var reg=/^1[34578]\d{9}$/;
        if(!reg.test($("#telephone").val())){
        	$("#telephone").focus();
        	$("#telephone").removeClass("success");
        	$("#telephone").addClass('has-err').next().addClass('error');
        	isCheckOk=false;
            return;
        }
        checkRequired("#weixin");
        if(!checkRequired("#weixin")){
			return;
		};
        checkRequired("#inviteCode");
        if(!checkRequired("#inviteCode")){
			return;
		};
        checkRequired("#uuid");
        if(!checkRequired("#uuid")){
			return;
		};
		if($("#powerId").val()=="0"){
			isCheckOk=false;
			alert("请选择代理级别！");
			return;
		}
        //checkRequired("#parentInviteCode");
        //if(!checkRequired("#parentInviteCode")){
		//	return;
		//};
		
		var checkCanSubmit = $("#checkCanSubmit").val()&& isCheckOk;
		//console.log($("#agent .checkCanSubmit").val());
		//var canSubmit = validCode();
		if(checkCanSubmit){
			$('#add-message').fadeOut();
			
			
			console.log("mid="+mid+"&weixin="+weixin+"&qq="+qq+"&name="+name+"&telephone="+telephone+"&uuid="+uuid+"&powerId="+powerId+"&inviteCode="+inviteCode+"&parentInviteCode="+parentInviteCode+"&rootManager="+rootManager);
			var sel = o;
			$.ajax({	
				   type: "POST",
				   url: "<%=basePath%>controller/manager/updateManagerInfo",
				   data: "mid="+mid+"&weixin="+weixin+"&qq="+qq+"&name="+name+"&telephone="+telephone+"&uuid="+uuid+"&powerId="+powerId+"&inviteCode="+inviteCode+"&parentInviteCode="+parentInviteCode+"&rootManager="+rootManager+"&rebate="+rebate,
				   success: function data(data){
				 			var param = eval('('+data+')');
							if(param.status == "0"){
								  alert("资料提交成功");
								  if(param.managerId){
									  var html='';
// 									  html=`<tr><td>"+name+"</td><td>"+param.managerId+"</td><td>"+telephone+"</td><td>"+weixin+"</td>"+"<td>"+qq+"</td><td>"+inviteCode+"</td><td>"+uuid+"</td><td>"+"</td><td>0</td><td>正常</td><td>"+rootManager==1?'是':'否'+"</td><td>"+powerId==5?'皇冠代理':(powerId==4?'钻石代理':(powerId==3?'铂金代理':(powerId==2?'黄金代理':'管理员')))+"</td>"+
// 									  "<td class='adminEdit'><a href='"+param.managerId+"' class='btn btn-sm btn-warning'>修改</a> <a href='"+param.managerId+"' class='btn btn-sm btn-success charge'>充房卡</a> <a class='btn btn-sm btn-danger delete'  href='"+param.managerId+"'>删除</a>  <a href='"+param.managerId+"' class='btn btn-sm btn-primary resetPwd'>密码重置</a></td></tr>`;
									 var root = rootManager==1?'是':'否';
									 var newpowerId =( powerId==5?'皇冠代理':(powerId==4?'钻石代理':(powerId==3?'铂金代理':(powerId==2?'黄金代理':'管理员'))));
									  html="<tr>"+
									  "<td><b>"+name+"</b></td>"+
									  "<td>"+param.managerId+"</td>"+
									  "<td>"+telephone+"</td>"+
									  "<td>"+weixin+"</td>"+
									  "<td>"+qq+"</td>"+
									  "<td>"+inviteCode+"</td>"+
									  "<td>"+uuid+"</td>"+
									  "<td>0</td>"+
									  "<td>0</td>"+
									  "<td>正常</td>"+
									  "<td>"+rebate+"</td>"+
									  "<td>"+root+"</td>"+
									  "<td>"+newpowerId+"</td>"+
									  "<td class='adminEdit'><a href='"+param.managerId+"' class='btn btn-sm btn-warning'>修改</a> <a href='"+param.managerId+"' class='btn btn-sm btn-success charge'>充房卡</a> <a class='btn btn-sm btn-danger delete'  href='"+param.managerId+"'>删除</a>  <a href='"+param.managerId+"' class='btn btn-sm btn-primary resetPwd'>密码重置</a></td></tr>";
// 									  $("#agent table tbody").append(html);
// 									  //console.log(111,html);
<%-- 									  if(<%=powerId%>!=1){ --%>
// 										  $('.adminEdit').html("<a class='btn btn-sm btn-warning'>修改</a> <a class='btn btn-sm btn-success charge'>充房卡</a>");
// 									  }
								  }
							}
							else{
							 	alert(param.error);
							}
							
		         		//更新个人信息展示	
		         		    $(sel).prev().html('');
							$("#agentDetail").fadeOut();
							$("#add-message").fadeOut();
				   	}
				});
		
		}	else{
			alert("邀请码不可用！");
		}
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

 

//
</script>
</body>
</html>