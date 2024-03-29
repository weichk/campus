<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customerCard_searchform','manage_customerCard_datagrid');
});

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customerCard_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					持卡编号: <input type="text" class="text" size="15" name="search_LIKE_cardNo"/>
					学校编码: <input type="text" class="text" size="15" name="search_LIKE_shoolCode"/>
					学校名称: <input type="text" class="text" size="15" name="search_LIKE_shoolName"/>
					会员用户编号: <input type="text" class="text" size="15" name="search_LIKE_customerUserNo"/>
					姓名: <input type="text" class="text" size="15" name="search_LIKE_realName"/>
					身份证号: <input type="text" class="text" size="15" name="search_LIKE_certNo"/>
				状态: <select style="width:80px;height:27px;" name="search_EQ_cardStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allCardStatuss}"><option value="${e.key}" ${param.search_EQ_cardStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					手机号: <input type="text" class="text" size="15" name="search_LIKE_mobileNo"/>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_customerCard_searchform','manage_customerCard_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerCard_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/demo/customerCard/listJson.html" toolbar="#manage_customerCard_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">id</th>
			<th field="cardNo">持卡编号</th>
			<th field="shoolCode">学校编码</th>
			<th field="shoolName">学校名称</th>
            <th field="alipayUserId">阿里用户id</th>
            <th field="customerUserNo">会员用户编号</th>
			<th field="realName">姓名</th>
			<th field="certNo">身份证号</th>
			<th field="cardStatus" formatter="mappingFormatter">状态</th>
			<th field="mobileNo">手机号</th>
			<th field="extJson">json扩展信息</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_customerCard_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_customerCard_action" style="display: none;">
      <a onclick="$.acooly.framework.edit({url:'/manage/demo/customerCard/edit.html',id:'{0}',entity:'customerCard',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.show('/manage/demo/customerCard/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <a onclick="$.acooly.framework.remove('/manage/demo/customerCard/deleteJson.html','{0}','manage_customerCard_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_customerCard_toolbar">
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/demo/customerCard/create.html',entity:'customerCard',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/demo/customerCard/deleteJson.html','manage_customerCard_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_customerCard_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>
      <div id="manage_customerCard_exports_menu" style="width:150px;">
        <div onclick="$.acooly.framework.exports('/manage/demo/customerCard/exportXls.html','manage_customerCard_searchform','会员卡信息')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>
        <div onclick="$.acooly.framework.exports('/manage/demo/customerCard/exportCsv.html','manage_customerCard_searchform','会员卡信息')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>
      </div>
      <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/demo/customerCard/importView.html',uploader:'manage_customerCard_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>
    </div>
  </div>

</div>
