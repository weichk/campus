/**
 *
 * acooly portal js for gfintech
 *
 * @author zhangpu
 * @date 2017-07-09
 * @type {{trimToEmpty: acooly_portal.trimToEmpty, ajax: acooly_portal.ajax, ajaxRender: acooly_portal.ajaxRender, renderData: acooly_portal.renderData}}
 */
var acooly_portal = {
    trimToEmpty: function (text) {
        if (text == null || text == 'null') return '';
        return text;
    },

    ajax: function (url, jsonData, onSuccess, onError) {
        $.ajax({
            url: url,
            method: 'post',
            data: jsonData,
            beforeSend: function () {
                $.acooly.loading();
            },
            complete: function () {
                $.acooly.loaded();
            },
            success: function (result) {
                if (result.success) {
                    if (onSuccess) {
                        onSuccess.call(this, result);
                    }
                } else {
                    $.acooly.toast(result.message);
                }
            },
            error: function (e) {
                if (onError) {
                    onError.call(this, e);
                } else {
                    $.acooly.msg("通讯异常:" + e, 2);
                }
            }
        })
    },

    ajaxAsync: function (url, jsonData, onSuccess,async, onError) {
        $.ajax({
            async: async,
            url: url,
            method: 'post',
            data: jsonData,
            beforeSend: function () {
                $.acooly.loading();
            },
            complete: function () {
                $.acooly.loaded();
            },
            success: function (result) {
                if (result.success) {
                    if (onSuccess) {
                        onSuccess.call(this, result);
                    }
                } else {
                    $.acooly.toast(result.message);
                }
            },
            error: function (e) {
                if (onError) {
                    onError.call(this, e);
                } else {
                    $.acooly.msg("通讯异常:" + e, 2);
                }
            }
        })
    },

    /**
     * ajax方式提交表单
     *
     * 可根据参数说明传值控制逻辑
     * 也可以直接传入标准jquery-ajax的参数覆盖封装的逻辑
     *
     * @param opts 参考下面的默认值说明
     */
    ajaxSubmit: function (opts) {

        var defOpts = {
            url: null,                      // 地址
            jsonData: null,                 // json数据，与formId任传其一
            formId: null,                   // form表单ID，与data任传其一
            buttonId: null,                 // 提交按钮id
            buttonDisabledClass: null,       // 提价按钮disableClass
            onSuccess: null,
            onFailure: null,
            onError: null,
            onBeforSend: null,
            onComplete: null,
            showloading: true
        }
        if (!opts.formId && !opts.jsonData) {
            $.acooly.toptip("formId和jsonData必输其一", "error");
            return;
        }
        opts.data = opts.jsonData;
        if (!opts.data) {
            opts.data = $('#' + opts.formId).serializeJson();
        }
        var options = $.extend(defOpts, opts);
        var ajaxOptions = {
            method: "post",
            cache: false,
            beforeSend: function () {
                if (options.showloading) {
                    if (options.buttonId) {
                        $.acooly.enable(options.buttonId);
                        $("#" + options.buttonId).html("正在加载...");
                    } else {
                        $.acooly.loading();
                    }
                }
                if (options.onBeforSend != null) {
                    options.onBeforSend.call(this);
                }

            },
            complete: function () {
                if (options.showloading) {
                    if (options.buttonId) {
                        $.acooly.enable(options.buttonId);
                        $("#" + options.buttonId).html("确 定");
                    } else {
                        $.acooly.loaded();
                    }
                }
                if (options.onComplete != null) {
                    options.onComplete.call(this);
                }
            },
            success: function (result) {
                if (result.success) {
                    if (options.onSuccess) {
                        options.onSuccess.call(this, result);
                    }
                } else {
                    if (options.onFailure) {
                        options.onFailure.call(this, result);
                    } else {
                        $.acooly.toptip(result.message, "error");
                    }
                }
            },
            error: function (e) {
                if (options.onError) {
                    options.onError.call(this, e);
                } else {
                    $.acooly.toptip("通讯异常:" + e, "error");
                }
            }
        }

        $.ajax($.extend(ajaxOptions, options));

    },

    ajaxRender: function (url, jsonData, container, template, beforeRender, afterReader) {
        var This = this;
        $.ajax({
            url: url,
            method: 'post',
            data: jsonData,
            beforeSend: function () {
                $.acooly.loading();
            },
            complete: function () {
                $.acooly.loaded();
            },
            success: function (result) {
                if (result.success) {
                    if (beforeRender) {
                        beforeRender.call(this, result);
                    }
                    This.renderData(container, template, result);
                    if (afterReader) {
                        afterReader.call(this, result);
                    }
                } else {
                    $.acooly.toast(result.message);
                }
            },
            error: function (e) {
                $.acooly.toast('数据加载失败:' + e);
            }
        })
    },

    renderData: function (container, template, result) {
        baidu.template.LEFT_DELIMITER = '<%';
        baidu.template.RIGHT_DELIMITER = '%>';
        var html = baidu.template(template, result);
        $('#' + container).html(html);
        return result.data.totalPage;
    },

    renderAppend: function (container, template, result) {
        baidu.template.LEFT_DELIMITER = '<%';
        baidu.template.RIGHT_DELIMITER = '%>';
        var html = baidu.template(template, result);
        $('#' + container).append(html);
        return result.data.totalPage;
    },


    /**
     * 分页列表（内容递增方式）
     *
     * 列表末尾提供加载更多的按钮，加载后的下一页数据接续在上一页数据后。
     * @param opts参数说明：
     * url 请求数据的URL
     * jsonData 请求参数
     * template 模板ID
     * renderContainer 分页列表数据容器
     * renderController 分页控制容器
     * beforeRender 数据load后，渲染完成前拦截函数
     * afterRender  渲染完成后拦截函数
     */
    pageAppend: function (opts) {

        var def = {
            refresh: false,      // 如果true则刷新，显示第一页。
            entity: null,
            pageSize: 10,
            jsonData: {},
            beforeRender: null,
            afterRender: null
        }
        var options = $.extend(def, opts);
        var pageNo = $('#' + options.renderController).attr("pageNo");
        if (options.refresh || !pageNo) {
            pageNo = 1;
        }
        var requestData = $.extend({rows: options.pageSize, page: pageNo}, options.jsonData);
        options.jsonData = requestData;

        if (options.entity) {
            options.template = options.entity + "_list_template";
            options.renderController = options.entity + "_list_controller";
            options.renderContainer = options.entity + "_list_container";
        }

        baidu.template.ESCAPE = false;

        var ajaxOptions = $.extend({
            url: options.url,
            jsonData: requestData,
            onSuccess: function (result) {

                if (options.beforeRender) {
                    options.beforeRender.call(this, result);
                }
                if (options.refresh) {
                    $.acooly.portal.renderData(options.renderContainer, options.template, result);
                } else {
                    $.acooly.portal.renderAppend(options.renderContainer, options.template, result);
                }


                if (result.data.hasNext || result.hasNext) {
                    $('#' + options.renderController).show();
                } else {
                    $('#' + options.renderController).hide();
                }
                pageNo = parseInt(pageNo) + 1;
                $('#' + options.renderController).attr("pageNo", pageNo);

                if (options.afterRender) {
                    options.afterRender.call(this, result);
                }
            }
        }, options);
        $.acooly.portal.ajaxSubmit(ajaxOptions);
    },


    /**
     * 刷新图片验证码
     * @param captchaImage [必填] 验证码图片ID
     * @param captchaInput [必填] 验证码输入框ID
     * @param captchaImageUrl [可选] 图片验证码URL地址，默认为acooly框架的/captcha.jpg
     */
    catpchaRefresh: function (captchaImage, captchaInput, captchaImageUrl) {
        if (!captchaImageUrl) {
            captchaImageUrl = "/jcaptcha.jpg";
        }
        $('#' + captchaImage).attr("src", captchaImageUrl + "?" + new Date());
        $('#' + captchaInput).val('');
    },


    /**
     * 发送短信验证码
     * 参数说明请参考函数内的默认参数
     * @param opts
     */
    catpchaSend: function (opts) {
        var def = {
            url: null,                      // [必选] 请求服务端地址
            sendButtonId: null,             // [必选] 发送按钮ID
            sendButtonDisableClass: null,   // [可选] 发送按钮disable的样式class
            jsonData: {},                   // [二选] 与formId必选一，需要手机号码及附属信息
            formId: null,                   // [二选] 与jsonData必选一，从序列化表单获取信息
            timeout: 120,                   // [可选] 倒计时秒数，默认120秒
            onSuccess: null,                // [可选] 成功回调
            onError: null,                  // [可选] 网络失败回调
        };
        var options = $.extend(def, opts);
        var requestData = options.jsonData;
        if (options.formId) {
            requestData = $.extend($('#' + options.formId).serializeJson(), options.jsonData);
        }

        $.ajax({
            url: options.url + "?" + new Date(),
            data: requestData,
            cache: false,
            method: 'post',
            beforeSend: function () {
                $.acooly.loading();
            },
            complete: function () {
                $.acooly.loaded();
            },
            success: function (result) {
                if (result.success) {
                    $("#" + options.sendButtonId).attr('disabled', true);
                    $.acooly.countdown.start(options.sendButtonId, options.timeout, options.sendButtonDisableClass)
                    $.acooly.toptip(result.message, 'success');
                } else {
                    $.acooly.toptip(result.message, 'error');
                }
                if (options.onSuccess) {
                    options.onSuccess.call(this, result);
                }
            },
            error: function (e) {
                if (options.onError) {
                    options.onError.call(this, e);
                } else {
                    console.info(e);
                    $.acooly.toptip("通讯异常:" + e, 'error');
                }
            }
        });


    }


};


/**
 * 密码相关
 * @type {{}}
 */
var acooly_password = {

    options: {
        higRegex: "^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
        midRegex: "^(?=.{8,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
        lowRegex: "(?=.{8,}).*"
    },

    /**
     * 验证码密码等级
     * @param password 密码
     * @returns {number} 等级（0：不合格,1:低，2:中，3:高）
     */
    verify: function (password) {
        if (new RegExp(this.options.higRegex, "g").test(password)) {
            return 3;
        } else if (new RegExp(this.options.midRegex, "g").test(password)) {
            return 2;
        } else if (new RegExp(this.options.lowRegex, "g").test(password)) {
            return 1;
        } else {
            return 0;
        }
    }
};


var acooly_countdown = {
    // 默认倒计时60s
    count: 60,

    disabledClass: '',

    /**
     * 启动
     * @param count
     * @param buttonId
     */
    start: function (buttonId, count, disabledClass) {
        if (count) {
            this.count = count;
        }
        if (disabledClass) {
            this.disabledClass = disabledClass;
        }
        this.doCount(buttonId);
    },

    /**
     * 停止
     */
    stop: function () {
        this.count = 0;
    },

    doCount: function (buttonId) {
        var i = --this.count;
        if (i < 0) {
            $("#" + buttonId).prop('disabled', false).removeClass(this.disabledClass);
            $("#" + buttonId).text("重新发送");
        } else {
            $("#" + buttonId).prop('disabled', true).addClass(this.disabledClass);
            $("#" + buttonId).text("倒计时" + i + "秒");
            setTimeout("$.acooly.countdown.doCount('" + buttonId + "')", 1000)
        }
    },
};


(function ($) {
    if (!$.acooly) {
        $.extend({acooly: {}});
    }

    $.extend($.acooly, {
        portal: acooly_portal
    });

    $.extend($.acooly, {
        password: acooly_password
    });

    $.extend($.acooly, {
        countdown: acooly_countdown
    });


    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

})(jQuery);


