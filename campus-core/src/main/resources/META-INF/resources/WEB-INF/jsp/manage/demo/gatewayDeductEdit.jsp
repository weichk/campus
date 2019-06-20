<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_gatewayDeduct_editform" action="${pageContext.request.contextPath}/manage/demo/gatewayDeduct/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="gatewayDeduct" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">渠道类型：</th>
				<td><select name="channelType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allChannelTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>渠道服务api：</th>
				<td><input type="text" name="channelApi" size="48" placeholder="请输入渠道服务api..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>批次号：</th>
				<td><input type="text" name="batchNo" size="48" placeholder="请输入批次号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>业务订单号：</th>
				<td><input type="text" name="bizOrderNo" size="48" placeholder="请输入业务订单号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>银行订单号：</th>
				<td><input type="text" name="bankOrderNo" size="48" placeholder="请输入银行订单号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>交易主体用户号：</th>
				<td><input type="text" name="userNo" size="48" placeholder="请输入交易主体用户号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>用户ip：</th>
				<td><input type="text" name="userIp" size="48" placeholder="请输入用户ip..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>交易对象用户号：</th>
				<td><input type="text" name="refUserNo" size="48" placeholder="请输入交易对象用户号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>订单状态：</th>
				<td><select name="status" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>通知状态：</th>
				<td><select name="notifyStatus" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allNotifyStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>通知次数：</th>
				<td><input type="text" name="notifySendCount" size="48" placeholder="请输入通知次数..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,10]']"/></td>
			</tr>					
			<tr>
				<th>通知时间：</th>
				<td><input type="text" name="notifyDate" size="20" placeholder="请输入通知时间..." class="easyui-validatebox text" value="<fmt:formatDate value="${gatewayDeduct.notifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>交易申请金额：</th>
				<td><input type="text" name="amount" size="48" placeholder="请输入交易申请金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>成功金额：</th>
				<td><input type="text" name="sucAmount" size="48" placeholder="请输入成功金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>内部备注：</th>
				<td><input type="text" name="memo" size="48" placeholder="请输入内部备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>有效时间：</th>
				<td><input type="text" name="validDate" size="20" placeholder="请输入有效时间..." class="easyui-validatebox text" value="<fmt:formatDate value="${gatewayDeduct.validDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>json扩展信息：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入json扩展信息..." style="width:300px;" name="extJson" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>银行响应报文：</th>
				<td><input type="text" name="bankRespMessage" size="48" placeholder="请输入银行响应报文..." class="easyui-validatebox text" data-options="validType:['length[1,0]']"/></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
