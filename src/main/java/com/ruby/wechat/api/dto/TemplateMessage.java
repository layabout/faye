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

    @XmlElement
    private String touser;

    @XmlElement
    private String msgtype;

    @XmlElementWrapper(name="data")
    @XmlElement
    private List<TemplateData> item;

    public TemplateMessage() {
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public List<TemplateData> getItems() {
        return item;
    }

    public void setItems(List<TemplateData> item) {
        this.item = item;
    }
}
