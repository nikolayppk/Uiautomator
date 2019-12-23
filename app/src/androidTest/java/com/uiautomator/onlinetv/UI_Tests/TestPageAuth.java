package com.uiautomator.onlinetv.UI_Tests;


import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

@RunWith(AndroidJUnit4.class)
public class TestPageAuth {

    PageAuth page;//Тестируемый PageObject
    UiDevice device;//контроллер

    @Before
    public void init() throws InterruptedException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.page = new PageAuth(device);
        Support support = new Support(device);
        support.RunApp();
        support.TrySearch_by_id(page.id_field, 30000);
    }
    @Test
    public void LoginField()
    {
       String login = page.ui_controls.get("Поле логина");
       device.findObject(By.res(login)).setText("41040900084655");
    }
    @Test
    public void PasswordField(){
        String password = page.ui_controls.get("Поле пароля");
        device.findObject(By.res(password)).setText("12345678");
        String result =  device.findObject(By.res(password)).getText();
        Log.i("Password", result);
    }
    @Test
    public void EnterButton()
    {
        String enter = page.ui_controls.get("Войти");
        String text = device.findObject(By.res(enter)).getText();
        Assert.assertEquals("Войти", text);
    }
    @Test
    public void SMS_password()
    {
        String sms = page.ui_controls.get("Получить код SMS");
        String result = device.findObject(By.res(sms)).getText();
        Assert.assertEquals("Получить код СМС–сообщением", result);
    }
    @Test
    public void WithoutReg()
    {
        String text = page.ui_controls.get("Продолжить без авторизации");
        String result = device.findObject(By.res(text)).getText();
        Assert.assertEquals("Продолжить без авторизации", result);
    }
    @After
    public void after() throws RemoteException, InterruptedException {
        Support support = new Support(device);
        support.closeApp_with_swipe();
    }
}
