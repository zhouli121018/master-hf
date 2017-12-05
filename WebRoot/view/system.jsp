<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
              <li><a href="#note">提现流水</a></li>
              <li><a href="#notice">公告管理</a></li>
              <li class="active"><a href="#system">系统参数配置</a></li>
          </ul>
      </div>
          <button type="button" class="btn btn-danger logout">退出登录</button>
      </div>
  </nav>
   <div class="content">
    <div id="system" class="active">
        <h2>系统参数配置</h2>
        <div class="panel panel-default">
            <div class="panel-body">
                <div>
                    <div class="table-responsive">
                        <table id="paramsTbl" class="table table-bordered table-striped table-hover">
                            <thead>
                            <tr>
                                <th>参数名</th>
                                <th>参数值</th>
                                <th>参数类型</th>
                                <th>参数说明</th>
                                <th>时间</th>
                                <th>编辑</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>参数名</td>
                                <td>值</td>
                                <td>type</td>
                                <td>说明</td>
                                <td>2017-07-25 08:26:27</td>
                                <td><input type="button" class="btn btn-success" value="修改"/></td>
                            </tr>
                            <tr>
                                <td>参数名</td>
                                <td>值</td>
                                <td>type</td>
                                <td>说明</td>
                                <td>2017-07-25 08:26:27</td>
                                <td><input type="button" class="btn btn-success" value="修改"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="system-params">
            <form>
                <button type="button" class="close"><span>&times;</span></button>
                <h4>公告信息</h4>
                <div id="params-message"></div>
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
<script src="../js/system.js"></script>
</body>
</html>