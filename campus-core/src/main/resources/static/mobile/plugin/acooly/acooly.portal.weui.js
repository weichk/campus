/***
 * 封装常规的提示操作
 * 本文件是weui实现。
 * PS: javascript没有接口和实现的概念，只有通过人工保证接口一致，不通框架不同实现，然后通过import引用实现切换
 * @type {{alert: acooly_weui.alert, msg: acooly_weui.msg, confirm: acooly_weui.confirm, prompt: acooly_weui.prompt, loading: acooly_weui.loading, loaded: acooly_weui.loaded, disable: acooly_weui.disable, enable: acooly_weui.enable, toptip: acooly_weui.toptip, toast: acooly_weui.toast, _buildOptions: (function(*=, *=, *=, *=, *=, *=): {text: *})}}
 */

acooly_weui = {
    alert: function (msg, title, onOK) {
        $.alert(this._buildOptions(msg, title, onOK));
    },

    msg: function (msg, title) {
        this.alert(msg, title);
    },

    confirm: function (msg, title, onOK, onCancel) {
        $.confirm(this._buildOptions(msg, title, onOK, onCancel));
    },

    prompt: function (msg, title, defaultValue, empty, onOK, onCancel) {
        $.prompt(this._buildOptions(msg, title, defaultValue, empty, onOK, onCancel));
    },

    loading: function (msg) {
        if (!msg)
            msg = '加载中…';
        $.showLoading(msg);
    },

    loaded: function (msg) {
        $.hideLoading();
    },

    disable: function (id) {
        $("#" + id).prop('disabled', true).addClass("weui_btn_disabled");
    },

    enable: function (id) {
        $("#" + id).prop('disabled', false).removeClass("weui_btn_disabled");
    },

    /**
     * @param message
     * @param type success,error,warning
     */
    toptip: function (message, type) {
        if (!type) type = 'error';
        $.toptip(message, type);
    },

    //type: success,cancel,forbidden,text,或则时间
    //$.toast.prototype.defaults.duration
    toast: function (message, type, callBack) {
        if (!type) type = "text";
        if (callBack) {
            $.toast(message, type, callBack);
        } else {
            $.toast(message, type);
        }
    },


    _buildOptions: function (msg, title, defaultValue, empty, onOK, onCancel) {
        var options = {text: msg};
        if (title) {
            options.title = title;
        }
        if (defaultValue) {
            options.input = defaultValue;
        }
        if (empty) {
            options.empty = empty;
        }
        if (onOK) {
            options.onOK = onOK;
        }
        if (onCancel) {
            options.onCancel = onCancel;
        }
        return options;
    }
};

$.extend($.acooly, acooly_weui);






