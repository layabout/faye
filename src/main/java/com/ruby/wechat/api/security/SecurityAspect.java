package com.ruby.wechat.api.security;

import com.ruby.common.exception.BusinessException;
import com.ruby.wechat.Constants;
import com.ruby.wechat.utils.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全处理
 * Created by ruby on 2016/9/29.
 * Email:liyufeng_23@163.com
 */
@Component
@Aspect
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    @Pointcut("execution(* com.ruby.wechat.api.WXServiceAPI.sendTemplateMessage(..))")
    public void securityAspect() {
    }

    @Before("securityAspect()")
    public void doBefore(JoinPoint jp) throws Exception{
        logger.trace("安全检查开始...");

        HttpServletRequest request = null;

        Object[] params = jp.getArgs();
        for(int i = 0; i<params.length; i++) {
            if(params[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest)params[i];
                break;
            }
        }

        String req_ip = IpUtils.getIpAddr(request);
        logger.trace("请求IP地址: {}", req_ip);

        if (!IpUtils.isIpAllowed(req_ip, Constants.WX_API_ALLOWED_IP))
            throw new BusinessException("IP禁止访问！", "403");
    }
}
