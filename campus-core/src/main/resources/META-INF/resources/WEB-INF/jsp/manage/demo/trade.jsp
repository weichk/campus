<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_trade_searchform','manage_trade_datagrid');
});

function reColour(value) {
    if (value == 'SUCCESS') {
        return 'color:#00ff00;';
    } else if (value == 'INIT') {
        return 'color:#436EEE;';
    } else {
        return 'color:red;';
    }
}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_trade_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
					交易订单号: <input type="text" class="text" size="15" name="search_LIKE_bizOrderNo"/>
					学校编号: <input type="text" class="text" size="15" name="search_LIKE_shoolNo"/>
					会员订单号: <input type="text" class="text" size="15" name="search_LIKE_customerUserNo"/>
					持卡编号: <input type="text" class="text" size="15" name="search_LIKE_cardNo"/>
					交易类型: <select style="width:80px;height:27px;" name="search_EQ_tradeType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allTradeTypes}"><option value="${e.key}" ${param.search_EQ_tradeType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					<%--交易金额: <input type="text" class="text" size="15" name="search_EQ_amount"/>--%>
					<%--交易后余额: <input type="text" class="text" size="15" name="search_EQ_balance"/>--%>
					交易状态: <select style="width:80px;height:27px;" name="search_EQ_tradeStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allTradeStatuss}"><option value="${e.key}" ${param.search_EQ_tradeStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					网关状态: <select style="width:80px;height:27px;" name="search_EQ_gatewayStauts" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allGatewayStautss}"><option value="${e.key}" ${param.search_EQ_gatewayStauts == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					交易完成时间: <input size="15" class="text" id="search_GTE_tradeTime" name="search_GTE_tradeTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_tradeTime" name="search_LTE_tradeTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					<%--更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
					<%--至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_trade_searchform','manage_trade_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_trade_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/demo/trade/listJson.html" toolbar="#manage_trade_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">id</th>
			<th field="bizOrderNo">交易订单号</th>
			<th field="merchOrderNo">商户订单号</th>
			<th field="schoolNo">学校编号</th>
			<th field="customerUserNo">会员订单号</th>
			<th field="cardNo">持卡编号</th>
			<th field="tradeType" formatter="mappingFormatter">交易类型</th>
			<th field="amount" sum="true" formatter="centMoneyFormatter">交易金额</th>
			<th field="balance" sum="true" formatter="centMoneyFormatter">交易后余额</th>
			<th field="tradeStatus" data-options="formatter:function(value){ return formatRefrence('manage_trade_datagrid','allTradeStatuss',value);},styler:function(v) {return reColour(v)}">交易状态</th>
			<th field="gatewayStauts" data-options="formatter:function(value){ return formatRefrence('manage_trade_datagrid','allGatewayStautss',value);},styler:function(v) {return reColour(v)}">网关状态</th>
			<th field="agreementNo">签约协议号</th>
			<th field="schoolAnthCode">学校授权编码</th>
			<th field="userIp">用户IP</th>
			<th field="comment">外部备注</th>
			<th field="memo">内部备注</th>
			<th field="extJson">json扩展信息</th>
		    <th field="tradeTime" formatter="dateTimeFormatter">交易完成时间</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_trade_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_trade_action" style="display: none;">
      <%--<a onclick="$.acooly.framework.edit({url:'/manage/demo/trade/edit.html',id:'{0}',entity:'trade',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>--%>
      <a onclick="$.acooly.framework.show('/manage/demo/trade/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <%--<a onclick="$.acooly.framework.remove('/manage/demo/trade/deleteJson.html','{0}','manage_trade_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>--%>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_trade_toolbar">
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/demo/trade/create.html',entity:'trade',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>--%>
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/demo/trade/deleteJson.html','manage_trade_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>--%>
      <%--<a href="#" class="easyui-menubutton" data-options="menu:'#manage_trade_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>--%>
      <%--<div id="manage_trade_exports_menu" style="width:150px;">--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/demo/trade/exportXls.html','manage_trade_searchform','交易记录')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/demo/trade/exportCsv.html','manage_trade_searchform','交易记录')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>--%>
      <%--</div>--%>
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/demo/trade/importView.html',uploader:'manage_trade_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>--%>
    </div>
  </div>

</div>
