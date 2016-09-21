package com.ruby.wechat.web.support.handler;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by ruby on 2016/5/25.
 * Email:liyufeng_23@163.com
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger("Logs");

    @ExceptionHandler(Exception.class)
    private ModelAndView defaultExceptionHandler(Exception exception) {

        ModelAndView modelAndView = new ModelAndView("error/general");
        Throwable rootCause = Throwables.getRootCause(exception);
        modelAndView.addObject("errorMessage",exception.getMessage());

        logger.error(rootCause.toString(), exception);

        return modelAndView;
    }

}
