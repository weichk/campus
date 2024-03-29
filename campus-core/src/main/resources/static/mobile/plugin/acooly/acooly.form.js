var acooly_form = {

    registerFourSplitInput: function (inputId) {
        $('#' + inputId).on("keyup", function (e) {
            //只对输入数字时进行处理
            if ((e.which >= 48 && e.which <= 57) ||
                (e.which >= 96 && e.which <= 105)) {
                //获取当前光标的位置
                var caret = this.selectionStart
                //获取当前的value
                var value = this.value
                //从左边沿到坐标之间的空格数
                var sp = (value.slice(0, caret).match(/\s/g) || []).length
                //去掉所有空格
                var nospace = value.replace(/\s/g, '')
                //重新插入空格
                var curVal = this.value = nospace.replace(/(\d{4})/g, "$1 ").trim()
                //从左边沿到原坐标之间的空格数
                var curSp = (curVal.slice(0, caret).match(/\s/g) || []).length
                //修正光标位置
                this.selectionEnd = this.selectionStart = caret + curSp - sp
            }
        });
    }

};

$.extend($.acooly, {
    form : acooly_form
})