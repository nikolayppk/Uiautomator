package com.uiautomator.onlinetv.Dialogs;


import com.uiautomator.onlinetv.PageObject;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class Error21Dialog extends PageObject {
    public String message = "android:id/message";//Отсутствует подключение к сети интернет (ошибка 21)
    public String inSettings = "android:id/button1";//В настройки

    public Error21Dialog(UiDevice _d) {
        super(_d);
    }
    public void InSettings_click()
    {
        this.device.findObject(By.res(this.inSettings)).click();
    }

    public String getText_btn_inSettings()
    {
        String text = device.findObject(By.res(this.inSettings)).getText();
        return text;
    }
    public String getMessage()
    {
        String message = device.findObject(By.res(this.message)).getText();
        return message;
    }
}
