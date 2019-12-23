package com.uiautomator.onlinetv.UI_Tests;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.uiautomator.onlinetv.MainScreen.MainMenu_auth;
import com.uiautomator.onlinetv.MainScreen.SettingsScreen;
import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

@RunWith(AndroidJUnit4.class)
public class TestSettingsUI {

    UiDevice device;
    Login login;

    Support support;


    @Before
    public void init() throws RemoteException, InterruptedException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();

        support.RunApp();//запуск приложения
        support.Valid_Enter();//авторизуемся
        device.pressBack();//скрываем шторку
        Thread.sleep(1500);
        go_to_settings();
    }

    @Test
    public void flow() throws InterruptedException {

        SettingsScreen settingsScreen = new SettingsScreen(device);

        menu_test(settingsScreen);
        title_screen_test(settingsScreen);
        mobile_internet_perm(settingsScreen);
        mobile_parent_rate_perm(settingsScreen);
    }

    private void menu_test(SettingsScreen settingsScreen) throws InterruptedException {
        //Проверка работоспособности кнопки
        settingsScreen = new SettingsScreen(device);
        settingsScreen.menu_button.click();
        MainMenu_auth menu = new MainMenu_auth(device);
        support.TrySearch_by_id(menu.user_id, 3000);
        device.pressBack();
        settingsScreen.Title_screen.getText();
    }
    private void title_screen_test(SettingsScreen settingsScreen)
    {
        String title = settingsScreen.Title_screen.getText();
        Assert.assertEquals("Настройки", title);
    }
    private void mobile_internet_perm(SettingsScreen settingsScreen)
    {
        String title = settingsScreen.Title_internet_perm.getText();
        Assert.assertEquals("Каналы на ТВ", title);
        Assert.assertEquals("android.widget.TextView", settingsScreen.Title_internet_perm.getClassName());

        String message = settingsScreen.switch_internet_perm.getText();
        Assert.assertEquals("Разрешить просматривать каналы с использованием мобильного интернета ОТКЛ.", message);
        Assert.assertEquals("android.widget.Switch", settingsScreen.switch_internet_perm.getClassName());
    }
    private void mobile_parent_rate_perm(SettingsScreen settingsScreen){
        String title = settingsScreen.Title_parent_rate.getText();
        Assert.assertEquals("Родительский контроль", title);
        Assert.assertEquals("android.widget.TextView", settingsScreen.Title_parent_rate.getClassName());

        String message = settingsScreen.switc_parent_rate.getText();
        Assert.assertEquals("Активировать пин-код для просмотра контента 18+ ОТКЛ.", message);
        Assert.assertEquals("android.widget.Switch", settingsScreen.switc_parent_rate.getClassName());

        String vk = settingsScreen.link_text.getText();
        Assert.assertEquals("Группа Вконтакте", vk);
        Assert.assertEquals("android.widget.TextView", settingsScreen.link_text.getClassName());
        Assert.assertEquals(true, settingsScreen.link_text.isClickable());

        String version = settingsScreen.version_text.getText();
        Assert.assertEquals("Триколор Онлайн ТВ v.2.2.376", version);
        Assert.assertEquals(false, settingsScreen.version_text.isClickable());
    }


    private void go_to_settings() throws InterruptedException {

        Toolbar toolbar = new Toolbar(device);
        toolbar.click_on_SubMenu();

        MainMenu_auth menu = new MainMenu_auth(device);
        support.TrySearch_by_id(menu.user_id, 3000);//отработка анимации
        menu.Settings_click();
    }

    @After
    public void after() throws InterruptedException, RemoteException {

        Toolbar toolbar = new Toolbar(device);
        toolbar.click_on_SubMenu();
        PageAuth auth = new PageAuth(device);
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/sign_out_button")).click();
        support.TrySearch_by_id("android:id/message", 3000);
        device.findObject(By.res("android:id/button1")).click();
        support.TrySearch_by_id(auth.id_field, 10000);

        Thread.sleep(3000);

        closeApp();
    }

    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем приложение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }


}
