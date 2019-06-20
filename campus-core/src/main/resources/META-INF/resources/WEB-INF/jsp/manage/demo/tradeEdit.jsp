<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_trade_editform" action="${pageContext.request.contextPath}/manage/demo/trade/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="trade" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">交易订单号：</th>
				<td><input type="text" name="bizOrderNo" size="48" placeholder="请输入交易订单号..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>学校编号：</th>
				<td><input type="text" name="shoolNo" size="48" placeholder="请输入学校编号..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>会员订单号：</th>
				<td><input type="text" name="customerUserNo" size="48" placeholder="请输入会员订单号..." class="easyui-validatebox text" data-options="validType:['length[1,64]']"/></td>
			</tr>					
			<tr>
				<th>持卡编号：</th>
				<td><input type="text" name="cardNo" size="48" placeholder="请输入持卡编号..." class="easyui-validatebox text" data-options="validType:['length[1,32]']"/></td>
			</tr>					
			<tr>
				<th>交易类型：</th>
				<td><select name="tradeType" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allTradeTypes}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>交易金额：</th>
				<td><input type="text" name="amount" size="48" placeholder="请输入交易金额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>交易后余额：</th>
				<td><input type="text" name="balance" size="48" placeholder="请输入交易后余额..." style="height: 27px;line-height: 27px;" class="easyui-numberbox text" data-options="validType:['length[1,19]']"/></td>
			</tr>					
			<tr>
				<th>交易状态：</th>
				<td><select name="tradeStatus" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allTradeStatuss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>网关状态：</th>
				<td><select name="gatewayStauts" editable="false" style="height:27px;" panelHeight="auto" class="easyui-combobox" >
					<c:forEach items="${allGatewayStautss}" var="e">
						<option value="${e.key}">${e.value}</option>
					</c:forEach>
				</select></td>
			</tr>					
			<tr>
				<th>外部备注：</th>
				<td><input type="text" name="comment" size="48" placeholder="请输入外部备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>内部备注：</th>
				<td><input type="text" name="memo" size="48" placeholder="请输入内部备注..." class="easyui-validatebox text" data-options="validType:['length[1,128]']"/></td>
			</tr>					
			<tr>
				<th>json扩展信息：</th>
				<td><textarea rows="3" cols="40" placeholder="请输入json扩展信息..." style="width:300px;" name="extJson" class="easyui-validatebox" data-options="validType:['length[1,512]']"></textarea></td>
			</tr>					
			<tr>
				<th>交易完成时间：</th>
				<td><input type="text" name="tradeTime" size="20" placeholder="请输入交易完成时间..." class="easyui-validatebox text" value="<fmt:formatDate value="${trade.tradeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
        </table>
      </jodd:form>
    </form>
</div>
