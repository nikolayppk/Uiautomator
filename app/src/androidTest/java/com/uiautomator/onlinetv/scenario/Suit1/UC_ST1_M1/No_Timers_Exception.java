package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.MainScreen.MainMenu_auth;
import com.uiautomator.onlinetv.MainScreen.MainMenu_no_auth;
import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.SettingsScreen;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;


public class No_Timers_Exception {

    UiDevice device;
    Login login;
    Support support;


    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
    }

    @Test
    public void flow() throws RemoteException, InterruptedException {

        support.RunApp();//запуск приложения
        support.Valid_Enter();//авторизуемся
        device.pressBack();//скрываем шторку
        Thread.sleep(1500);

        Toolbar toolbar = new Toolbar(device);
        support.TrySearch_by_id(toolbar.search_icon,4000);
        toolbar.click_on_SubMenu();

        MainMenu_auth menu = new MainMenu_auth(device);
        support.TrySearch_by_id(menu.user_id, 5000);
        menu.Timers_click();//Кликаем на таймеры

        support.TrySearch_by_text("Таймеры", 5000);
        UiObject2 text_no_timers = device.findObject(By.res("com.gsgroup.tricoloronline:id/empty_text"));
        String text = text_no_timers.getText();

        Assert.assertEquals("Нет запланированных таймеров", text);
    }

    @After
    public void after() throws InterruptedException, RemoteException {

        support.sign_out();
        closeApp();
    }

    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем приложение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }
}
