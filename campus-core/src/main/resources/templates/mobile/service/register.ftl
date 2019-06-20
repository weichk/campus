<!DOCTYPE html>
<html>
<head>
    <title>注册用户信息</title>

    <!-- 页面头部引用资源及配置 -->
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no, email=no"/>
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- build:css -->
    <link rel="stylesheet" href="/mobile/css/plugins.css">
    <link rel="stylesheet" href="/mobile/css/base.css">
    <!-- endbuild -->

    <#include "/mobile/common/include.ftl">

</head>

<body ontouchstart>
<div class="acooly-warpper">
    <div class="weui-cells acooly-school-reg">
        <a class="weui-cell weui-cell_access" href="javascript:;">
            <div class="weui-cell__hd"><img src="/mobile/image/school_logo.png"></div>
            <div class="weui-cell__bd">
                <p>${school.shoolName}</p>
            </div>
            <div class="weui-cell__ft"></div>
        </a>
    </div>
    <div class="weui-cells weui-cells_form acooly-reg-form">
        <input type="hidden" id="shoolCode" value="${school.shoolCode}">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">一卡通账号</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="number" pattern="[0-9]*" id="cardNo" placeholder="请输入正确的一卡通卡号">
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">用户姓名</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="text" id="realName" placeholder="请输入姓名">
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">身份证号</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="number" id="certNo" pattern="[0-9]*" placeholder="请输入身份证号">
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">手机号码</label></div>
            <div class="weui-cell__bd">
                <input class="weui-input" type="number" id="mobileNo" pattern="[0-9]*" placeholder="请输入手机号">
            </div>
        </div>
    </div>
    <div class="acooly-frame-btn">
        <a href="javascript:;" id="reg-alert" class="weui-btn acooly-btn-primary">确定</a>
    </div>
</div>

<script src="/mobile/plugin/jquery-2.1.4.js"></script>
<script src="/mobile/plugin/fastclick.js"></script>
<script>
    $(function () {
        FastClick.attach(document.body);
    });
</script>
<script src="/mobile/plugin/jquery-weui.js"></script>
<script>
    $(document).on("click", "#reg-alert", function () {
        var shoolCode = $("#shoolCode").val();
        var cardNo = $("#cardNo").val();
        var realName = $("#realName").val();
        var certNo = $("#certNo").val();
        var mobileNo = $("#mobileNo").val();

        $.ajax({
            url: "/mobile/service/school/customerCardBind",
            async: false,
            data: {
                shoolCode: shoolCode,
                cardNo: cardNo,
                realName: realName,
                certNo: certNo,
                mobileNo: mobileNo
            },
            success: function (result) {
                if (result.success) {
                    $.alert("您已成功绑定", "操作成功！");
                } else {
                    $.alert("绑定失败", result.message);
                }
            }
        });
    });
</script>
</body>
</html>
