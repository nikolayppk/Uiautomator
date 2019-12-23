package com.uiautomator.onlinetv;

import androidx.test.uiautomator.UiDevice;

public class PageObject {

    protected UiDevice device;

    public PageObject(UiDevice _d)
    {
        this.device = _d;
    }
}
