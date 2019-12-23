package com.uiautomator.onlinetv.MainScreen;

import com.uiautomator.onlinetv.PageObject;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class MainMenu_auth extends PageObject {

    public String user_id = "com.gsgroup.tricoloronline:id/user_id";//надпись с id текущего пользователя
    public String device_name = "com.gsgroup.tricoloronline:id/user_device";//надпись с именем текущего устройства
    public String out_icon = "com.gsgroup.tricoloronline:id/sign_out_button";//кнопка разлогина

    //Имена элементов подменю:
    public String MainScreen = "Главный экран";//by desc
    public String Timers = "Таймеры";//by desc
    public String Settings = "Настройки";//by desc

    public MainMenu_auth(UiDevice _d) {
        super(_d);
    }

    public String getUserID()
    {
        String id = device.findObject(By.res(user_id)).getText();
        return id;
    }
    public String getDevice_name()
    {
        String d = device.findObject(By.res(device_name)).getText();
        return d;
    }
    public void sign_out_icon_click()
    {
        device.findObject(By.res(out_icon)).click();
    }
    public void MainScreen_click()//клик на главное меню
    {
        device.findObject(By.text(MainScreen)).click();
    }
    public void Timers_click()//клик на таймеры
    {
        device.findObject(By.text(Timers)).click();
    }
    public void Settings_click()//клик на настройки
    {
        device.findObject(By.text(Settings)).click();
    }
}
