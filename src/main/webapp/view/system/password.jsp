<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body>

<div>
<form id="user_password_form" method="post">
    <div class="fitem">
        <label>旧密码:</label>
        <input type="password" id="oldPasswordInput" name="oldPassword" />
    </div>
    <div class="fitem">
        <label>新密码:</label>
        <input type="password" id="newPasswordInput" name="newPassword" />
    </div>
    <div class="fitem">
        <label>确认密码:</label>
        <input type="password" id="newPasswordConfirmInput" name="newPasswordConfirm" />
    </div>
    
    <input type="hidden" name="userId" value="${currentUser.userId }"  />
    
    <div class="fitem">
    	<label>&nbsp;</label>
    	<a href="#" class="easyui-linkbutton" onclick="savePassword();">保存</a>
    </div>
</form>
</div>

<script type="text/javascript">
function savePassword() {
	$('#user_password_form').form('submit', {
		url: '<%=request.getContextPath() %>/app/sys/user/updatePassword',
		onSubmit: function(){
			if ($.trim($('#oldPasswordInput').val()).length == 0) {
				$.messager.show({ title: '操作结果', msg: '请输入旧密码' });
				return false;
			}
			
			if ($.trim($('#newPasswordInput').val()).length == 0) {
				$.messager.show({ title: '操作结果', msg: '请输入新密码' });
				return false;
			}

			if ($.trim($('#newPasswordInput').val()) == $.trim($('#oldPasswordInput').val())) {
				$.messager.show({ title: '操作结果', msg: '新密码和旧密码不能相同' });
				return false;
			}
			
			if ($.trim($('#newPasswordConfirmInput').val()).length == 0) {
				$.messager.show({ title: '操作结果', msg: '请输入确认新密码' });
				return false;
			}
			
			if ($.trim($('#newPasswordInput').val()) != $.trim($('#newPasswordConfirmInput').val())) {
				$.messager.show({ title: '操作结果', msg: '新密码和确认密码输入不同' });
				return false;
			}

			return true;
        },
        success: function(result){
        	result = eval('(' + result + ')');
        	var msg = result.msg;
            if (result.successful) {
				msg = '操作成功';
				clearPasswordForm();
            }
            
            $.messager.show({ title: '操作结果', msg: msg });
        }
	});
}

function clearPasswordForm() {
	$('#oldPasswordInput').val('');
	$('#newPasswordInput').val('');
	$('#newPasswordConfirmInput').val('');
}
</script>
</body>
</html>