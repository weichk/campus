<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${gatewayDeduct.id}</td>
	</tr>					
	<tr>
		<th width="25%">渠道类型:</th>
		<td>${gatewayDeduct.channelType.message}</td>
	</tr>					
	<tr>
		<th>渠道服务api:</th>
		<td>${gatewayDeduct.channelApi}</td>
	</tr>					
	<tr>
		<th>批次号:</th>
		<td>${gatewayDeduct.batchNo}</td>
	</tr>					
	<tr>
		<th>业务订单号:</th>
		<td>${gatewayDeduct.bizOrderNo}</td>
	</tr>					
	<tr>
		<th>银行订单号:</th>
		<td>${gatewayDeduct.bankOrderNo}</td>
	</tr>					
	<tr>
		<th>交易主体用户号:</th>
		<td>${gatewayDeduct.userNo}</td>
	</tr>					
	<tr>
		<th>用户ip:</th>
		<td>${gatewayDeduct.userIp}</td>
	</tr>					
	<tr>
		<th>交易对象用户号:</th>
		<td>${gatewayDeduct.refUserNo}</td>
	</tr>					
	<tr>
		<th>订单状态:</th>
		<td>${gatewayDeduct.status.message}</td>
	</tr>					
	<tr>
		<th>通知状态:</th>
		<td>${gatewayDeduct.notifyStatus.message}</td>
	</tr>					
	<tr>
		<th>通知次数:</th>
		<td>${gatewayDeduct.notifySendCount}</td>
	</tr>					
	<tr>
		<th>通知时间:</th>
		<td><fmt:formatDate value="${gatewayDeduct.notifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>交易申请金额:</th>
		<td>${gatewayDeduct.amount}</td>
	</tr>					
	<tr>
		<th>成功金额:</th>
		<td>${gatewayDeduct.sucAmount}</td>
	</tr>					
	<tr>
		<th>内部备注:</th>
		<td>${gatewayDeduct.memo}</td>
	</tr>					
	<tr>
		<th>有效时间:</th>
		<td><fmt:formatDate value="${gatewayDeduct.validDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>json扩展信息:</th>
		<td>${gatewayDeduct.extJson}</td>
	</tr>					
	<tr>
		<th>银行响应报文:</th>
		<td>${gatewayDeduct.bankRespMessage}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${gatewayDeduct.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${gatewayDeduct.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
