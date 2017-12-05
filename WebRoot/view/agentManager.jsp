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
    <title>代理管理</title>
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
          <a class="navbar-brand" href="#">
              代理管理
          </a>
      </div>
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav" id="navlist">
              <li><a href="#info">代理信息</a></li>
              <%if(powerId==1){ %>
              <li  class="active"><a href="#agentManager">代理管理</a></li>
              <%} %>
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
              <li><a href="#handle">异常处理</a></li>
              <%} %>
              
          </ul>
      </div>
          <button type="button" class="btn btn-danger logout" onclick="logout()">退出登录</button>
      </div>
  </nav>
   <div class="content">
    <div id="agentManager" class="active">
        <h2>代理管理</h2>
        <div class="panel panel-default">
            <div class="panel-body">
                <form class="form-inline form-btn">
                    <input type="text" autofocus class="search" id="inputName" placeholder="请输入代理真实姓名"/>
                    <input type="number" class="search" id="inputInviteCode" min="1" placeholder="请输入代理邀请码查询"/>
                    <input type="number" class="search" id="inputUuid" min="1" placeholder="请输入用户ID"/>
                    <input type="button" class="btn btn-primary" onclick="" value="查询" id="search"/>
                </form>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover mainTbl tree">
                        
                        <tbody id="tbody">
                        </tbody>
                    </table>
                </div>
                
            </div>


        </div>
        <div id="agentDetail">
             <form>
                <button type="button" class="close"><span>&times;</span></button>
                <h4>代理信息</h4>
                <div id="agent-message">
                	<div class="form-group">
                           <label>姓名</label>
                           <input id="name" type="text" class="form-control" value="">
                           <p>请输入2-4位中文姓名</p>
                	</div>
                	<div class="form-group">
                           <input id="mid" style="display:none" value="">
                	</div>
         			<div class="form-group">
                    	<label>手机号</label>
                    	<input id="telephone" type="text" class="form-control" value="">
                    	<p>手机号码格式不正确，请重新输入(11位)</p>
        			</div>
         			<div class="form-group">
                   	 	<label>微信</label>
                    	<input id="weixin" type="text" class="form-control" value="">
         			</div>
         			<div class="form-group">
                    	<label>QQ号码</label>
                    	<input id="QQ" type="text" class="form-control" value="">
         			</div>
         			<div class="form-group">
                    	<label>邀请码</label>
                    	<input id="inviteCode" type="text" class="form-control" onchange="validCode()" value="">
                    	<p id="validInfo"></p>
    		        	<input type="hidden" id="checkCanSubmit" value="true">
         			</div>
         			<div class="form-group">
                    	<label>游戏用户ID</label>
                    	<input id="uuid" type="number" onchange="validUuid()" class="form-control" value="">
         			</div>
         			<div class="form-group">
                    	<label>分成比例</label>
                    	<input id="rebate" type="text" class="form-control" value="">
                    	<p>请输入正确的格式，如(0.7)</p>
         			</div>
         			<div class="form-group">
                    	<label>代理状态</label>
                    	<select id="status">
	                    	<option value="0">正常</option>
	                    	<option value="2">禁用</option>
                    	</select>
         			</div>
                	<div class="form-group">
                           <label>重新绑定上级代理邀请码</label>
                           <input id="parentInviteCode" type="text" class="form-control" value="" placeholder="请输入上级代理邀请码">
                           <p>请输入正确的格式</p>
               		</div>
               		<div class="form-group" id="rootManager">
        		       <label>是否能开代理</label> <br>
        		       <label> <input type="radio" name="rootManager" value="1" title="1">是</label>&nbsp;&nbsp;
        	           <label> <input type="radio" name="rootManager" value="0" title="0">否</label>
        			</div>
         			<div class="form-group">
                    	<label>代理级别</label>
                    	<select id="powerId" ><option value="5">皇冠代理</option><option value="4">钻石代理</option><option value="3">铂金代理</option><option value="2">黄金代理</option></select>
         			</div>
                </div>
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
                            <label>用户ID：</label>
                            <input type="text" name="userid" disabled class="form-control">
                            <input type="hidden" name="userid" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>充蓝钻：</label>
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
<script type="text/javascript">
//var canSubmit = true;
var managerUpId=0;
$('#search').click(function(){
	if($('#inputInviteCode').val()==''&& $('#inputUuid').val()=='' && $('#inputName').val()==''){
		alert('请输入正确信息查询！');
		return;
	}
	var uuid=$('#inputUuid').val();
	var inviteCode=$('#inputInviteCode').val();
	var name=$('#inputName').val();
	console.log(uuid,inviteCode);
	$.ajax({
		   url: "<%=basePath%>controller/manager/selectManagerByInviteCode",
		   data: {uuid:uuid,inviteCode:inviteCode,name:name},
		   success: function data(data){
			       $('#tbody').html("");
		 			var param = JSON.parse(data);
					console.log(param);
					console.log(param.status);
					if(param.status==1){
						var obj = param.manager;
						managerUpId = obj.managerUpId;
						var powerIdStr='';
						var rootManagerStr='';
						var statusStr='';
						if(obj.powerId==5){
							powerIdStr="皇冠代理";
						}else if(obj.powerId==4){
							powerIdStr="钻石代理";
						}else if(obj.powerId==3){
							powerIdStr="铂金代理";
						}else if(obj.powerId==2){
							powerIdStr="黄金代理";
						}
						if(obj.status==0){
							statusStr="正常";
						}else{
							statusStr="禁用";
						}
						if(obj.rootManager==1){
							rootManagerStr="是";
						}else{
							rootManagerStr="否";
						}
						var rebateStr = "";
						if(obj.rebate){
							rebateStr=obj.rebate;
						}else{
							rebateStr="";
						}
						var html="<tr><td>姓名</td><td>"+obj.name+"</td></tr>"+
						"<tr><td>代理编码</td><td>"+obj.id+"</td></tr>"+
						"<tr><td>手机号</td><td>"+obj.telephone+"</td></tr>"+
						"<tr><td>微 信</td><td>"+obj.weixin+"</td></tr>"+
						"<tr><td>QQ</td><td>"+obj.qq+"</td></tr>"+
						"<tr><td>邀请码</td><td>"+obj.inviteCode+"</td></tr>"+
						"<tr><td>用户ID</td><td>"+obj.password+"</td></tr>"+
						"<tr><td>分成比例</td><td>"+rebateStr+"</td></tr>"+
						"<tr><td>代理状态</td><td>"+statusStr+"</td></tr>"+
						"<tr><td>是否开代理</td><td>"+rootManagerStr+"</td></tr>"+
						"<tr><td>代理级别</td><td>"+powerIdStr+"</td></tr>"+
						"<tr><td>操作</td><td><a href='"+obj.id+"' class='btn btn-sm btn-warning'>修改</a> <a href='"+obj.id+"' class='btn btn-sm btn-success charge'>充房卡</a> <a href='"+obj.id+"' class='btn btn-sm btn-primary resetPwd'>密码重置</a></td>"+
						"</tr>";
						console.log(html);
						$('#tbody').html(html);
						$('#tbody').find('td').each(function(i,dom){
							if($(this).html()=="undefined"){
								$(this).html('');
							}
						})
					}
					
		   }
	})
})

function changeMessage(id,formId,tdId,c){
    var index=0;
    var str;
    var beforeCode;
    $(id).on('click','table td:last-child a',function(e){
        e.preventDefault();
        var that=this;

        var sel=$(this).parents('tr').children().first().text();
        var num=$(this).parents('tr').children()[1].innerHTML;
        if($(this).hasClass('delete')){
            if(confirm("确定删除"+sel+" 项吗？")){
                $(that).parents('tr').remove();
                deleteAgent(this);
            }
            return;
        }else if($(this).hasClass('resetPwd')){
        	var mid = $(this).parents('tbody').find('tr:eq(1) td:eq(1)').html();
        	console.log(mid);
            var str1=prompt('请输入新密码：');
            if(str1==null){
                return;
            }
            var str2=prompt('请再次输入新密码：');
            if(str1!=str2){
                alert('两次输入密码不一致！请重新输入！');
                return;
            }else if((str1!=null)&&str1==str2&&confirm("确定修改密码？")){
            		//var mid = $(this).parents('tr').attr('mark');
            		$.ajax({	
            			   type: "POST",
            			   url: sessionStorage['basePath']+"controller/manager/updateManagerPassword?newPwd="+str1+"&mid="+mid,
            			   data: "",
            			   success: function data(data){
            				  var returns =  eval('(' + data + ')');
            				  if(returns.status=='0'){
            					  alert('修改成功！');
            				  }
            				  else
            					 alert(returns.error);
            			   }
            		});
            	
            }
            return;

        }
        if($(this).hasClass('charge')){
            $("#charge").fadeIn();
        	$("#charge form .uname").val($(this).parents('tbody').find('tr:eq(0) td:eq(1)').html());
        	$("#charge form [name='userid']").val($(this).parents('tbody').find('tr:eq(6) td:eq(1)').html());
        	return;
        }
        $(formId).fadeIn();
        var name=$(this).parents('tbody').find('tr:eq(0) td:eq(1)').html();
        var mid=$(this).parents('tbody').find('tr:eq(1) td:eq(1)').html();
        var telephone=$(this).parents('tbody').find('tr:eq(2) td:eq(1)').html();
        var weixin=$(this).parents('tbody').find('tr:eq(3) td:eq(1)').html();
        var QQ=$(this).parents('tbody').find('tr:eq(4) td:eq(1)').html();
        var inviteCode=$(this).parents('tbody').find('tr:eq(5) td:eq(1)').html();
        var uuid=$(this).parents('tbody').find('tr:eq(6) td:eq(1)').html();
        var rebate=$(this).parents('tbody').find('tr:eq(7) td:eq(1)').html();
        var status=$(this).parents('tbody').find('tr:eq(8) td:eq(1)').html();
        var rootManager=$(this).parents('tbody').find('tr:eq(9) td:eq(1)').html();
        var powerId = $(this).parents('tbody').find('tr:eq(10) td:eq(1)').html();
        console.log(name);
        $('#name').val(name);
        $('#mid').val(mid);
        $('#telephone').val(telephone);
        $('#weixin').val(weixin);
        $('#QQ').val(QQ);
        $('#inviteCode').val(inviteCode);
        $('#uuid').val(uuid);
        $('#rebate').val(rebate);
        if(status=="正常"){
        	$('#status').val('0');
        }else{
        	$('#status').val('2');
        }
        if(rootManager=='是'){
        	$("#rootManager input").first().prop('checked',true);
        }else{
        	$("#rootManager input").last().prop('checked',true);
        }
        console.log(powerId);
        getParentInviteById(managerUpId,powerId);
        $(formId).find('input').each(function(i,dom){
        	if($(this).val()=="undefined"){
        		$(this).val('');
        	}
        })
    });
    
    $(formId+' .sure').click(function(){
    	saveManagerInfo();
    });
    
    $(formId+' .cancel').on('click',function(){
        $(formId).fadeOut();
    });
}
//changeMessage('#vip','#vipDetail','#vip-message',1);
changeMessage('#agentManager','#agentDetail','#agent-message',1);

$("#charge .cancel").click(function(){
	$("#charge").hide();
})
$("#charge .sure").click(function(){
	var str = $("#charge form").serialize();
	console.log(str);
	$("#charge").hide();
	$("#charge form [name='payCardNum']").val("");
	$.ajax({	
		   type: "POST",
		   url: sessionStorage['basePath']+"controller/manager/updateAccount",
		   data: str,
		   success: function data(data){
			   //成功返回之后重置userId,managerId
			  var param  = eval('('+data+')');
			  alert(param.msg);
		 }
	});
})


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
	var inviteCode = $("#inviteCode").val();
	$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/inviteCodeValidAgentManager?inviteCode="+inviteCode,
		   data: "",
		   success: function data(data){
			   var returns =  eval('(' + data + ')');
				var total = returns.valid;
				if(total){
				    $("#checkCanSubmit").val(true);
				}
				else{
					$("#checkCanSubmit").val(false);
					alert('该邀请码不可用');
				}
			}
		});
	return true;
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

function getParentInviteById(id,power){
	$.ajax({	
		   type: "POST",
		   url: "<%=basePath%>controller/manager/getParentInviteById?id="+id,
		   data: "",
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
		   
				if(power=="皇冠代理"){
			    	$('#powerId').val('5');
			    }else if(power=="钻石代理"){
			    	$('#powerId').val('4');
			    }else if(power=="铂金代理"){
			    	$('#powerId').val('3');
			    }else if(power=="黄金代理"){
			    	$('#powerId').val('2');
			    }
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

function saveManagerInfo(){
		var weixin = $("#weixin").val();
		var qq = $("#QQ").val();
		var name = $("#name").val();
		var telephone = $("#telephone").val();
		var uuid = $("#uuid").val();
		var rebate = $("#rebate").val();
		var powerId = $("#powerId").val();
		var status = $("#status").val();
		var inviteCode = $("#inviteCode").val();
		var parentInviteCode = $("#parentInviteCode").val();
		var mid=$('#mid').val();
		var rootManager = $("input[name='rootManager']:checked").val();
		
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
// 		var nreg=/^([\u4e00-\u9fa5]){2,4}$/;
// 		if(!nreg.test($("#name").val())){
// 				$("#name").focus();
// 	        	$("#name").removeClass("success");
// 	        	$("#name").addClass('has-err').next().addClass('error');
// 	        	isCheckOk=false;
// 	            return;
// 		}
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
			$('#add-message').hide();
			console.log("mid="+mid+"&weixin="+weixin+"&qq="+qq+"&name="+name+"&telephone="+telephone+"&uuid="+uuid+"&powerId="+powerId+"&inviteCode="+inviteCode+"&parentInviteCode="+parentInviteCode+"&rootManager="+rootManager);
			
			$.ajax({	
				   type: "POST",
				   url: "<%=basePath%>controller/manager/updateManagerInfo",
				   data: "mid="+mid+"&weixin="+weixin+"&qq="+qq+"&name="+name+"&telephone="+telephone+"&uuid="+uuid+"&powerId="+powerId+"&inviteCode="+inviteCode+"&parentInviteCode="+parentInviteCode+"&rootManager="+rootManager+"&rebate="+rebate+"&status="+status,
				   success: function data(data){
				 			var param = eval('('+data+')');
							if(param.status == "0"){
								  alert("资料提交成功");
								  var powerIdStr='';
	          						var rootManagerStr='';
	          						var statusStr='';
	          						if(powerId==5){
	          							powerIdStr="皇冠代理";
	          						}else if(powerId==4){
	          							powerIdStr="钻石代理";
	          						}else if(powerId==3){
	          							powerIdStr="铂金代理";
	          						}else if(powerId==2){
	          							powerIdStr="黄金代理";
	          						}
	          						if(status==0){
	          							statusStr="正常";
	          						}else{
	          							statusStr="禁用";
	          						}
	          						if(rootManager==1){
	          							rootManagerStr="是";
	          						}else{
	          							rootManagerStr="否";
	          						}
	          						var html="<tr><td>姓名</td>"+"<td>"+name+"</td></tr>"+
	          						"<tr><td>代理编码</td><td>"+mid+"</td></tr>"+
	          						"<tr><td>手机号</td><td>"+telephone+"</td></tr>"+
	          						"<tr><td>微 信</td><td>"+weixin+"</td></tr>"+
	          						"<tr><td>QQ</td><td>"+qq+"</td></tr>"+
	          						"<tr><td>邀请码</td><td>"+inviteCode+"</td></tr>"+
	          						"<tr><td>用户ID</td><td>"+uuid+"</td></tr>"+
	          						"<tr><td>分成比例</td><td>"+rebate+"</td></tr>"+
	          						"<tr><td>代理状态</td><td>"+statusStr+"</td></tr>"+
	          						"<tr><td>是否开代理</td><td>"+rootManagerStr+"</td></tr>"+
	          						"<tr><td>代理级别</td><td>"+powerIdStr+"</td></tr>"+
	          						"<tr><td>操作</td><td><a href='#' class='btn btn-sm btn-warning'>修改</a> <a href='#' class='btn btn-sm btn-success charge'>充房卡</a> <a href='#' class='btn btn-sm btn-primary resetPwd'>密码重置</a></td>"+
	          						"</tr>";
	          						console.log(html);
	          						$('#tbody').html(html);
							}
							else{
							 	alert(param.error);
							}
							
		         		//更新个人信息展示	
							$("#agentDetail").hide();
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