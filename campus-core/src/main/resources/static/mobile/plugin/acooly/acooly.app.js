/**
 * acooly App相关库封装
 * @type {{}}
 */

(function ($) {
    var acooly_app = {

        /**
         * 判断当前浏览器是否移动浏览器
         */
        isMobile: function () {
            var userAgentInfo = navigator.userAgent;
            var mobileAgents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
            var mobile_flag = false;
            //根据userAgent判断是否是手机
            for (var v = 0; v < mobileAgents.length; v++) {
                if (userAgentInfo.indexOf(mobileAgents[v]) > 0) {
                    mobile_flag = true;
                    break;
                }
            }
            var screen_width = window.screen.width;
            var screen_height = window.screen.height;

            //根据屏幕分辨率判断是否是手机
            if (screen_width < 500 && screen_height < 800) {
                mobile_flag = true;
            }
            return mobile_flag;
        },

        /**
         * 获取当前设备类型
         * return pc,android,ios,other
         */
        getDeviceType: function () {

            if (!this.isMobile()) {
                return 'pc';
            }

            var userAgent = navigator.userAgent;
            var p = navigator.platform;
            var isAndroid = userAgent.indexOf('Android') > -1 || userAgent.indexOf('Linux') > -1; //android终端或者uc浏览器
            var isiOS = !!userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

            if (isAndroid == true) {
                return 'android';
            }

            if (isiOS == true) {
                return 'ios';
            }
            return 'other';
        },

        /**
         * H5与native桥接方法注册
         * @param callback
         * @returns {*}
         */
        setupNativeToWebBridge: function (callback) {
            var deviceType = this.getDeviceType();
            console.info("deviceType", deviceType);
            if (deviceType == 'ios') {
                if (window.WKWebViewJavascriptBridge) {
                    return callback(WKWebViewJavascriptBridge);
                }
                if (window.WKWVJBCallbacks) {
                    return window.WKWVJBCallbacks.push(callback);
                }
                window.WKWVJBCallbacks = [callback];
                window.webkit.messageHandlers.iOS_Native_InjectJavascript.postMessage(null)
            } else if (deviceType == 'android') {
                callback(window.bridge);
            } else {
                // do nothding
            }
        },

        /**
         * 页面上注册回调事件和方法
         * web页面调用nativeApp
         * @param opts
         */
        webCallNative: function (opts) {
            var defOpts = {
                element: null,              // 绑定的元素（如按钮）
                event: null,                // 绑定的事件
                service: null,              // 服务名称
                jsonData: null,             // 参数数据
                onComplete: null            // app返回数据回调
            }
            var options = $.extend(defOpts, opts);
            this.setupNativeToWebBridge(function (bridge) {
                console.info("call app:" + JSON.stringify(options));
                $(options.element).on(options.event, function () {
                    /// H5 ---> APP    H5调用原生
                    var data = $.extend({'service': options.service}, options.jsonData);
                    bridge.callHandler('awake', data, function (response) {
                        if (options.onComplete != null) {
                            options.onComplete.call(this, response);
                        }
                    })
                })
            });
        },

        nativeCallWeb: function (opts) {

        }
    };
    $.extend($.acooly, {
        app: acooly_app
    });
})(jQuery);

