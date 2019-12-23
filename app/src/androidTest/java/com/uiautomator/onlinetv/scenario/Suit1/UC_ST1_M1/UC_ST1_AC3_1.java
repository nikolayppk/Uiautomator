package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;


//кейс авторизации по логин-пароль
public class UC_ST1_AC3_1 {

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
    public void test() throws RemoteException, InterruptedException {
        support.RunApp();//запуск приложения
        PageAuth auth = new PageAuth(device);

        Auth();//авторизуемся
        checkID();//чекаем id в submenu

        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/sign_out_button")).click();
        support.TrySearch_by_id("android:id/message", 3000);
        device.findObject(By.res("android:id/button1")).click();
        support.TrySearch_by_id(auth.id_field, 10000);

        Thread.sleep(3000);
    }

    private void Auth() throws InterruptedException, RemoteException {
        //создаем обвес страницы авторизации
        PageAuth auth = new PageAuth(device);
        support.TrySearch_by_id(auth.Enter_btn, 30000);
        auth.LoginField(login.login);
        auth.PasswordField(login.password);
        auth.Enter();
        //ждем проявления toolbar
        toolbar();
    }

    private void toolbar() throws InterruptedException {

        Toolbar toolbar = new Toolbar(device);
        support.TrySearch_by_id(toolbar.search_icon, 30000);
        toolbar.click_on_SubMenu();
    }
    private void checkID() throws InterruptedException {
        //Чекаем id на submenu
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/user_id", 15000);
        String result = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/user_id")).getText();
        Assert.assertEquals("ID: "+login.login, result);
    }

    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем прилоение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }

    @After
    public void after() throws InterruptedException, RemoteException {
        closeApp();
    }
}
