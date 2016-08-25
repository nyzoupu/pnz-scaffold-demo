<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻管理</title>
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
			iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a>
	</div>
	
	<div id="user_save_dialog">
		<div class="inner-wrapper">
			<div class="ftitle">请输入信息</div>
			<form id="news_info_form" method="post">
				<div class="fitem">
					<label>id:</label> <input type="text" id="id"
						name="id" />
				</div>
				<div class="fitem">
					<label>新闻标题:</label> <input type="text" name="name" />
				</div>

				<div class="fitem">
					<label>新闻内容:</label> <input type="text" name="cotent" />
					
				</div>

				<div class="fitem">
					<label>日期:</label> <input type="text" name="data" />
				</div>

				<input type="hidden" name="newsId" value="" />

			</form>
		</div>
	</div>
<script>
var UrlConfig = {
	NewsList: '<%=request.getContextPath() %>/app/sys/news/list',
	NewsAdd: '<%=request.getContextPath() %>/app/sys/news/add',
	NewsUpdate: '<%=request.getContextPath() %>/app/sys/news/update',
	NewsDelete: '<%=request.getContextPath() %>/app/sys/news/delete',
};

var url = '';
$(function(){
	$('#user_list_dg').datagrid({
		url: UrlConfig.NewsList,
		toolbar: '#user_list_dg_toolbar',
		singleSelect: true,
		rownumbers: true,
		pagination: true,
		pageSize: 20,
		fit: true,
		fitColumns: true,
		columns: [[
			{title:'ID', field:'id'},
			{title:'tittle', field:'name'},
			{title:'content', field:'cotent'},
			{title:'date', field:'data', width:100}
		]]
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
$('#user_save_dialog').dialog({
	title: '新闻信息',
    width: 500,
    height: 300,
    closed: true,
    cache: false,
    modal: true,
    buttons: [{
    	text: '保存',
    	handler: function() {
    		$('#news_info_form').form('submit',{
    	        url: url,
    	        onSubmit: function(){
    	            var success = $(this).form('validate');
    	            if (!success){
    	            	return false;
    	            }
    	               	            
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
    	            	$('#news_info_form').form('clear');
    	            }
    	        }
    	    });
    	}
    },{
    	text: '取消',
    	handler: function() {
    		$('#user_save_dialog').dialog('close');
        	$('#news_info_form').form('clear');
    	}
    }]
});


function openNewWin() {
	$('#usernameInput').attr("readonly",false);
	$('#roleSelect option:first').attr('selected','selected');
	$('#groupSelect option:first').attr('selected','selected');
	$('#groupSelect').attr('disabled', false);
	$('#user_save_dialog').dialog('open');
	url = UrlConfig.NewsAdd;
}

function openEditWin() {
	$('#usernameInput').attr("readonly", "readonly");
	$('#groupSelect').attr('disabled', false);
	$('#news_info_form').form('clear');
	
	var row = $('#user_list_dg').datagrid('getSelected');
	
	if (!row) {
		alert('请选择用户'); 
		return;
	}
	
	$('#news_info-form').form('load', row);	
	

	if ($('#groupSelect option:selected').length == 0) {
		$('#groupSelect option:first').attr('selected','selected');
	}
	
	if ($('#roleSelect').val() == 'root') {
		$('#groupSelect').attr('disabled', true);
	}
	
	$('#user_save_dialog').dialog('open');
	
	url = UrlConfig.NewsUpdate;
}

function deleteUser() {
	var row = $('#user_list_dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','请确认是否删除该用户?',function(r){
            if (r){
                $.post(UrlConfig.NewsDelete, {newsId:row.id}, function(result){
                    $.messager.show({ title: '操作结果', msg: '操作成功' });
                	$('#user_list_dg').datagrid('reload');
                },'json');
            }
        });
    } else {
    	alert('请选择用户');
    }
}


</script>
</body>
</html>