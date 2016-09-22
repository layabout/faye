package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ruby on 2016/9/22.
 * Email:liyufeng_23@163.com
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WXEncryptMessage {
    //开发者微信号
    @XmlElement(name = "ToUserName")
    private String toUserName;
    //密文
    @XmlElement(name = "Encrypt")
    private String encrypt;

    public WXEncryptMessage() {
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }
}
