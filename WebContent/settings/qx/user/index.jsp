<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
   $(function(){
	   
	   getUserList($("#pageNo").val(),$("#pageSize").val());
	   getTotal();
	   
	   
	   
	   //为创建按钮绑定事件
	   $("#createBtn").click(function(){
		   $("#create-loginActNo").val("");
		   $("#create-username").val("");
		   $("#create-loginPwd").val("");
		   $("#create-confirmPwd").val("");
		   $("#create-email").val("");
		   $("#create-expireTime").val("");
		   $("#create-allowIps").val("");
		   $("#createUserModal").modal("show");
	   })
   })
   
   //实现改变查询的记录数
   function change(pageNo,pageSize){
	var html = "";
	$("#changeBtn").html("");
	html+='<a  href="javascript:void(0);" onclick="change('+pageNo+','+pageSize+')" >'+pageSize+'</a>   ';
	html+='<span class="caret"></span>';
	$("#changeBtn").html(html);
	$("#pageNo").val(pageNo);
	$("#pageSize").val(pageSize);
	getUserList($("#pageNo").val(),$("#pageSize").val());
}
   
   //实现翻页
   function beforePage(){
	if($("#pageNo").val()==1){
		alert("当前已经是最开始页!");
	}else{
		var pageNo = $("#pageNo").val()-1;
		$("#pageNo").val(pageNo);
		getUserList($("#pageNo").val(),$("#pageSize").val());
	}
}

function afterPage(){
	var pageNo = Math.ceil($("#total").val() /$("#pageSize").val()) ;
	if($("#pageNo").val()==pageNo){
		alert("当前已经是最后一页!");
	}else{
		pageNo =parseInt($("#pageNo").val())+1;
		$("#pageNo").val(pageNo);
		getUserList($("#pageNo").val(),$("#pageSize").val());
	}
}
   
   function changePage(pageNo){
	   $("#pageNo").val(pageNo);
	   getUserList($("#pageNo").val(),$("#pageSize").val());
   }
   
   
   function firstPage(){
	$("#pageNo").val(1);
	getUserList($("#pageNo").val(),$("#pageSize").val());
}

function lastPage(){
	var pageNo = Math.ceil($("#total").val() /$("#pageSize").val()) ;
	$("#pageNo").val(pageNo);
	getUserList($("#pageNo").val(),$("#pageSize").val());
}

//获取总的记录条数
   function getTotal(){
	$.ajax({
		url:"setting/qx/user/getUserTotal.do",
		type:"get",
		dataType:"json",
		success:function(data){
			$("#total").html(data);
			$("#total").val(data);
		}
	})
}
   
   //获取页面开始铺设的数据
   function getUserList(pageNo,pageSize){
	   
		$.ajax({
			url:"setting/qx/user/getUserList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize
			},
			type:"get",
			dataType:"json",
			success:function(data){
				var html = "";
				$.each(data,function(i,n){
					var lockState = "";
					if(n.lockState==1){
						lockState = "启动";
					}else{
						lockState = "锁定";
					}
					html+=' <tr class="active" >';
					html+='	<td><input type="checkbox" name="xz" value='+n.id+'/></td>';
					html+='	<td>'+(i+1)+'</td>';
					html+='	<td><a  href="settings/qx/user/detail.do?id='+n.id+'">'+n.loginAct+'</a></td>';
					html+='	<td>'+n.name+'</td> ';
					html+='	<td>'+n.deptno+'</td>';
					html+='	<td>'+n.email+'</td>';
					html+='	<td>'+n.expireTime+'</td>';
					html+='	<td>'+n.allowIps+'</td>';
					html+='	<td>'+lockState+'</td>';
					html+='	<td>'+n.createBy+'</td>';
					html+='	<td>'+n.createTime+'</td>';
					html+='	<td>'+n.editBy+'</td>';
					html+='	<td>'+n.editTime+'</td>';
					html+='</tr>';
				})
				$("#userBody").html(html);
				
			}
		})
   }
</script>
</head>
<body>
	<input id="pageNo" value="1" type="hidden"/>	
	<input id="pageSize" value="10" type="hidden"/>	
	<!-- 创建用户的模态窗口 -->
	<div class="modal fade" id="createUserModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增用户</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-loginActNo" class="col-sm-2 control-label">登录帐号<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-loginActNo">
							</div>
							<label for="create-username" class="col-sm-2 control-label">用户姓名</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-username">
							</div>
						</div>
						<div class="form-group">
							<label for="create-loginPwd" class="col-sm-2 control-label">登录密码<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="password" class="form-control" id="create-loginPwd">
							</div>
							<label for="create-confirmPwd" class="col-sm-2 control-label">确认密码<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="password" class="form-control" id="create-confirmPwd">
							</div>
						</div>
						<div class="form-group">
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email">
							</div>
							<label for="create-expireTime" class="col-sm-2 control-label">失效时间</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-expireTime">
							</div>
						</div>
						<div class="form-group">
							<label for="create-lockStatus" class="col-sm-2 control-label">锁定状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-lockStatus">
								  <option></option>
								  <option>启动</option>
								  <option>锁定</option>
								</select>
							</div>
							<label for="create-org" class="col-sm-2 control-label">部门<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="create-dept">
                                    <option></option>
                                  <c:forEach items="${deptType}" var="d">
								  	<option>${d.depeName}</option>
								  </c:forEach>
                                </select>
                            </div>
						</div>
						<div class="form-group">
							<label for="create-allowIps" class="col-sm-2 control-label">允许访问的IP</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-allowIps" style="width: 280%" placeholder="多个用逗号隔开">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>用户列表</h3>
			</div>
		</div>
	</div>
	
	<div class="btn-toolbar" role="toolbar" style="position: relative; height: 80px; left: 30px; top: -10px;">
		<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
		  
		  <div class="form-group">
		    <div class="input-group">
		      <div class="input-group-addon">用户姓名</div>
		      <input class="form-control" type="text">
		    </div>
		  </div>
		  &nbsp;&nbsp;&nbsp;&nbsp;
		  <div class="form-group">
		    <div class="input-group">
		      <div class="input-group-addon">部门名称</div>
		      <input class="form-control" type="text">
		    </div>
		  </div>
		  &nbsp;&nbsp;&nbsp;&nbsp;
		  <div class="form-group">
		    <div class="input-group">
		      <div class="input-group-addon">锁定状态</div>
			  <select class="form-control">
			  	  <option></option>
			      <option>锁定</option>
				  <option>启用</option>
			  </select>
		    </div>
		  </div>
		  <br><br>
		  
		  <div class="form-group">
		    <div class="input-group">
		      <div class="input-group-addon">失效时间</div>
			  <input class="form-control" type="text" id="startTime" />
		    </div>
		  </div>
		  
		  ~
		  
		  <div class="form-group">
		    <div class="input-group">
			  <input class="form-control" type="text" id="endTime" />
		    </div>
		  </div>
		  
		  <button type="submit" class="btn btn-default">查询</button>
		  
		</form>
	</div>
	
	
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px; width: 110%; top: 20px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" data-toggle="modal" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
		
	</div>
	
	<div style="position: relative; left: 30px; top: 40px; width: 110%">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" /></td>
					<td>序号</td>
					<td>登录帐号</td>
					<td>用户姓名</td>
					<td>部门名称</td>
					<td>邮箱</td>
					<td>失效时间</td>
					<td>允许访问IP</td>
					<td>锁定状态</td>
					<td>创建者</td>
					<td>创建时间</td>
					<td>修改者</td>
					<td>修改时间</td>
				</tr>
			</thead>
			<tbody id="userBody">
			</tbody>
		</table>
	</div>
	
	<div style="height: 50px; position: relative;top: 30px; left: 30px;">
		<div>
			<button type="button" class="btn btn-default" style="cursor: default;">共<b id="total">50</b>条记录</button>
		</div>
		<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
			<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
			<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" id="changeBtn">
					10
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li><a  href="javascript:void(0);" onclick="change(1,10)" >10</a></li>
						<li><a  href="javascript:void(0);" onclick="change(1,20)" >20</a></li>
						<li><a  href="javascript:void(0);" onclick="change(1,30)">30</a></li>
				</ul>
			</div>
			<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
		</div>
		<div style="position: relative;top: -88px; left: 285px;">
			<nav>
				<ul class="pagination">
					<li> <a href="javascript:void(0);" onclick="firstPage()">首页</a></li>
					<li ><a href="javascript:void(0);" onclick="beforePage()">上一页</a></li>
					<li ><a href="javascript:void(0);" onclick="changePage(1)">1</a></li>
					<li><a href="javascript:void(0);" onclick="changePage(2)">2</a></li>
					<li><a href="javascript:void(0);" onclick="changePage(3)">3</a></li>
					<li><a href="javascript:void(0);" onclick="changePage(4)">4</a></li>
					<li><a href="javascript:void(0);" onclick="changePage(5)">5</a></li>
					<li><a href="javascript:void(0);" onclick="afterPage()">下一页</a></li>
					<li ><a href="javascript:void(0);" onclick="lastPage()">末页</a></li>
				</ul>
			</nav>
		</div>
	</div>
			
</body>
</html>