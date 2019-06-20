<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp"%>
<c:if test="${initParam['ssoEnable']=='true'}">
    <%@ include file="/WEB-INF/jsp/manage/common/ssoInclude.jsp" %>
</c:if>
<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_gatewayDeduct_searchform','manage_gatewayDeduct_datagrid');
});

function reBankColour(value) {
    if (value == 'BS') {
        return 'color:#00ff00;';
    } else if (value == 'BP' || value == 'INIT') {
        return 'color:#436EEE;';
    } else {
        return 'color:red;';
    }
}

function reNotifyColour(value) {
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
    <form id="manage_gatewayDeduct_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          	<div>
				渠道类型: <select style="width:80px;height:27px;" name="search_EQ_channelType" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allChannelTypes}"><option value="${e.key}" ${param.search_EQ_channelType == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					渠道服务api: <input type="text" class="text" size="15" name="search_LIKE_channelApi"/>
					<%--批次号: <input type="text" class="text" size="15" name="search_LIKE_batchNo"/>--%>
					业务订单号: <input type="text" class="text" size="15" name="search_LIKE_bizOrderNo"/>
					银行订单号: <input type="text" class="text" size="15" name="search_LIKE_bankOrderNo"/>
					交易主体用户号: <input type="text" class="text" size="15" name="search_LIKE_userNo"/>
					<%--用户ip: <input type="text" class="text" size="15" name="search_LIKE_userIp"/>--%>
					<%--交易对象用户号: <input type="text" class="text" size="15" name="search_LIKE_refUserNo"/>--%>
					订单状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allStatuss}"><option value="${e.key}" ${param.search_EQ_status == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					通知状态: <select style="width:80px;height:27px;" name="search_EQ_notifyStatus" editable="false" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allNotifyStatuss}"><option value="${e.key}" ${param.search_EQ_notifyStatus == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					<%--通知次数: <input type="text" class="text" size="15" name="search_EQ_notifySendCount"/>--%>
					<%--通知时间: <input size="15" class="text" id="search_GTE_notifyDate" name="search_GTE_notifyDate" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
					<%--至<input size="15" class="text" id="search_LTE_notifyDate" name="search_LTE_notifyDate" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
					<%--交易申请金额: <input type="text" class="text" size="15" name="search_EQ_amount"/>--%>
					<%--成功金额: <input type="text" class="text" size="15" name="search_EQ_sucAmount"/>--%>
					<%--有效时间: <input size="15" class="text" id="search_GTE_validDate" name="search_GTE_validDate" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
					<%--至<input size="15" class="text" id="search_LTE_validDate" name="search_LTE_validDate" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
					<%--银行响应报文: <input type="text" class="text" size="15" name="search_LIKE_bankRespMessage"/>--%>
					创建时间: <input size="15" class="text" id="search_GTE_createTime" name="search_GTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" class="text" id="search_LTE_createTime" name="search_LTE_createTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					<%--更新时间: <input size="15" class="text" id="search_GTE_updateTime" name="search_GTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
					<%--至<input size="15" class="text" id="search_LTE_updateTime" name="search_LTE_updateTime" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />--%>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search('manage_gatewayDeduct_searchform','manage_gatewayDeduct_datagrid');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
          	</div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_gatewayDeduct_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/demo/gatewayDeduct/listJson.html" toolbar="#manage_gatewayDeduct_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true" singleSelect="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id" sum="true">id</th>
			<th field="channelType" formatter="mappingFormatter">渠道类型</th>
			<th field="channelApi">渠道服务api</th>
			<th field="batchNo">批次号</th>
			<th field="bizOrderNo">业务订单号</th>
			<th field="bankOrderNo">银行订单号</th>
			<th field="userNo">交易主体用户号</th>
			<th field="userIp">用户ip</th>
			<th field="refUserNo">交易对象用户号</th>
			<th field="status" data-options="formatter:function(value){ return formatRefrence('manage_gatewayDeduct_datagrid','allStatuss',value);},styler:function(v) {return reBankColour(v)}">订单状态</th>
			<th field="notifyStatus" data-options="formatter:function(value){ return formatRefrence('manage_gatewayDeduct_datagrid','allNotifyStatuss',value);},styler:function(v) {return reNotifyColour(v)}">通知状态</th>
			<th field="notifySendCount">通知次数</th>
		    <th field="notifyDate" formatter="dateTimeFormatter">通知时间</th>
			<th field="amount" sum="true" formatter="centMoneyFormatter">交易申请金额</th>
			<th field="sucAmount" sum="true" formatter="centMoneyFormatter">成功金额</th>
			<th field="memo">内部备注</th>
		    <th field="validDate" formatter="dateTimeFormatter">有效时间</th>
			<th field="extJson">json扩展信息</th>
			<th field="bankRespMessage" formatter="contentFormatter">银行响应报文</th>
		    <th field="createTime" formatter="dateTimeFormatter">创建时间</th>
		    <th field="updateTime" formatter="dateTimeFormatter">更新时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_gatewayDeduct_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>

    <!-- 每行的Action动作模板 -->
    <div id="manage_gatewayDeduct_action" style="display: none;">
      <%--<a onclick="$.acooly.framework.edit({url:'/manage/demo/gatewayDeduct/edit.html',id:'{0}',entity:'gatewayDeduct',width:500,height:400});" href="#" title="编辑"><i class="fa fa-pencil fa-lg fa-fw fa-col"></i></a>--%>
      <a onclick="$.acooly.framework.show('/manage/demo/gatewayDeduct/show.html?id={0}',500,400);" href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
      <%--<a onclick="$.acooly.framework.remove('/manage/demo/gatewayDeduct/deleteJson.html','{0}','manage_gatewayDeduct_datagrid');" href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>--%>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_gatewayDeduct_toolbar">
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.create({url:'/manage/demo/gatewayDeduct/create.html',entity:'gatewayDeduct',width:500,height:400})"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加</a>--%>
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/demo/gatewayDeduct/deleteJson.html','manage_gatewayDeduct_datagrid')"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>--%>
      <%--<a href="#" class="easyui-menubutton" data-options="menu:'#manage_gatewayDeduct_exports_menu'"><i class="fa fa-arrow-circle-o-down fa-lg fa-fw fa-col"></i>批量导出</a>--%>
      <%--<div id="manage_gatewayDeduct_exports_menu" style="width:150px;">--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/demo/gatewayDeduct/exportXls.html','manage_gatewayDeduct_searchform','网关代扣记录')"><i class="fa fa-file-excel-o fa-lg fa-fw fa-col"></i>Excel</div>--%>
        <%--<div onclick="$.acooly.framework.exports('/manage/demo/gatewayDeduct/exportCsv.html','manage_gatewayDeduct_searchform','网关代扣记录')"><i class="fa fa-file-text-o fa-lg fa-fw fa-col"></i>CSV</div>--%>
      <%--</div>--%>
      <%--<a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.imports({url:'/manage/demo/gatewayDeduct/importView.html',uploader:'manage_gatewayDeduct_import_uploader_file'});"><i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i>批量导入</a>--%>
    </div>
  </div>
 <script type="text/javascript">
     $(function() {

         var formatJson = function(json, options) {
             var reg = null,
                 keyStyle='color:#92278f;',
                 valStyle='color:#3ab54a;',
                 formatted = '',
                 pad = 0,
                 PADDING = '    '; // one can also use '\t' or a different number of spaces


             // optional settings
             options = options || {};
             // remove newline where '{' or '[' follows ':'
             options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
             // use a space after a colon
             options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

             // begin formatting...
             if (typeof json !== 'string') {
                 // make sure we start with the JSON as a string
                 json = JSON.stringify(json);
             } else {
                 // is already a string, so parse and re-stringify in order to remove extra whitespace
                 json = JSON.parse(json);
                 json = JSON.stringify(json);
             }

             // add newline before and after curly braces
             reg = /([\{\}])/g;
             json = json.replace(reg, '\r\n$1\r\n');

             // add newline before and after square brackets
             reg = /([\[\]])/g;
             json = json.replace(reg, '\r\n$1\r\n');

             // add newline after comma
             reg = /(\,)/g;
             json = json.replace(reg, '$1\r\n');

             // remove multiple newlines
             reg = /(\r\n\r\n)/g;
             json = json.replace(reg, '\r\n');

             // remove newlines before commas
             reg = /\r\n\,/g;
             json = json.replace(reg, ',');

             // optional formatting...
             if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                 reg = /\:\r\n\{/g;
                 json = json.replace(reg, ':{');
                 reg = /\:\r\n\[/g;
                 json = json.replace(reg, ':[');
             }
             if (options.spaceAfterColon) {
                 reg = /\:/g;
                 json = json.replace(reg, ': ');
             }

             $.each(json.split('\r\n'), function(index, node) {
                 var i = 0,
                     indent = 0,
                     padding = '';

                 if (node.match(/\{$/) || node.match(/\[$/)) {
                     indent = 1;
                 } else if (node.match(/\}/) || node.match(/\]/)) {
                     if (pad !== 0) {
                         pad -= 1;
                     }
                 } else {
                     if(node && node != ''){
                         var pair = node.split(':');
                         node = '<span style="'+keyStyle+'">'+pair[0]+'</span>:<span style="'+valStyle+'">'+ pair[1]+'</span>';
                     }
                     indent = 0;
                 }

                 for (i = 0; i < pad; i++) {
                     padding += PADDING;
                 }

                 formatted += padding + node + '\r\n';
                 pad += indent;
             });

             return formatted;
         };



         $('#manage_gatewayDeduct_datagrid').datagrid({
             view : detailview,
             detailFormatter : function(index, row) {
                 return '<div class="ddv" style="padding:10px 5px"></div>';
             },
             onExpandRow : function(index, row) {

                 var htmlContent = '' +
                     '<h3 style="border-bottom: 1px solid #dddddd;">银行通知:</h3>' +
                     '<div><pre>'+formatJson(row.bankRespMessage)+'</pre></div></div>' +
                     '<div style="float:left;">'

                 var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
                 ddv.panel({
                     border : false,
                     cache : false,
                     content: htmlContent
                 });
                 $('#manage_gatewayDeduct_datagrid').datagrid('fixDetailRowHeight', index);
             }
         });
     });
 </script>
</div>
