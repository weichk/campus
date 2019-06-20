<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_shool_searchform','manage_shool_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_shool_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					学校编码: <input type="text" class="text" size="15" name="search_LIKE_shoolCode"/>
					学校名称: <input type="text" class="text" size="15" name="search_LIKE_shoolName"/>
					学校联系人姓名: <input type="text" class="text" size="15" name="search_LIKE_contactsName"/>
					学校联系电话: <input type="text" class="text" size="15" name="search_LIKE_mobileNo"/>
					省: <input type="text" class="text" size="15" name="search_LIKE_province"/>
					市: <input type="text" class="text" size="15" name="search_LIKE_city"/>
					县: <input type="text" class="text" size="15" name="search_LIKE_district"/>
				状态: <select style="width:80px;height:27px;" name="search_EQ_shoolStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allShoolStatuss}"><option value="${e.key}" ${param.search_EQ_shoolStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_shool_searchform','manage_shool_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_shool_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/demo/shool/listJson.html" toolbar="#manage_shool_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">id</th>
			<th field="shoolCode">学校编码</th>
			<th field="shoolName">学校名称</th>
			<th field="contactsName">学校联系人姓名</th>
			<th field="mobileNo">学校联系电话</th>
			<th field="address">学校地址</th>
			<th field="province">省</th>
			<th field="city">市</th>
			<th field="district">县</th>
            <th field="aliPid">支付宝PID</th>
            <th field="aliAuthCode">商户授权令牌</th>
			<th field="shoolStatus" formatter="mappingFormatter">状态</th>
			<th field="extJson">json扩展信息</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_shool_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_shool_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/demo/shool/edit.html',id:'{0}',entity:'shool',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/demo/shool/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/demo/shool/deleteJson.html','{0}','manage_shool_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_shool_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/demo/shool/create.html',entity:'shool',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/demo/shool/deleteJson.html','manage_shool_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_shool_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_shool_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/demo/shool/exportXls.html','manage_shool_searchform','学校信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/demo/shool/exportCsv.html','manage_shool_searchform','学校信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/demo/shool/importView.html',uploader:'manage_shool_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
        <a href="${authUrl}" style="color: orangered" target="_blank">学校授权链接</a>
    </div>
  </div>

</div>
