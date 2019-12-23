package com.uiautomator.onlinetv;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class SettingsScreen {


    private final UiDevice device;
    public String title_mobile_internet_permission = "com.gsgroup.tricoloronline.mobile:id/tv_title_cellular";//заголовок
    public String mobile_interner_permission = "com.gsgroup.tricoloronline.mobile:id/swc_allow_cellular";//переключатель разрешения на использование мобильного интернета

    public String title_parent_rate_permission = "com.gsgroup.tricoloronline.mobile:id/tv_title_parent_control";//заголовок
    public String parent_rate_permission = "com.gsgroup.tricoloronline.mobile:id/swc_enable_parent_control";//разрешение родительского контроля

    public String app_version_lbl = "com.gsgroup.tricoloronline.mobile:id/tv_app_version";//версия мобильного приложения

    public SettingsScreen(UiDevice _d){
        this.device = _d;
    }

    public String getTitle_parent_rate_permission()
    {
        String text = device.findObject(By.res(this.title_parent_rate_permission)).getText();
        return text;
    }

    public String getMobile_interner_permission()
    {
        String text = device.findObject(By.res(mobile_interner_permission)).getText();
        return text;
    }

    public String getParent_rate_permission()
    {
        String text = device.findObject(By.res(parent_rate_permission)).getText();
        return text;
    }

    public String getApp_version_lbl()
    {
        String text = device.findObject(By.res(app_version_lbl)).getText();
        return text;
    }
    public void click_Mobile_internet_rermission()
    {
        device.findObject(By.res(this.mobile_interner_permission)).click();
    }
    public void click_Parent_rate_permission()
    {
        device.findObject(By.res(this.parent_rate_permission)).click();
    }
}
