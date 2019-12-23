package com.uiautomator.onlinetv.Dialogs;

import com.uiautomator.onlinetv.PageObject;

import java.util.HashMap;

import androidx.test.uiautomator.UiDevice;

public class FailAuthDialog extends PageObject {

    public String message = "android:id/message";
    public String try_again = "android:id/button1";
    public String without_authorisation = "android:id/button2";

    public HashMap<String, String> ui_controls;

    public FailAuthDialog(UiDevice _d)
    {
        super(_d);
        ui_controls = new HashMap<String, String>();
        ui_controls.put("Сообщение",message);
        ui_controls.put("Попробовать еще раз", this.try_again);
        ui_controls.put("Продолжить без авторизации", this.without_authorisation);
    }



}
