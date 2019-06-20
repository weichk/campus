<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_customerCard_editform" action="${pageContext.request.contextPath}/manage/demo/customerCard/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="customerCard" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">持卡编号：</th>
				<td><input type="text" name="cardNo" size="48" placeholder="请输入持卡编号..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>学校编码：</th>
				<td><input type="text" name="shoolCode" size="48" placeholder="请输入学校编码..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>学校名称：</th>
				<td><input type="text" name="shoolName" size="48" placeholder="请输入学校名称..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>会员用户编号：</th>
				<td><input type="text" name="customerUserNo" size="48" placeholder="请输入会员用户编号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
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
				<td><select name="cardStatus" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allCardStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
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
