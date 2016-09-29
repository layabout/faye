package com.ruby.wechat.api.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by ruby on 2016/9/27.
 * Email:liyufeng_23@163.com
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateMessage {

    private String touser;

    private String template;

    @XmlElementWrapper(name="data")
    private List<TemplateData> item;

    public TemplateMessage() {
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<TemplateData> getItems() {
        return item;
    }

    public void setItems(List<TemplateData> item) {
        this.item = item;
    }

}
