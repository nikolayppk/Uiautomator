package com.uiautomator.onlinetv;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class EpgCard {

    public String poster = "com.gsgroup.tricoloronline.mobile:id/iv_event_poster";
    public String title = "com.gsgroup.tricoloronline.mobile:id/tv_epg_title";
    public String description = "com.gsgroup.tricoloronline.mobile:id/tv_epg_description";
    public String action = "com.gsgroup.tricoloronline.mobile:id/btn_action";

    public void CheckCardUI(UiDevice device)
    {
        device.findObject(By.res(this.poster)).getClass();
        device.findObject(By.res(this.title)).getText();
        device.findObject(By.res(this.description)).getText();
        device.findObject(By.res(this.action)).getText();
    }

}
