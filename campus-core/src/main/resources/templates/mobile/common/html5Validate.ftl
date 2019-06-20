<script src="/mobile/plugin/html5validate/jquery-html5Validate-min.js"></script>
<script>
    //自定义表单验证的错误消息样式
    $.testRemind.css = {
        padding: "6px 8px",
        borderColor: "#dddddd",
        borderRadius: 4,
        boxShadow: "2px 2px 4px rgba(0,0,0,.2)",
        background: "#fff url(/mobile/plugin/html5validate/remind.jpg) no-repeat 5px 7px",
        backgroundColor: "#fff",
        fontSize: 12,
        textIndent: 20
    };

    OBJREG.REALNAME = "^[\\u0391-\\uFFE5]{2,6}$";
    OBJREG["prompt"].realName = "请输入汉字的姓名";

    OBJREG.CERTNO = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";
    OBJREG["prompt"].certNo = "请输入18位身份证号码";

    OBJREG.MOBILE = "^1[2|3|4|5|6|7|8|9]\\d{9}$"
    OBJREG["prompt"].mobile = "请输入有效的手机号码";
</script>