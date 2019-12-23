package com.uiautomator.onlinetv.Dialogs;

import com.uiautomator.onlinetv.PageObject;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class ExitDialog extends PageObject {

    public String abort_btn = "android:id/button2";//Отмена
    public String ok_btn = "android:id/button1";//ОК
    public String message = "android:id/message";//Exit message "Вы действительно хотите  выйти?"

    public ExitDialog(UiDevice _d) {
        super(_d);
    }

    public void abort_btn_click()
    {
        device.findObject(By.res(this.abort_btn)).click();
    }
    public void ok_btn_click()
    {
        device.findObject(By.res(this.ok_btn)).click();
    }
    public String getMessage()
    {
        String message = device.findObject(By.res(this.message)).getText();
        return message;
    }


}
