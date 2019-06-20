<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${customer.id}</td>
	</tr>					
	<tr>
		<th width="25%">用户号:</th>
		<td>${customer.userNo}</td>
	</tr>					
	<tr>
		<th>用户名:</th>
		<td>${customer.userName}</td>
	</tr>					
	<tr>
		<th>姓名:</th>
		<td>${customer.realName}</td>
	</tr>					
	<tr>
		<th>身份证号:</th>
		<td>${customer.certNo}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${customer.customerStatus}</td>
	</tr>					
	<tr>
		<th>阿里支付授权码:</th>
		<td>${customer.aliAuthCode}</td>
	</tr>					
	<tr>
		<th>手机号:</th>
		<td>${customer.mobileNo}</td>
	</tr>					
	<tr>
		<th>json扩展信息:</th>
		<td>${customer.extJson}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${customer.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${customer.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
