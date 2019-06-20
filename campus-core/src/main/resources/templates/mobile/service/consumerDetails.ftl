<!DOCTYPE html>
<html lang="zh">
<head>
    <@includePage path="/mobile/common/meta.html"/>
    <title>消费明细</title>
    <@includePage path="/mobile/common/include.html"/>
</head>
<body>
<!--顶部-->
<@includePage path="/mobile/common/header.html"/>
<!--end-->
<div class="acooly-warpper">
    <div class="acooly-account-top">
        <!--卡信息-->
        <div id="card_info_id">

        </div>
        <div class="acooly-account-date">
            <p class="name">查询时间：</p>
            <input type="hidden" id='query_date_id' value=""/>
            <p class="input"><input type="text" id='picker' value="${nowDate}"/></p>
        </div>
    </div>
    <!--消费记录-->
    <div class="acooly-account-list" id="consumerDetails_container">

    </div>
    <div class="weui-panel__ft" id="consumerDetails_controller">
        <a href="javascript:void(0);" onclick="pageQuery()" class="weui-cell weui-cell_access weui-cell_link">
            <div class="weui-cell__bd">查看更多</div>
            <span class="weui-cell__ft"></span>
        </a>
    </div>
</div>
<div id="lijia" class="weui-popup__container popup-bottom">
    <div class="weui-popup__overlay"></div>
    <div class="weui-popup__modal acooly-select-card">
        <div class="toolbar">
            <div class="toolbar-inner">
                <a href="javascript:;" class="picker-button close-popup">关闭</a>
                <h1 class="title">请选择您要查询的卡号</h1>
            </div>
        </div>
        <div class="acooly-card-rows">
            <!--卡列表信息-->
            <div class="weui-cells weui-cells_radio" id="card_list_id">

            </div>
        </div>
    </div>

</div>

<!-- footer start -->
<@includePage path="/mobile/common/footer.html"/>
<!-- footer end -->

<!-- 卡信息列表: -->
<script id="user_card_info_template" type="text/html">
    <%
    var customerCard = entity;
    %>
    <div class="acooly-account-info">
        <div>姓名：<%=customerCard.realName%></div>
        <div>学校：<%=customerCard.shoolName%></div>
    </div>
    <div class="weui-cells acooly-account-card">
        <a class="weui-cell weui-cell_access open-popup" href="javascript:;" data-target="#lijia">
            <div class="weui-cell__bd">
                <input type="hidden" id="school_no_id" value="<%=customerCard.shoolCode%>">
                <input type="hidden" id="card_no_id" value="<%=customerCard.cardNo%>">
                <p>卡号：<%=customerCard.cardNo%></p>
            </div>
            <div class="weui-cell__ft" onclick="getCardList()">切换卡号</div>
        </a>
    </div>
</script>

<!-- 卡列表模板: -->
<script id="user_card_list_template" type="text/html">
    <%
    for(var i=0; i < rows.length; i++){
    var customerCard = rows[i];
    %>
    <label class="weui-cell weui-check__label" for="<%=customerCard.id%>">
        <div class="weui-cell__bd">
            <div class="name">
                <p>姓名：<span><%=customerCard.realName%></span></p>
                <p>学校：<span><%=customerCard.shoolName%></span></p>
            </div>
            <div class="card">卡号：<span><%=customerCard.cardNo%></span></div>
        </div>
        <div class="weui-cell__ft">
            <input type="radio" class="weui-check" name="radio1" id="<%=customerCard.id%>" onclick="selectCard('<%=customerCard.id%>')">
            <span class="weui-icon-checked"></span>
        </div>
    </label>
    <% } %>
</script>

<!-- 交易流水列表模版: -->
<script id="consumerDetails_template" type="text/html">
    <%
    for(var i=0; i < rows.length; i++){
    var entity = rows[i];
    %>
    <div class="row">
        <div class="info">
            <p class="mark">交易地点：<%=entity.tradeTitle%></p>
            <p class="blance">账户余额：￥<%=$.acooly.format.money(entity.balance,false)%></p>
            <p class="time"><%=entity.tradeTime%></p>
        </div>
        <div class="value">
            <p>-<%=$.acooly.format.money(entity.amount,false)%></p>
        </div>
    </div>
    <% } %>
</script>

<script type="text/javascript" src="/mobile/plugin/template/baiduTemplate.js"></script>
<script type="text/javascript" src="/mobile/plugin/fastclick.js"></script>
<script>
    $(function () {
        FastClick.attach(document.body);
    });

    $("#picker").picker({
        title: "请选择您要查询的时间",
        cols: [
            {
                textAlign: 'center',
                // values: ['2011', '2012', '2013', '2014', '2015', '2016', '2017', '2018']
                values: (function() {
                                var years = [];
                                var currentYear = new Date().getFullYear();
                                for(var i = 2003; i <= currentYear; i++) years.push(i);
                                return years;
                })()
            },
            {
                textAlign: 'center',
                values: ['年']
            },
            {
                textAlign: 'center',
                values: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
            },
            {
                textAlign: 'center',
                values: ['月']
            },
        ],
        onClose:function (result) {
            $("#query_date_id").attr("value",result.value[0]+'-'+result.value[2]);
            pageQuery(true);
        },
    });

    /**
     * 分页查询消费记录
     */
    function pageQuery(refresh) {
        if (!refresh) refresh = false;
        var queryDate = $("#query_date_id").val();
        var cardNo = $("#card_no_id").val();
        var schoolNo = $("#school_no_id").val();
        $.acooly.portal.pageAppend({
            refresh: refresh,
            url: '/mobile/service/consumer/consumerDetails.html',
            pageSize: 10,
            template: 'consumerDetails_template',
            renderContainer: 'consumerDetails_container',
            renderController: "consumerDetails_controller",
            jsonData: {cardNo: cardNo, schoolNo: schoolNo,queryDate:queryDate},
        })
    }

    /**
     * 获取卡列表
     */
    function getCardList() {
        var bt=baidu.template;
        $.acooly.portal.ajax('/mobile/service/consumer/getUserCardList.html',null,function(result){
            var html=bt('user_card_list_template',result);
            $('#card_list_id').html(html);
        });
    }

    /**
     * 获取卡信息
     */
    function getCardInfo(id) {
        var bt=baidu.template;
        $.acooly.portal.ajaxAsync('/mobile/service/consumer/getUserCardInfo.html',{id:id},function(result){
            var html=bt('user_card_info_template',result);
            $('#card_info_id').html(html);
        },false);
    }

    /**
     * 获取当前选中的卡
     * @param id
     */
    function selectCard(id) {
        getCardInfo(id);
        pageQuery(true);
    }

    $(function () {
        checkBindCard();
    });

    /**
     * 校验用户是否已经绑卡
     */
    function checkBindCard() {
        $.ajax({
            url: "/mobile/service/consumer/checkBindCard.html",
            async: false,
            method: 'post',
            success: function (result) {
                if (result.success) {
                    //获取当前日期
                    var currentYear = new Date().getFullYear();
                    var currentMonth =  new Date().getMonth()+1;
                    $("#query_date_id").attr("value",currentYear+'-'+currentMonth);
                    getCardInfo(0);
                    pageQuery(true);
                } else {
                    $.alert({
                        title: '绑卡',
                        text: '你还未在生活上绑定校园通，暂不能查询消费记录，请去绑卡',
                        onOK: function () {
                            location.href = "/mobile/service/school/list.html";
                        }
                    });
                }
            }
        });
    }
</script>

</body>
</html>