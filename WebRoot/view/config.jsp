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
    <title>系统配置</title>
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
                    <li class="active"><a href="#config">系统配置</a></li>
                    <li><a href="#handle">异常处理</a></li>
                    <%} %>
                    
                </ul>
            </div>
            <button type="button" class="btn btn-danger logout" onclick="logout()">退出登录</button>
        </div>
    </nav>
    <div class="content">
        <div id="config" class="active">
            <h2>系统配置</h2>
            <button  type="button" class="btn  btn-success addConfig">
                <span class="glyphicon glyphicon-plus"></span>
                新 增
            </button>
            <div class="panel panel-default">
                <div class="panel-body">
                    <div>
                        <div class="table-responsive">
                            <table id="configTbl" class="table table-bordered table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>key</th>
                                    <th>value</th>
                                    <th>properties</th>
                                    <th>label</th>
                                    <th>createTime</th>
                                    <th>编辑</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>freeTime</td>
                                        <td>2017-8-18 2017-8-20</td>
                                        <td>properties</td>
                                        <td>标签</td>
                                        <td>创建时间</td>
                                        <td>
                                            <a href="#" class="btn btn-sm btn-warning modify" >修 改</a>
                                            <a href="#" class="btn btn-sm btn-danger delete" >删 除</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>1</td>
                                        <td>freeTime</td>
                                        <td>2017-8-18 2017-8-20</td>
                                        <td>properties</td>
                                        <td>标签</td>
                                        <td>创建时间</td>
                                        <td>
                                            <a href="#" class="btn btn-sm btn-warning modify" >修 改</a>
                                            <a href="#" class="btn btn-sm btn-danger delete" >删 除</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>1</td>
                                        <td>freeTime</td>
                                        <td>2017-8-18 2017-8-20</td>
                                        <td>properties</td>
                                        <td>标签</td>
                                        <td>创建时间</td>
                                        <td>
                                            <a href="#" class="btn btn-sm btn-warning modify" >修 改</a>
                                            <a href="#" class="btn btn-sm btn-danger delete" >删 除</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>




            <div id="add-config">
                <form>
                    <button type="button" class="close"><span>&times;</span></button>
                    <h4>新增配置信息</h4>
                    <div class="add">
                        <div class="form-group">
                            <label>key：</label>
                            <input type="text" name="key" class="form-control">
                            <p>请输入key信息</p>
                        </div>
                        <div class="form-group">
                            <label>value：</label>
                            <input type="text" name="value"  class="form-control">
                            <p>请输入value信息</p>
                        </div>
                        <div class="form-group">
                            <label>properties：</label>
                            <input type="text" name="properties"  class="form-control">
                            <p>请输入properties信息</p>
                        </div>
                        <div class="form-group">
                            <label>label：</label>
                            <input type="text" name="label"  class="form-control">
                            <p>请输入label信息</p>
                        </div>
                    </div>
                    <input type="button" class="btn  btn-success sure" value="确定增加"/>
                    <input type="button" class="btn  btn-danger cancel" value="取 消"/>
                </form>
            </div>
            <div id="configDetail">
                <form>
                    <button type="button" class="close"><span>&times;</span></button>
                    <h4> <b></b> 配置信息</h4>
                    <div id="config-message">
                        <div class="form-group">
                            <label>key：</label>
                            <input type="text" name="key" class="form-control">
                            <p>请输入key信息</p>
                        </div>
                        <div class="form-group">
                            <label>value：</label>
                            <input type="text" name="value"  class="form-control">
                            <p>请输入value信息</p>
                        </div>
                        <div class="form-group">
                            <label>properties：</label>
                            <input type="text" name="properties"  class="form-control">
                            <p>请输入properties信息</p>
                        </div>
                        <div class="form-group">
                            <label>label：</label>
                            <input type="text" name="label"  class="form-control">
                            <p>请输入label信息</p>
                        </div>
                    </div>
                    <input type="button"  class="btn  btn-success sure" value="修 改"/>
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
<script src="../js/gm.js"></script>
<script src="../js/config.js"></script>
<script>
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

$(function(){


	sessionStorage['basePath']= "<%=basePath%>";

	$.ajax({
	 	   url: "<%=basePath%>controller/manager/getAllConfig",
	 	   data: "",
	 	   success: function data(data){
	 		  var returns =  eval('(' + data + ')');
	 		  var arr= returns.gmconfigs;

	
	 		  
	 		  console.dir(arr);
	 		  for(var i=0,html='';i<arr.length;i++){
	 		         var obj=arr[i];
	 		        var now=new Date(obj.createtime).toLocaleString();
	 		         html+="<tr><td>"+obj.id+"</td><td>"+obj.key+"</td><td>"+obj.value+"</td><td>"+obj.properties+"</td>"+
	 		         "<td>"+obj.label+"</td><td>"+now+"</td><td><a href='"+obj.id+"' class="+"'btn btn-sm btn-warning modify'"+" >修 改</a> <a href='"+obj.id +"' class="+"'btn btn-sm btn-danger delete'" +">删 除</a></td></tr>";
	 		  };
	 		  //console.log(html);

	 		  $("#configTbl tbody").html(html);
      }})
})

</script>
</body>
</html>