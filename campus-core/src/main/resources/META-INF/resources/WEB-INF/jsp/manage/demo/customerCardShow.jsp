<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${customerCard.id}</td>
	</tr>					
	<tr>
		<th width="25%">持卡编号:</th>
		<td>${customerCard.cardNo}</td>
	</tr>					
	<tr>
		<th>学校编码:</th>
		<td>${customerCard.shoolCode}</td>
	</tr>					
	<tr>
		<th>学校名称:</th>
		<td>${customerCard.shoolName}</td>
	</tr>					
	<tr>
		<th>会员用户编号:</th>
		<td>${customerCard.customerUserNo}</td>
	</tr>					
	<tr>
		<th>姓名:</th>
		<td>${customerCard.realName}</td>
	</tr>					
	<tr>
		<th>身份证号:</th>
		<td>${customerCard.certNo}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${customerCard.cardStatus.message}</td>
	</tr>					
	<tr>
		<th>手机号:</th>
		<td>${customerCard.mobileNo}</td>
	</tr>					
	<tr>
		<th>json扩展信息:</th>
		<td>${customerCard.extJson}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${customerCard.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${customerCard.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
