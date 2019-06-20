<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_customer_editform" action="${pageContext.request.contextPath}/manage/demo/customer/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="customer" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">用户号：</th>
				<td><input type="text" name="userNo" size="48" placeholder="请输入用户号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>用户名：</th>
				<td><input type="text" name="userName" size="48" placeholder="请输入用户名..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>姓名：</th>
				<td><input type="text" name="realName" size="48" placeholder="请输入姓名..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>身份证号：</th>
				<td><input type="text" name="certNo" size="48" placeholder="请输入身份证号..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>状态：</th>
				<td><input type="text" name="customerStatus" size="48" placeholder="请输入状态..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>阿里支付授权码：</th>
				<td><input type="text" name="aliAuthCode" size="48" placeholder="请输入阿里支付授权码..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>手机号：</th>
				<td><input type="text" name="mobileNo" size="48" placeholder="请输入手机号..." class="easyui-validatebox text" data-options="validType:['length[1,11]']"/></td>
			</tr>					
			<tr>
				<th>json扩展信息：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入json扩展信息..." style="width:300px;" name="extJson" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
