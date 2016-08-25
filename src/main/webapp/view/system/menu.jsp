<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/style/main.css">
<title>菜单管理</title>
</head>
<body>
<table id="mebu_list_dg" style="width:auto"></table>

<div id="mebu_list_dg_toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddWin()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditWin()">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteMenu()">删除</a>
</div>

<div id="menu_save_dialog" class="easyui-dialog" title="菜单管理" style="width:500px;height:280px;padding:10px;"
	buttons="#menu_save_dialog_buttons" data-options=" 
		modal:true,
		closed:true,
		iconCls:'icon-save'">

	<div class="ftitle">请输入菜单信息</div>
    <form id="menu_info_form" method="post">
        <div class="fitem">
            <label>菜单名称:</label>
            <input type="text" id="menuName" name="menuName" />
        </div>
        <div class="fitem">
            <label>菜单URL:</label>
            <input type="text" id="menuUrl" name="menuUrl" />
        </div>
        <div class="fitem" id="p_row">
            <label>父菜单:</label>
            <input type="text" id="parentMenuName" readonly="readonly" />
        </div>
                
        <input type="hidden" id="parentMenuId" name="parentMenuId" />        
        <input type="hidden" id="menuId" name="menuId" />
        
        <p class="ftips">提示：如果添加子菜单，请先在列表中选择父菜单；否则将添加为顶级菜单。</p>
    </form>
</div>

<div id="menu_save_dialog_buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#menu_save_dialog').dialog('close');">取消</a>
</div>

<script type="text/javascript">
var UrlConfig = {
	SysMenuAdd: '<%=request.getContextPath() %>/app/sys/menu/add',
	SysMenuUpdate: '<%=request.getContextPath() %>/app/sys/menu/update',
	SysMenuDelete: '<%=request.getContextPath() %>/app/sys/menu/delete',
	SysMenuList: '<%=request.getContextPath() %>/app/sys/menu/list'
};

$(function() {		
	$('#mebu_list_dg').treegrid({
		rownumbers:true,
		singleSelect:true,
		toolbar:'#mebu_list_dg_toolbar',
		url: UrlConfig.SysMenuList,
		idField:'menuId',
		treeField:'menuName',
		fit: true,
		fitColumns: true,
		columns: [[
			{title:'菜单名字', field:'menuName'},
			{title:'菜单URL', field:'menuUrl', width:300}
		]]
	});
});

var url = '';

function openAddWin() {
	$('#menu_info_form').form('clear');
	$('#p_row').show();
	var row = $('#mebu_list_dg').treegrid('getSelected');
	
	if (row) {
		$('#parentMenuName').val(row.menuName);
		$('#parentMenuId').val(row.menuId);
	}
	
	$('#menu_save_dialog').dialog('open');
	url = UrlConfig.SysMenuAdd;
}

function openEditWin() {
	$('#menu_info_form').form('clear');
	$('#p_row').hide();
	var row = $('#mebu_list_dg').treegrid('getSelected');
	
	if (row) {
		$('#menu_info_form').form('load', row);
		$('#menu_save_dialog').dialog('open');
		url = UrlConfig.SysMenuUpdate;
	} else {
		alert('请选择要修改的菜单');
	}
	
}

function save() {
	$('#menu_info_form').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	result = eval('(' + result + ')');
            if (result.successful) {
            	$('#menu_save_dialog').dialog('close');      // close the dialog
                $('#mebu_list_dg').treegrid('reload');    // reload the user data
            	$.messager.show({ title: '操作结果', msg: '操作成功' });
            } else {
            	$.messager.show({ title: '操作结果', msg: result.msg });
            }
        }
    });
}

function deleteMenu() {
	var row = $('#mebu_list_dg').treegrid('getSelected');
	if (! row) {
		alert('请选择要删除的行，只能删除叶子节点');
		return;
	} 
	
	if (row.children != null && row.children.length > 0) {
		alert("只能删除叶子节点");
		return;
	}
	
	$.messager.confirm('Confirm','请确认是否删除该菜单?',function(r){
		if (r) {
			$.post(UrlConfig.SysMenuDelete, {menuId:row.menuId}, function(){
				$.messager.show({ title: '操作结果', msg: '操作成功' });
	        	$('#mebu_list_dg').treegrid('reload');
			}, 'json');
		}
	});
}

</script>
</body>
</html>