<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>id:</th>
		<td>${shool.id}</td>
	</tr>					
	<tr>
		<th width="25%">学校编码:</th>
		<td>${shool.shoolCode}</td>
	</tr>					
	<tr>
		<th>学校名称:</th>
		<td>${shool.shoolName}</td>
	</tr>					
	<tr>
		<th>学校联系人姓名:</th>
		<td>${shool.contactsName}</td>
	</tr>					
	<tr>
		<th>学校联系电话:</th>
		<td>${shool.mobileNo}</td>
	</tr>
	<tr>
		<th>学校支付宝PID:</th>
		<td>${shool.aliPid}</td>
	</tr>
	<tr>
		<th>支付宝操作权码:</th>
		<td>${shool.aliAuthCode}</td>
	</tr>					
	<tr>
		<th>学校地址:</th>
		<td>${shool.address}</td>
	</tr>					
	<tr>
		<th>省:</th>
		<td>${shool.province}</td>
	</tr>					
	<tr>
		<th>市:</th>
		<td>${shool.city}</td>
	</tr>					
	<tr>
		<th>县:</th>
		<td>${shool.district}</td>
	</tr>					
	<tr>
		<th>状态:</th>
		<td>${shool.shoolStatus.message}</td>
	</tr>					
	<tr>
		<th>json扩展信息:</th>
		<td>${shool.extJson}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${shool.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${shool.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
