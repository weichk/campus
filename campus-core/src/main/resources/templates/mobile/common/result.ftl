<!DOCTYPE html>
<html lang="zh">
<head>
    <@includePage path="/mobile/common/meta.html"/>
    <title>异常结果</title>
    <@includePage path="/mobile/common/include.html"/>
</head>
<body>
<!--顶部-->
<@includePage path="/mobile/common/header.html"/>
<!--end-->

<div class="weui_main">
    <div class="weui-flex">
        <div class="weui-flex__item acooly-ac" style="margin-top: 72px;">
            <img src="/mobile/image/auth_success.png" style="width:150px;">
        </div>
    </div>
    <div class="acooly-ac acooly-fs-14 p10"><#if messages??>${messages[0]}</#if></div>
    <div style="padding: 15px;">
        <button id="resultButId" class="weui-btn weui-btn_main"
                style="width:150px;"
                type="button">返回
        </button>
    </div>
</div>

<!-- footer start -->
<@includePage path="/mobile/common/footer.html"/>
<!-- footer end -->
<script src="/mobile/plugin/acooly/acooly.app.js"></script>
<script>
    $(function () {
        $.acooly.app.webCallNative({
            element: $('#resultButId'),                 // 绑定的元素（如按钮）
            event: "click",                                     // 绑定的事件
            service: "pop",                                     // 服务名称
        });
    });
</script>
</body>
</html>