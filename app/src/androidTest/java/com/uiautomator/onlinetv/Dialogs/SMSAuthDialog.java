package com.uiautomator.onlinetv.Dialogs;

import com.uiautomator.onlinetv.PageObject;

import java.util.HashMap;

import androidx.test.uiautomator.UiDevice;

public class SMSAuthDialog extends PageObject {

    public String Title = "com.gsgroup.tricoloronline.mobile:id/alertTitle";
    public String message = "android:id/message";
    public String pin_code_field = "com.gsgroup.tricoloronline.mobile:id/et_code";
    public String btn_ok = "android:id/button1";
    public String btn_cancel = "android:id/button2";

    //Упрощенная карта связки контролов
    public HashMap<String, String> ui_controls;

    public SMSAuthDialog(UiDevice _d) {
        super(_d);
        this.ui_controls = new HashMap<String, String>();
        ui_controls.put("Заголовок", this.Title);
        ui_controls.put("Сообщение", this.message);
        ui_controls.put("Поле ввода смс кода", this.pin_code_field);
        ui_controls.put("Кнопка ок", this.btn_ok);
        ui_controls.put("Кнопка отмены", this.btn_cancel);
    }
}
