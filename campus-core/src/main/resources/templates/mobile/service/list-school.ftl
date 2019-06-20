<!DOCTYPE html>
<html>
<head>
<title>学校列表</title>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
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
        <div class="weui-search-bar acooly-search-bar" id="searchBar">
            <form class="weui-search-bar__form">
                <div class="weui-search-bar__box">
                    <i class="weui-icon-search"></i>
                    <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="请输入学校名称" required="">
                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
                </div>
                <label class="weui-search-bar__label" id="searchText">
                    <i class="weui-icon-search"></i>
                    <span>搜索学校</span>
                </label>
            </form>
            <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
        </div>
        
        <div class="weui-cells acooly-school-list">
            <#list schools as school>
                <a class="weui-cell weui-cell_access" href="/mobile/service/school/register.html?shoolCode=${school.shoolCode}">
                    <div class="weui-cell__hd"><img src="/mobile/image/school_logo.png"></div>
                    <div class="weui-cell__bd">
                        <p>${school.shoolName}</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>
            </#list>

        </div>
        </div>
  
    </div>
    
<script src="/mobile/plugin/jquery-2.1.4.js"></script>
<script src="/mobile/plugin/fastclick.js"></script>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>
<script src="/mobile/plugin/jquery-weui.js"></script>
</body>
</html>
