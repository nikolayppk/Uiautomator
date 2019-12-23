package com.uiautomator.onlinetv.problems;

import android.support.test.InstrumentationRegistry;


import com.uiautomator.onlinetv.Dialogs.Error21Dialog;
import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class UC_ST1_token_no_internet {

    UiDevice device;
    Support support;//Сборник вспомогательных методов
    //Диалоговое окно
    Error21Dialog error;
    //текст который контролируется
    private String text_message = "Отсутствует подключение к сети Интернет (ошибка 21)";
    private String text_btn = "В НАСТРОЙКИ";
    //Данные для авторизации
    private String login = "41040900084655";
    private String password = "12345tgb";

    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//получаем объект драйвера
        this.support = new Support(device);//вспомогательные функции
        this.support.RunApp();//Запуск приложения*/
    }

    @Test
    public void Test() throws InterruptedException {
        Auth();
        support.Wifi(false);
        Thread.sleep(2000);
        support.RunApp();
        check1();
    }
    public void check1() throws InterruptedException {
        Error21Dialog error = new Error21Dialog(device);
        support.TrySearch_by_id(error.message, 3000);
        Assert.assertEquals(this.text_message, error.getMessage());
        Assert.assertEquals(this.text_btn, error.getText_btn_inSettings());
    }
    private void Auth() throws InterruptedException {

        PageAuth auth = new PageAuth(device);
        support.TrySearch_by_id(auth.Enter_btn, 3000);
        auth.LoginField(login);
        auth.PasswordField(password);
        auth.Enter();
        Toolbar toolbar = new Toolbar(device);
        support.TrySearch_by_id(toolbar.like_icon, 3000);
        toolbar.click_on_SubMenu();
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/user_id", 3000);
        String result = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/user_id")).getText();
        Assert.assertEquals("ID: "+login, result);
        Thread.sleep(2000);
        device.pressHome();
    }

    @After
    public void after()
    {
        support.Wifi(true);
    }
}
