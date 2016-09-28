package com.ruby.wechat.utils;

/**
 * 通知类型枚举
 * Created by ruby on 2016/9/28.
 * Email:liyufeng_23@163.com
 */
public enum NotificationType {

    TM001("交易通知", "TM001", "A2bFVeK0iUHeXZ7FtoOIcBdWXf95LwT_oKTmL-b9AH0", "url1", null),
    TM002("提现通知", "TM002", "B2bFVeK0iUHeXZ7FtoOIcBdWXf95LwT_oKTmL-b9AH0", "url2", "#FF0000"),
    TM003("转账通知", "TM003", "C2bFVeK0iUHeXZ7FtoOIcBdWXf95LwT_oKTmL-b9AH0", "url3", null);

    private String desc;

    private String template;

    private String template_id;

    private String url;

    private String topcolor;

    NotificationType(String desc, String template, String template_id, String url, String topcolor) {
        this.desc = desc;
        this.template = template;
        this.template_id = template_id;
        this.url = url;
        this.topcolor = topcolor;
    }

    public static String getTemplate(String template) {
        for (NotificationType c : NotificationType .values()) {
            if (c.getTemplate().equals(template)) {
                return c.template;
            }
        }
        return null;
    }

    public static NotificationType getNotificationType(String template) {
        for (NotificationType c : NotificationType .values()) {
            if (c.getTemplate().equals(template)) {
                return c;
            }
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    @Override
    public String toString() {
        return this.template;
    }

    public static void main (String[] args) {
        System.out.println(NotificationType.getTemplate("TM003") == null);
    }
}
