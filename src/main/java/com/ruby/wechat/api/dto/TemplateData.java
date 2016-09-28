package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateData {

    @XmlElement
    private String name;

    @XmlElement
    private String value;

    public TemplateData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
