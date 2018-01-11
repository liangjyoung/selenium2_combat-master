package com.demo.test.pages;

import org.openqa.selenium.By;
/**
 * @description 登录页面元素定位声明
 * */
public class LoginPage {
    /**用户名输入框*/
    public static final By LP_INPUT_USERNAME = By.id("username");
    
    /**动态码输入框*/
    public static final By LP_INPUT_PASSWORD = By.id("password");
    
    /**登录按钮*/
    public static final By LP_BUTTON_LOGIN = By.id("login");
    
    /**登录错误信息*/
    public static final By LP_TEXT_ERROR= By.xpath("//*[@color='red']");
    
}