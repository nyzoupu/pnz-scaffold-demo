<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table class="easyui-datagrid" style="width:auto" title="Users List" 
		data-options="rownumbers:true,singleSelect:true,toolbar:toolbar,url:'<%=request.getContextPath() %>/app/user/list2',pagination:true">
	<thead>
	<tr>
		<th data-options="field:'name'">Name</th>
		<th data-options="field:'standard'">Standard</th>
		<th data-options="field:'age'">Age</th>
		<th data-options="field:'sex'">Sex</th>
	</tr>
	<thead>
</table>
<script type="text/javascript">
var toolbar = [{
    text:'Add',
    iconCls:'icon-add',
    handler:function(){alert('add')}
},{
    text:'Cut',
    iconCls:'icon-cut',
    handler:function(){alert('cut')}
},'-',{
    text:'Save',
    iconCls:'icon-save',
    handler:function(){alert('save')}
}];
</body>
</html>