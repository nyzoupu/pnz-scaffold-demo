<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath() %>/resources/easyui/themes/icon.css">
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resources/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath() %>/resources/style/main.css">
</head>
<body>

	<table id="user_list_dg"></table>

	<div id="user_list_dg_toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="openNewWin()">添加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="openEditWin()">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="resetPassword()">重置密码</a>
	</div>

	<div id="user_save_dialog">
		<div class="inner-wrapper">
			<div class="ftitle">请输入用户信息</div>
			<form id="user_info_form" method="post">
				<div class="fitem">
					<label>登录名:</label> <input type="text" id="usernameInput"
						name="username" />
				</div>
				<div class="fitem">
					<label>用户名称:</label> <input type="text" name="name" />
				</div>

				<div class="fitem">
					<label>角色:</label> <select id="roleSelect" name="roleId">
						<option value="" selected="selected">请选择角色</option>
					</select>
				</div>

				<div class="fitem">
					<label>机构:</label> <select id="groupSelect" name="groupId">
						<option value="" selected="selected">请选择机构</option>
					</select>
				</div>

				<input type="hidden" name="userId" value="" />

				<p class="ftips">提示：默认密码为 123456</p>
			</form>
		</div>
	</div>

	<script>
var UrlConfig = {
	SysUserList: '<%=request.getContextPath() %>/app/sys/user/list',
	SysUserPage: '<%=request.getContextPath() %>/app/sys/user/page',
	SysUserAdd: '<%=request.getContextPath() %>/app/sys/user/add',
	SysUserUpdate: '<%=request.getContextPath() %>/app/sys/user/update',
	SysUserDelete: '<%=request.getContextPath() %>/app/sys/user/delete',
	SysUserResetPassword: '<%=request.getContextPath() %>/app/sys/user/resetPassword',
	
	SysRoleList: '<%=request.getContextPath() %>/app/sys/role/listNoRoot',
	SysGroupList: '<%=request.getContextPath() %>/app/sys/group/ownGroups'
};

$(function(){
	$('#user_list_dg').datagrid({
		url: UrlConfig.SysUserPage,
		toolbar: '#user_list_dg_toolbar',
		singleSelect: true,
		rownumbers: true,
		pagination: true,
		pageSize: 20,
		fit: true,
		fitColumns: true,
		columns: [[
			{title:'用户名', field:'username'},
			{title:'姓名', field:'name'},
			{title:'机构', field:'groupName'},
			{title:'角色', field:'roleName', width:100}
		]]
	});
	
	$('#user_save_dialog').dialog({
		title: '用户信息',
	    width: 500,
	    height: 300,
	    closed: true,
	    cache: false,
	    modal: true,
	    buttons: [{
	    	text: '保存',
	    	handler: function() {
	    		$('#user_info_form').form('submit',{
	    	        url: url,
	    	        onSubmit: function(){
	    	            var success = $(this).form('validate');
	    	            if (! success) {
	    	            	return false;
	    	            }
	    	            
	    	            /*if ($('#roleSelect').val() == '') {
	    	            	alert('请选择角色');
	    	            	return false;
	    	            }

	    	            if (! $('#groupSelect').attr('disabled')) {
		    	            if ($('#groupSelect').val() == '') {
		    	            	alert('请选择机构');
		    	            	return false;
		    	            }
	    	            }*/
	    	            
	    	            return true;
	    	        },
	    	        success: function(result){
	    	        	result = eval('(' + result + ')');
	    	            if (! result.successful) {
	    	            	$.messager.show({ title: '操作结果', msg: result.msg });
	    	            } else {
	    	            	$.messager.show({ title: '操作结果', msg: '操作成功' });
	    	                $('#user_list_dg').datagrid('reload');    // reload the user data
	    	            	$('#user_save_dialog').dialog('close');   // close the dialog
	    	                $('.validatebox-tip').remove();
	    	            	$('#user_info_form').form('clear');
	    	            }
	    	        }
	    	    });
	    	}
	    },{
	    	text: '取消',
	    	handler: function() {
	    		$('#user_save_dialog').dialog('close');
            	$('#user_info_form').form('clear');
	    	}
	    }]
	});
	
	$('#roleSelect').change(function() {
		var $this = $(this);
		if ($this.val() == 'root') {
			$('#groupSelect option:first').attr('selected','selected');
			$('#groupSelect').attr('disabled', true);
		} else {
			$('#groupSelect').attr('disabled', false);
		}
	});
});

findAllRole();
findAllGroup();

var url = '';

function openNewWin() {
	$('#usernameInput').attr("readonly", false);
	$('#roleSelect option:first').attr('selected', 'selected');
	$('#groupSelect option:first').attr('selected', 'selected');
	$('#groupSelect').attr('disabled', false);
	$('#user_save_dialog').dialog('open');
	url = UrlConfig.SysUserAdd;
}

function openEditWin() {
	$('#usernameInput').attr("readonly", "readonly");
	$('#groupSelect').attr('disabled', false);
	$('#user_info_form').form('clear');
	
	var row = $('#user_list_dg').datagrid('getSelected');
	if (! row) {
		alert('请选择用户'); 
		return;
	}
	
	$('#user_info_form').form('load', row);	
	

	if ($('#groupSelect option:selected').length == 0) {
		$('#groupSelect option:first').attr('selected','selected');
	}
	
	if ($('#roleSelect').val() == 'root') {
		$('#groupSelect').attr('disabled', true);
	}
	
	$('#user_save_dialog').dialog('open');
	
	url = UrlConfig.SysUserUpdate;
}

function findAllRole() {
	var $roleSelect = $('#roleSelect');
	if ($roleSelect.find('option').length > 1) {
		$roleSelect.html('<option value="" selected="selected">请选择角色</option>');
	}

	$.post(UrlConfig.SysRoleList + "?_=" + (new Date().valueOf()), null, function(result) {
		for (var i = 0; i < result.length; i++) {
			var role = result[i];
			$roleSelect.append('<option value="' + role.roleId + '">' + role.roleName + '</option>');
		}
		
	}, 'json');
}

function findAllGroup() {
	var $groupSelect = $('#groupSelect');
	if ($groupSelect.find('option').length > 1) {
		$groupSelect.html('<option value="" selected="selected">请选择机构</option>');
	} 
	
	$.post(UrlConfig.SysGroupList + "?_=" + (new Date().valueOf()), null, function(result) {
		for (var i = 0; i < result.length; i++) {
			var group = result[i];
			$groupSelect.append('<option value="' + group.groupId + '">' + group.groupName + '</option>');
		}
		
	}, 'json');
}

function deleteUser() {
	var row = $('#user_list_dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','请确认是否删除该用户?',function(r){
            if (r){
                $.post(UrlConfig.SysUserDelete, {userId:row.userId}, function(result){
                    $.messager.show({ title: '操作结果', msg: '操作成功' });
                	$('#user_list_dg').datagrid('reload');
                },'json');
            }
        });
    } else {
    	alert('请选择用户');
    }
}

function resetPassword() {
	var row = $('#user_list_dg').datagrid('getSelected');
	if (! row) {
		//$.messager.show({ title: '操作提示', msg: '请先选择用户' });
		alert('请先选择用户');
		return;
	}
	
	$.messager.confirm('Confirm','请确认是否要重置用户密码为 123456 ？',function(r){
		if (r) {
			$.post(UrlConfig.SysUserResetPassword, {userId: row.userId}, function(result) {
				if (result.successful) {
					$.messager.show({ title: '操作结果', msg: '操作成功' });
				}
			}, 'json');
		}
	});
	
}

</script>
</body>
</html>