package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateData {

    private String mark;

    private String value;

    private String color;

    public TemplateData() {
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
