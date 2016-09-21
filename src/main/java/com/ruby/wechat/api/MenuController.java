package com.ruby.wechat.api;

import com.ruby.wechat.api.dto.Button;
import com.ruby.wechat.api.dto.Menu;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruby on 2016/8/5.
 * Email:liyufeng_23@163.com
 */
@RestController
public class MenuController {


    @RequestMapping(value = {"/api/menu/get","/menu/get"}, method = RequestMethod.GET)
    public Menu getMenu(){
        Menu menu = new Menu();
        Button button = new Button();
        button.setName("扫码");


        Button subButton = new Button();
        subButton.setType("scancode_waitmsg");
        subButton.setName("扫码带提示");
        subButton.setKey("rselfmenu_0_0");

        List<Button> subButtons = new ArrayList<Button>();
        subButtons.add(subButton);

        button.setSub_button(subButtons);

        List<Button> buttons = new ArrayList<Button>();
        buttons.add(button);

        menu.setButtons(buttons);
        return menu;
    }

}
