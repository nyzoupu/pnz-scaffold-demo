<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/style/main.css">
<title>角色管理</title>
</head>
<body>
<table id="rolelist_dg" style="width:auto" >
	<thead>
	<tr>
		<th data-options="field:'roleName'">角色名称</th>
		<th data-options="field:'remark',width:300">备注</th>
	</tr>
	<thead>
</table>

<div id="rolelist_dg_toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openPriv()">设置权限</a>
</div>

<div id="role_save_dialog" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
        closed="true" buttons="#role_save_dialog_buttons" modal="true">
    <div class="ftitle">请输入角色信息</div>
    <form id="role_info_form" method="post">
        <div class="fitem">
            <label>角色名称:</label>
            <input type="text" name="roleName" />
        </div>
        <div class="fitem">
            <label>备注:</label>
            <input type="text" name="remark" />
        </div>
        
        <input type="hidden" name="roleId" />
    </form>
</div>
<div id="role_save_dialog_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#role_save_dialog').dialog('close')">取消</a>
</div>

<div id="role_menu_dialog" class="easyui-dialog" title="修改角色权限" style="width:500px;height:500px;padding:10px 20px"
       buttons="#role_menu_dialog_buttons" data-options="modal:true, closed:true, iconCls:'icon-save'">
    
    <div class="ftitle">角色：<span id="menuWinRoleName"></span></div>
    
    <ul id="et" class="easyui-tree"></ul>
    
    
</div>

<div id="role_menu_dialog_buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRoleMenu()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#role_menu_dialog').dialog('close');">取消</a>
</div>

<script type="text/javascript">

var UrlConfig = {
	SysRoleList: '<%=request.getContextPath() %>/app/sys/role/list',
	SysRoleAdd: '<%=request.getContextPath() %>/app/sys/role/add',
	SysRoleUpdate: '<%=request.getContextPath() %>/app/sys/role/update',
	SysRoleDelete: '<%=request.getContextPath() %>/app/sys/role/delete',
	SysRoleMenu: '<%=request.getContextPath() %>/app/sys/role/menu',
	SysRoleSaveMenu: '<%=request.getContextPath() %>/app/sys/role/saveMenu'
};

$(function(){
	$('#rolelist_dg').datagrid({
		url: UrlConfig.SysRoleList,
		toolbar: '#rolelist_dg_toolbar',
		singleSelect: true,
		rownumbers: true,
		fit: true,
		fitColumns: true
	});
});

var url;
function newUser(){
    $('#role_save_dialog').dialog('open').dialog('setTitle','添加角色');
    $('#role_info_form').form('clear');
    url = UrlConfig.SysRoleAdd;
}
function editUser(){
    var row = $('#rolelist_dg').datagrid('getSelected');

    if (row){
        $('#role_save_dialog').dialog('open').dialog('setTitle','编辑角色');
        $('#role_info_form').form('load',row);
        url = UrlConfig.SysRoleUpdate;
    } else {
    	alert("请选择角色");
    }
}
function saveUser(){
    $('#role_info_form').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	result = eval('(' + result + ')');
            if (result.successful) {
            	$('#role_save_dialog').dialog('close');        // close the dialog
                $('#rolelist_dg').datagrid('reload');    // reload the user data
                $.messager.show({ title: '操作结果', msg: '操作成功' });
            } else {
            	$.messager.show({ title: '操作结果', msg: result.msg });
            }
        }
    });
}

function destroyUser(){
    var row = $('#rolelist_dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','请确认是否删除该角色?',function(r){
            if (r){
                $.post(UrlConfig.SysRoleDelete, {roleId:row.roleId}, function(result){
                    $.messager.show({ title: '操作结果', msg: '操作成功' });
                	$('#rolelist_dg').datagrid('reload');
                },'json');
            }
        });
    } else {
    	alert('请选择角色');
    }
}

function openPriv() {
	var row = $('#rolelist_dg').datagrid('getSelected');
	if (! row) {
    	alert('请选择角色'); 
    	return;
	}
	
	$('#menuWinRoleName').text(row.roleName);
	$('#role_menu_dialog').dialog('open');
		 
	$('#et').tree({
		url: UrlConfig.SysRoleMenu + '?roleId=' + row.roleId,
		animate: true,
		checkbox: true,
		lines: true
	});
}

function getChecked(){

    var s = '';
    var nodes = $('#et').tree('getChecked');
    for(var i = 0; i < nodes.length; i++){

        if ($('#et').tree('isLeaf', nodes[i].target)) {
            if (s != '') {
            	s += ',';
            }
            s += nodes[i].id;
        }
        
    }
    
	return s;
}

function saveRoleMenu() {
	var checkedMenus = getChecked();
	
	var row = $('#rolelist_dg').datagrid('getSelected');
	
	var url = UrlConfig.SysRoleSaveMenu;
	var data = {roleId: row.roleId, menuId: checkedMenus};
		
	$.post(
			url,
			data,
			function(result) {
				if (result.successful) {
					$.messager.show({title: '操作结果',msg: '操作成功'});				
					$('#role_menu_dialog').dialog('close');
				}
			}, 
			'json');
}

</script>

</body>
</html>