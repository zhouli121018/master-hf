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
    <title>公告管理</title>
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/gm.css">

    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>
<body onload="getNotice()">
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
              <li class="active"><a href="#notice">公告管理</a></li>
              <li><a href="#config">系统配置</a></li>
              <li><a href="#handle">异常处理</a></li>
              <%} %>
          </ul>
      </div>
          <button type="button" class="btn btn-danger logout" onclick="logout()">退出登录</button>
      </div>
  </nav>
   <div class="content">
    <div id="notice" class="active">
        <h2>公告管理</h2>
        <button  type="button" class="btn  btn-success add-notice">
            <span class="glyphicon glyphicon-plus"></span>
            新增公告
        </button>
        <div class="panel panel-default">
            <div class="panel-body">
                <div>
                    <div class="table-responsive">
                        <table id="noticeTbl" class="table table-bordered table-striped table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>公告内容</th>
                                <th>代理Id</th>
                                <th>公告类型</th>
                            </tr>
                            </thead>
                            <tbody>
                            
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>


       
        <div id="notice-pages"></div>
        <div id="add-notice">
            <form>
                <button type="button" class="close"><span>&times;</span></button>
                <h4>新增公告信息</h4>
                <div class="add"></div>
                <input type="button" onclick="saveNotice()" class="btn  btn-success sure" value="确 定"/>
                <input type="button" class="btn  btn-danger cancel" value="取 消"/>
            </form>
        </div>
        <div id="noticeDetail">
            <form>
                <button type="button" class="close"><span>&times;</span></button>
                <h4>公告信息</h4>
                <div id="notice-message"></div>
                <input type="button"  class="btn  btn-success sure" value="修 改"/>
                <input type="button" class="btn  btn-danger cancel" value="取 消"/>
            </form>
        </div>
    </div>
   </div>
</div>
<script src="../js/jquery-1.11.3.js"></script>
<script src="../js/ajaxfileupload.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/bootstrap-paginator.js"></script>
<script src="../js/bootlint.js"></script>
<script src="../js/gm.js"></script>
<script src="../js/notice.js"></script>
<script type="text/javascript">
function getNotice(){
	var html = '';
	 $.ajax({	
	 	   type: "POST",
	 	   url: "<%=basePath%>controller/manager/getAllNotice",
	 	  contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	 	   data: "",
	 	   success: function data(data){
	 		  var returns =  eval('(' + data + ')');
	 		 var arr= returns.notices;
	 		for(var i=0,html='';i<arr.length;i++){
		         html+="<tr>"
		        	 html+="<td>"+arr[i].id+"</td>";
		        	 html+="<td>"+arr[i].content+"</td>";
		        	 if (typeof (arr[i].managerId) != 'undefined')
		        	 html+="<td>"+arr[i].managerId+"</td>";
		        	 else
		        		 html+="<td>----</td>";
		        	 html+="<td>";
		        	 html+=(arr[i].type==0?'代理公告':(arr[i].type==1?'消息公告':arr[i].type==3?'给全体代理的公告':'全服图片公告'));
		        	 html+="</td>";
		        	 html+="</tr>";
		     }
	 		  $("#noticeTbl tbody").html(html);

	 	   	}
	 	});
	
	
	
	    

	   }

function ajaxFileUpload() {
    $.ajaxFileUpload
    (
        {
            url: '<%=basePath%>uploadAndDown/uploadImg', //用于文件上传的服务器端请求地址
            secureuri: false, //是否需要安全协议，一般设置为false
            fileElementId: 'filecontent', //文件上传域的ID
            dataType: 'json', //返回值类型 一般设置为json
            success: function (data)  //服务器成功响应处理函数
            {
                if (typeof (data.error) != 'undefined') {
                    if (data.error != '') {
                        alert(data.error);
                    } else {
                        alert(data.msg);
                    }
                }else{
                	var imageUrl = data.imgurl;
                	$("#content").val(imageUrl);
                }
            },
            error: function (data)//服务器响应失败处理函数
            {
                alert('失败');
            }
        }
    )
    return false;
}

function saveNotice(){
    $("#add-notice").fadeOut();
    var type = $("#noticetype").val();
    var content = $("#content").val();
    var inviteCode = $("#inviteCode").val();
    $.ajax({	
 	   type: "POST",
 	   url: "<%=basePath%>controller/manager/sendNotice",
 	  contentType: "application/x-www-form-urlencoded; charset=utf-8", 
 	   data: "notice="+content+"&type="+type+"&inviteCode="+inviteCode,
 	   success: function data(data){
 		  var returns =  eval('(' + data + ')');
 		  if (typeof (returns.error) != 'undefined') {
              if (returns.error != '') {
                  alert(returns.error);
              } else {
                  alert(returns.msg);
              }
          }else{
          	alert(returns.info);
          }

 	   	}
 	});



};
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