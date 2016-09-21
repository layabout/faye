package com.ruby.wechat.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * Created by ruby on 2016/8/10.
 * Email:liyufeng_23@163.com
 */
@Controller
public class HomeController {

    @Autowired
    DefaultKaptcha captchaProducer;

    @RequestMapping(value = {"/","/home","/index"}, method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        session.setAttribute(com.google.code.kaptcha.Constants. KAPTCHA_SESSION_KEY , capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO. write (bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

}
