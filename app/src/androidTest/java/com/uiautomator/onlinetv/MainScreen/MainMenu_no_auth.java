package com.uiautomator.onlinetv.MainScreen;


import com.uiautomator.onlinetv.PageObject;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class MainMenu_no_auth extends PageObject {


    public String Authorisation = "com.gsgroup.tricoloronline.mobile:id/sign_in_button";//Надпись авторизоваться
    //Имена элементов подменю:
    public String MainScreen = "Главный экран";//by desc
    public String Timers = "Таймеры";//by desc
    public String Settings = "Настройки";//by desc

    public String getAuthorisationText()
    {
        String text = device.findObject(By.res(Authorisation)).getText();
        return text;
    }

    public void clickAuthorisation()
    {
        device.findObject(By.res(Authorisation)).click();
    }

    public MainMenu_no_auth(UiDevice _d) {
        super(_d);
    }

    public void MainScreen_click()//клик на главное меню
    {
        device.findObject(By.desc(MainScreen)).click();
    }
    public void Timers_click()//клик на таймеры
    {
        device.findObject(By.desc(Timers)).click();
    }
    public void Settings_click()//клик на настройки
    {
        device.findObject(By.desc(Settings)).click();
    }
}
