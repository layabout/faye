package com.ruby.wechat.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by ruby on 2016/8/5.
 * Email:liyufeng_23@163.com
 */
public class Button {

    /**一级菜单数组，个数应为1~3个*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String button;
    /**
     * 二级菜单数组，个数应为1~5个
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Button> sub_button;
    /**菜单的响应动作类型*/
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    /**菜单标题，不超过16个字节，子菜单不超过60个字节*/
    private String name;
    /**
     * click等点击类型必须
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String key;
    /**
     * view类型必须
     * 网页链接，用户点击菜单可打开链接，不超过1024字节
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;
    /**
     * media_id类型和view_limited类型必须
     * 调用新增永久素材接口返回的合法media_id
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String media_id;

    public Button() {
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public List<Button> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Button> sub_button) {
        this.sub_button = sub_button;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
