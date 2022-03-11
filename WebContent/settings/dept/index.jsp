<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
%>
<%@page import="java.lang.Math"
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
	
	getTotal();
	
	pageList($("#pageNo").val(),$("#pageSize").val());
	
	$("#qx").click(function(){
		$("input[name=xz]").prop("checked",this.checked);
	})
	
	$("#deptBody").on("click",$("input[name = xz]"),function(){
		$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
	})
	
	$("#deleteBtn").click(function(){
		var $xz = $("input[name=xz]:checked"); 
		if($xz.length==0){
			alert("请选择需要删除的记录");
		}else{
			//进行确认删除
			if(confirm("确认删除所有选中的记录")){
				var param = "";
				 for(var i=0;i<$xz.length;i++){
					 param += "id="+$($xz[i]).val();
					 if(i<$xz.length-1){
						 param += "&";
					 }
				 }
			}
			
			$.ajax({
				url:"workbench/dept/delete.do",
				data:param,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.success){
						pageList($("#pageNo").val(),$("#pageSize").val());
					}else{
						alert("删除市场活动失败");
					}
				}
			})
		}
	})
	
	$("#editUpdateBtn").click(function(){
		$.ajax({
			url:"setting/dept/editUpdate.do",
			data:{
				"id":$("#edit-id").val(),
				"deptId":$.trim($("#edit-code").val()),
				"deptName":$.trim($("#edit-name").val()),
				"deptBoss":$.trim($("#edit-manager").val()),
				"bossTelephone":$.trim($("#edit-phone").val()),
				"description":$.trim($("#edit-describe").val())
			},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.success){
					alert("修改成功！")
					$("#editDeptModal").modal("hide");
					pageList($("#pageNo").val(),$("#pageSize").val());
				}else{
					alert("修改失败");
				}
			}
		})
	})
	
	$("#editBtn").click(function(){
		var $xz = $("input[name = xz]:checked");
		if($xz.length == 0){
			alert("请选择需要修改的记录");
		}else if($xz.length > 1){
			alert("只能选择一条记录进行修改");
		}else{
			var id = $xz.val();
		}
		$.ajax({
			url:"setting/dept/getEditDept.do",
			data:{
				"id":id
			},
			type:"get",
			dataType:"json",
			success:function(data){
					$("#edit-id").val(data.id);
					$("#edit-code").val(data.deptId);
					$("#edit-name").val(data.deptName);
					$("#edit-manager").val(data.deptBoss);
					$("#edit-phone").val(data.bossTelephone);
					$("#edit-describe").val(data.description);
					$("#editDeptModal").modal("show");
			}
		})
	})
	
	$("#createBtn").click(function(){
		$("#create-code").val("");
		$("#create-name").val("");
		$("#create-manager").val("");
		$("#create-phone").val("");
		$("#create-describe").val("");
		$("#createDeptModal").modal("show");
	})
	
	$("#saveBtn").click(function(){
		$.ajax({
			url:"setting/dept/createDept.do",
			data:{
				"deptId":$.trim($("#create-code").val()),
				"deptName":$.trim($("#create-name").val()),
				"deptBoss":$.trim($("#create-manager").val()),
				"bossTelephone":$.trim($("#create-phone").val()),
				"description":$.trim($("#create-describe").val())
			},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.success){
					alert("保存成功！");
					$("#createDeptModal").modal("hide");
					pageList(1,$("#pageSize").val());
				}else{
					alert("保存成功失败");
				}
			}
		})
	})
	
	$("#update").click(function(){
		if($.trim($("#newPwd").val())=="" || $.trim($("#oldPwd").val()) == "" || $.trim($("#confirmPwd").val())==""){
			alert("输入不能为空");
		}else{
			if($("#newPwd").val() != $("#confirmPwd").val()){
				alert("两次输入的新密码不一致,请重新输入")
			}else{
				$.ajax({
					url:"setting/user/updatePwd.do",
					data:{"newPwd":$("#newPwd").val(),"oldPwd":$("#oldPwd").val()},
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.success){
							alert("更新密码成功!请重新登录");
							window.location.href='login.jsp';
						}else{
							alert("更新失败!请检查原密码是否正确")
						}
					}
				})
			}
		}
		
	})
})

function beforePage(){
	if($("#pageNo").val()==1){
		alert("当前已经是最开始页!");
	}else{
		var pageNo = $("#pageNo").val()-1;
		$("#pageNo").val(pageNo);
		pageList($("#pageNo").val(),$("#pageSize").val());
	}
}

function afterPage(){
	var pageNo = Math.ceil($("#total").val() /$("#pageSize").val()) ;
	if($("#pageNo").val()==pageNo){
		alert("当前已经是最后一页!");
	}else{
		pageNo =parseInt($("#pageNo").val())+1;
		alert(pageNo);
		$("#pageNo").val(pageNo);
		pageList($("#pageNo").val(),$("#pageSize").val());
	}
}


function firstPage(){
	$("#pageNo").val(1);
	pageList($("#pageNo").val(),$("#pageSize").val());
}

function lastPage(){
	var pageNo = Math.ceil($("#total").val() /$("#pageSize").val()) ;
	$("#pageNo").val(pageNo);
	pageList($("#pageNo").val(),$("#pageSize").val());
}

function change(pageNo,pageSize){
	var html = "";
	$("#changeBtn").html("");
	html+='<a  href="javascript:void(0);" onclick="change('+pageNo+','+pageSize+')" >'+pageSize+'</a>   ';
	html+='<span class="caret"></span>';
	$("#changeBtn").html(html);
	$("#pageNo").val(pageNo);
	$("#pageSize").val(pageSize);
	pageList($("#pageNo").val(),$("#pageSize").val());
}


function openEditPwdModal(){
	$("#newPwd").val("");
	$("#oldPwd").val("");
	$("#confirmPwd").val("");
	$("#editPwdModal").modal("show");
}

function getTotal(){
	$.ajax({
		url:"setting/dept/getTotal.do",
		type:"get",
		dataType:"json",
		success:function(data){
			$("#total").html(data);
			$("#total").val(data);
		}
	})
}


function pageList(pageNo,pageSize){
	$("#qx").prop("checked",false);
	$.ajax({
		url:"setting/dept/pageList.do",
		data:{
			"pageNo":pageNo,
			"pageSize":pageSize,
		},
		type:"post",
		dataType:"json",
		success:function(data){
			var html = "";
			$.each(data,function(i,n){                     
				html += '<tr class="active">';
				html += '<td><input type="checkbox" name = "xz" value='+n.id+'></td>';
				html += '<td>'+n.deptId+'</td>';
				html += '<td>'+n.deptName+'</td>';
				html += '<td>'+n.deptBoss+'</td>';
				html += '<td>'+n.bossTelephone+'</td>';
				html += '<td>'+n.description+'</td>';
				html += '</tr>';
			})
			$("#deptBody").html(html);
		}
	})
}
</script>
</head>
<body>

	<input id="pageNo" value="1" type="hidden"/>	
	<input id="pageSize" value="10" type="hidden"/>	
	
	<!-- 我的资料 -->
	<div class="modal fade" id="myInformation" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">我的资料</h4>
				</div>
				<div class="modal-body">
					<div style="position: relative; left: 40px;">
						姓名：<b>${user.name }</b><br><br>
						登录帐号：<b>${user.loginAct}</b><br><br>
						组织机构：<b>1005，市场部，二级部门</b><br><br>
						邮箱：<b>${user.email }</b><br><br>
						失效时间：<b>${user.expireTime }</b><br><br>
						允许访问IP：<b>${user.allowIps }(限制解除)</b>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改密码的模态窗口 -->
	<div class="modal fade" id="editPwdModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 70%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改密码</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="oldPwd" class="col-sm-2 control-label">原密码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input  type="text" class="form-control" id="oldPwd" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="newPwd" class="col-sm-2 control-label">新密码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="newPwd" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="confirmPwd" class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="confirmPwd" style="width: 200%;">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button id="update"  type="button" class="btn btn-primary">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 退出系统的模态窗口 -->
	<div class="modal fade" id="exitModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 30%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">离开</h4>
				</div>
				<div class="modal-body">
					<p>您确定要退出系统吗？</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="window.location.href='login.jsp';">确定</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 顶部 -->
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
		<div style="position: absolute; top: 15px; right: 15px;">
			<ul>
				<li class="dropdown user-dropdown">
					<a href="javascript:void(0)" style="text-decoration: none; color: white;" class="dropdown-toggle" data-toggle="dropdown">
						<span class="glyphicon glyphicon-user"></span> ${user.name} <span class="caret"></span>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</a>
					<ul class="dropdown-menu">
						<li><a href="workbench/index.jsp"><span class="glyphicon glyphicon-home"></span> 工作台</a></li>
						<li><a href="settings/index.jsp"><span class="glyphicon glyphicon-wrench"></span> 系统设置</a></li>
						<li><a href="javascript:void(0)" data-toggle="modal" data-target="#myInformation"><span class="glyphicon glyphicon-file"></span> 我的资料</a></li>
						<li><a href="javascript:void(0)" data-toggle="modal" onclick="openEditPwdModal()"><span class="glyphicon glyphicon-edit"></span> 修改密码</a></li>
						<li><a href="javascript:void(0);" data-toggle="modal" data-target="#exitModal"><span class="glyphicon glyphicon-off"></span> 退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
	
	<!-- 创建部门的模态窗口 -->
	<div class="modal fade" id="createDeptModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-plus"></span> 新增部门</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-code" class="col-sm-2 control-label">编号<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-code" style="width: 200%;" placeholder="编号不能为空，具有唯一性">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-name" class="col-sm-2 control-label">名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-name" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-manager" class="col-sm-2 control-label">负责人</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-manager" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">电话</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone" style="width: 200%;">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 55%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改部门的模态窗口 -->
	<div class="modal fade" id="editDeptModal" role="dialog">
		<input type="hidden" id="edit-id">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-edit"></span> 编辑部门</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-code" class="col-sm-2 control-label">编号<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-code" style="width: 200%;" placeholder="不能为空，具有唯一性" value="1110">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-name" class="col-sm-2 control-label">名称</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-name" style="width: 200%;" value="财务部">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-manager" class="col-sm-2 control-label">负责人</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-manager" style="width: 200%;" value="张飞">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">电话</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" style="width: 200%;" value="010-84846004">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 55%;">
								<textarea class="form-control" rows="3" id="edit-describe">description info</textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="editUpdateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<div style="width: 95%">
		<div>
			<div style="position: relative; left: 30px; top: -10px;">
				<div class="page-header">
					<h3>部门列表</h3>
				</div>
			</div>
		</div>
		<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px; top:-30px;">
			<div class="btn-group" style="position: relative; top: 18%;">
			  <button type="button" class="btn btn-primary"  id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
			  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
			</div>
		</div>
		<div style="position: relative; left: 30px; top: -10px;">
			<table class="table table-hover">
				<thead>
					<tr style="color: #B3B3B3;">
						<td><input type="checkbox" id="qx" /></td>
						<td>编号</td>
						<td>名称</td>
						<td>负责人</td>
						<td>电话</td>
						<td>描述</td>
					</tr>
				</thead>
				<tbody id="deptBody">
					
				</tbody>
			</table>
		</div>
		
		<div style="height: 50px; position: relative;top: 0px; left:30px;">
			<div>
				<button type="button" class="btn btn-default" style="cursor: default;">共<b id="total"></b>条记录</button>
			</div>
			<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
				<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
				<div class="btn-group">
					<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"  id="changeBtn">
						<a  href="javascript:void(0);" onclick="change(1,10)" >10</a>
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
						<li ><a href="javascript:void(0);" onclick="firstPage()">首页</a></li>
						<li ><a href="javascript:void(0);" onclick="beforePage()">上一页</a></li>
						<li><a href="javascript:void(0);" onclick="afterPage()">下一页</a></li>
						<li><a href="javascript:void(0);" onclick="lastPage()">末页</a></li>
					</ul>
				</nav>
			</div>
		</div>
			
	</div>
	
</body>
</html>