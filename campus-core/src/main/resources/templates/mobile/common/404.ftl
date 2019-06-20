<!DOCTYPE html>
<html lang="zh">
<head>
    <@includePage path="/mobile/common/meta.html"/>
    <title>没有找到页面</title>
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
    <div class="acooly-ac">对不起，没有找到任何有用的东西</div>
    <div style="padding: 15px;">
        <button onclick="history.back()" class="weui-btn weui-btn_main" style="width:150px;" type="button">返回</button>
    </div>
</div>

<!-- footer start -->
<@includePage path="/mobile/common/footer.html"/>
<!-- footer end -->

</body>
</html>