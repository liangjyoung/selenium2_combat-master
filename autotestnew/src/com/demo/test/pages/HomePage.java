package com.demo.test.pages;

import org.openqa.selenium.By;

/**
 * @description 首页面元素定位声明
 * */
public class HomePage {
    
    /**用户名显示区域*/
    public static final By HP_TEXT_USERNAME= By.xpath(".//*[@id='navbar-container']/div[6]/b");
    /**修改密码按钮*/
    public static final By HP_BUTTON_FLIGHTS = By.xpath(".//*[@id='updatepassword']/button");
    /**退出按钮*/
    public static final By HP_BUTTON_ITINERARY = By.xpath(".//*[@id='exit']");
    /**Home按钮*/
    public static final By HP_BUTTON_HOME = By.xpath(".//*[@id='main-container']/div/div[2]/div[1]/ul/li/a");
    /**Sign Off按钮*/
  //  public static final By HP_BUTTON_SIGNOFF = By.xpath("//*[@src='/WebTours/images/signoff.gif']");
    /**首页完整文本*/
    public static final By HP_TEXT_HOME= By.xpath("html/body/div[3]/div[2]/span");    
}