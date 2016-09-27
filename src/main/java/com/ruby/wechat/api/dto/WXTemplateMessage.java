package com.ruby.wechat.api.dto;

import java.util.List;
import java.util.Map;

/**
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
public class WXTemplateMessage {

    private String touser;

    private String template_id;

    private String url;

    private Map<String, WXTemplateData> data;

    public WXTemplateMessage() {
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, WXTemplateData> getData() {
        return data;
    }

    public void setData(Map<String, WXTemplateData> data) {
        this.data = data;
    }
}
