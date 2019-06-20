<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${trade.id}</td>
	</tr>					
	<tr>
		<th width="25%">交易订单号:</th>
		<td>${trade.bizOrderNo}</td>
	</tr>					
	<tr>
		<th>学校编号:</th>
		<td>${trade.shoolNo}</td>
	</tr>					
	<tr>
		<th>会员订单号:</th>
		<td>${trade.customerUserNo}</td>
	</tr>					
	<tr>
		<th>持卡编号:</th>
		<td>${trade.cardNo}</td>
	</tr>					
	<tr>
		<th>交易类型:</th>
		<td>${trade.tradeType.message}</td>
	</tr>					
	<tr>
		<th>交易金额:</th>
		<td>${trade.amount}</td>
	</tr>					
	<tr>
		<th>交易后余额:</th>
		<td>${trade.balance}</td>
	</tr>					
	<tr>
		<th>交易状态:</th>
		<td>${trade.tradeStatus.message}</td>
	</tr>					
	<tr>
		<th>网关状态:</th>
		<td>${trade.gatewayStauts.message}</td>
	</tr>					
	<tr>
		<th>外部备注:</th>
		<td>${trade.comment}</td>
	</tr>					
	<tr>
		<th>内部备注:</th>
		<td>${trade.memo}</td>
	</tr>					
	<tr>
		<th>json扩展信息:</th>
		<td>${trade.extJson}</td>
	</tr>					
	<tr>
		<th>交易完成时间:</th>
		<td><fmt:formatDate value="${trade.tradeTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${trade.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${trade.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
