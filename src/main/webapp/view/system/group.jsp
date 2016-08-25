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
<title>机构管理</title>
</head>
<body>
<table id="group_list_dg" style="width:auto" >
	<thead>
	<tr>
		<th data-options="field:'groupName'">机构名称</th>
		<th data-options="field:'groupNo'">机构编号</th>
		<th data-options="field:'remark',width:100">备注</th>
	</tr>
	<thead>
</table>

<div id="group_list_dg_toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddGroupDialog()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditGroupDialog()">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteGroup()">删除</a>
</div>

<div id="group_save_dialog" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
        closed="true" buttons="#group_save_dialog_buttons" modal="true">
    <div class="ftitle">请输入机构信息</div>
    <form id="group_info_form" method="post">
        <div class="fitem">
            <label>机构名称:</label>
            <input type="text" name="groupName" />
        </div>
        <div class="fitem">
            <label>机构编号:</label>
            <input type="text" name="groupNo" />
        </div>
        <div class="fitem">
            <label>备注:</label>
            <input type="text" name="remark" />
        </div>
        
        <input type="hidden" name="groupId" />
    </form>
</div>
<div id="group_save_dialog_buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveGroup()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#group_save_dialog').dialog('close')">取消</a>
</div>

<script type="text/javascript">

var UrlConfig = {
	SysGroupList: '<%=request.getContextPath() %>/app/sys/group/list',
	SysGroupAdd: '<%=request.getContextPath() %>/app/sys/group/add',
	SysGroupUpdate: '<%=request.getContextPath() %>/app/sys/group/update',
	SysGroupDelete: '<%=request.getContextPath() %>/app/sys/group/delete'
};

$(function(){
	$('#group_list_dg').datagrid({
		url: UrlConfig.SysGroupList,
		toolbar: '#group_list_dg_toolbar',
		singleSelect: true,
		rownumbers: true,
		fit: true,
		fitColumns: true,
	});
});

var url;
function openAddGroupDialog(){
    $('#group_save_dialog').dialog('open').dialog('setTitle','添加机构');
    $('#group_info_form').form('clear');
    url = UrlConfig.SysGroupAdd;
}

function openEditGroupDialog(){
    var row = $('#group_list_dg').datagrid('getSelected');

    if (row){
        $('#group_save_dialog').dialog('open').dialog('setTitle','编辑机构');
        $('#group_info_form').form('load',row);
        url = UrlConfig.SysGroupUpdate;
    } else {
    	alert("请选择需要修改的行");
    }
}

function saveGroup(){
    $('#group_info_form').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	result = eval('(' + result + ')');
            if (result.successful) {
            	$('#group_save_dialog').dialog('close');        // close the dialog
                $('#group_list_dg').datagrid('reload');    // reload the user data
                $.messager.show({ title: '操作结果', msg: '操作成功' });
            } else {
            	$.messager.show({ title: '操作结果', msg: result.msg });
            }
        }
    });
}

function deleteGroup() {
	var row = $('#group_list_dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','请确认是否删除?',function(r){
            if (r){
                $.post(UrlConfig.SysGroupDelete, {groupId:row.groupId}, function(result){
                    $.messager.show({ title: '操作结果', msg: '操作成功' });
                	$('#group_list_dg').datagrid('reload');
                },'json');
            }
        });
    } else {
    	alert('请选择角色');
    }
}

</script>

</body>
</html>