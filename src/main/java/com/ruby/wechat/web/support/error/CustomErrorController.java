package com.ruby.wechat.web.support.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ruby on 2016/5/26.
 * Email:liyufeng_23@163.com
 */
@Controller
public class CustomErrorController {
    /**
     * Display an error page, as defined in web.xml <code>custom-error</code> element.
     */

    @RequestMapping("/error/404")
    public String notFoundError() {
        return "error/404";
    }

    @RequestMapping("/error/500")
    public String serverError() {
        return "error/500";
    }
}
