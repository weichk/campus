<!-- build:css -->
<link rel="stylesheet" href="/mobile/css/plugins.css">
<link rel="stylesheet" href="/mobile/css/base.css">

<script type="text/javascript" src="/mobile/plugin/jquery-2.1.4.js"></script>
<script type="text/javascript" src="/mobile/plugin/acooly/acooly.portal.js"></script>
<script type="text/javascript" src="/mobile/plugin/acooly/acooly.portal.weui.js"></script>
<script type="text/javascript" src="/mobile/plugin/acooly/acooly.format.js"></script>
<script type="text/javascript" src="/mobile/plugin/acooly/acooly.verify.js"></script>
<script type="text/javascript" src="/mobile/plugin/acooly/acooly.form.js"></script>

<script src="/mobile/plugin/jquery-weui.js"></script>

<script type="text/javascript">
    /**
     * CSRF-Token 客户端支持脚本。
     */
    var token = $("meta[name='X-CSRF-TOKEN']").attr("content");// 从meta中获取token
    $(document).ajaxSend(function(e, xhr, options) {
        if (!options.crossDomain) {
            xhr.setRequestHeader("X-CSRF-TOKEN", token);// 每次ajax提交请求都会加入此token
        }
    });
    $(function() {
        // CSRF自动为当前页码的所有表单添加hidden(csrfToken)
        $("form").each(function() {
            if ($(this).attr('enctype') == 'multipart/form-data') {
                var action = $(this).attr('action');
                action += (action.indexOf('?') != -1 ? '&' : '?');
                action += "_csrf=${Request['org.springframework.security.web.csrf.CsrfToken'].token}";
                $(this).attr('action', action);
            } else {
                $(this).append("<input type=\"hidden\" name=\"_csrf\" value=\"${Request['org.springframework.security.web.csrf.CsrfToken'].token}\"/>");
            }
        });
    });

    <#--// 全局错误处理-->
    <#--$(function() {-->
        <#--var errorMessage = "${!Session.messages}"-->
        <#--if($.acooly.portal.trimToEmpty(errorMessage) != ""){-->
            <#--$.acooly.toptip(errorMessage,"error");-->
        <#--}-->
    <#--});-->

</script>