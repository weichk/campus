<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_shool_editform" action="${pageContext.request.contextPath}/manage/demo/shool/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="shool" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">学校编码：</th>
				<td><input type="text" name="shoolCode" size="48" placeholder="请输入学校编码..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>学校名称：</th>
				<td><input type="text" name="shoolName" size="48" placeholder="请输入学校名称..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>学校联系人姓名：</th>
				<td><input type="text" name="contactsName" size="48" placeholder="请输入学校联系人姓名..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>学校联系电话：</th>
				<td><input type="text" name="mobileNo" size="48" placeholder="请输入学校联系电话..." class="easyui-validatebox text" data-options="validType:['length[1,16]']"/></td>
			</tr>					
			<tr>
				<th>学校地址：</th>
				<td><input type="text" name="address" size="48" placeholder="请输入学校地址..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>省：</th>
				<td><input type="text" name="province" size="48" placeholder="请输入省..." class="easyui-validatebox text" data-options="validType:['length[1,20]']"/></td>
			</tr>					
			<tr>
				<th>市：</th>
				<td><input type="text" name="city" size="48" placeholder="请输入市..." class="easyui-validatebox text" data-options="validType:['length[1,20]']"/></td>
			</tr>					
			<tr>
				<th>县：</th>
				<td><input type="text" name="district" size="48" placeholder="请输入县..." class="easyui-validatebox text" data-options="validType:['length[1,20]']"/></td>
			</tr>
			<tr>
				<th>学校支付宝PID：</th>
				<td><textarea rows="3" cols="40" placeholder="学校支付宝PID" style="width:300px;" name="aliPid" class="easyui-validatebox" data-options="validType:['length[1,255]'],required:true"></textarea></td>
			</tr>
			<tr>
				<th>状态：</th>
				<td><select name="shoolStatus" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allShoolStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>json扩展信息：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入json扩展信息..." style="width:300px;" name="extJson" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
