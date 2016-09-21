package com.ruby.wechat.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by ruby on 2016/8/8.
 * Email:liyufeng_23@163.com
 */
public class Menu {

    @JsonProperty(value="button")
    private List<Button> buttons;

    public Menu() {
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }
}
