package com.qiudot.edu.web;

import org.apache.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 移动App端公共路由导航
 *
 * @author zhangpu@acooly.cn
 * @date 2018-08-15 16:31
 */
@Controller
@RequestMapping("/mobile/common/")
public class CommonRouterController {

    @RequestMapping("meta")
    public String meta(HttpServletRequest request) {
        return "/mobile/common/meta";
    }

    @RequestMapping("include")
    public String inlcude(HttpServletRequest request) {
        return "/mobile/common/include";
    }

    @RequestMapping("footer")
    public String footer(HttpServletRequest request) {
        return "/mobile/common/footer";
    }

    @RequestMapping("header")
    public String header(HttpServletRequest request) {
        return "/mobile/common/header";
    }

    @RequestMapping("html5Validate")
    public String html5Validate(HttpServletRequest request) {
        return "/mobile/common/html5Validate";
    }

    @RequestMapping("pullDownRefresh")
    public String pullDownRefresh(HttpServletRequest request) {
        return "/mobile/common/pullDownRefresh";
    }

    /**
     * 401错误
     *
     * @param request
     * @return
     */
    @RequestMapping("401")
    public String http401(HttpServletRequest request) {
        request.setAttribute("referer", request.getHeader(HttpHeaders.REFERER));
        return "/mobile/common/401";
    }

    @RequestMapping("404")
    public String http404(HttpServletRequest request) {
        return "/mobile/common/404";
    }


    @RequestMapping("result")
    public String result(HttpServletRequest request) {
        return "/mobile/common/result";
    }

}
