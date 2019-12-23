package com.uiautomator.onlinetv.scenario.SettingsScreen;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.Dialogs.NeedInternetDialog;
import com.uiautomator.onlinetv.MainScreen.MainMenu_auth;
import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;


//разрешение на просмотр при помощи моблиьного интернета (лишь установка)
public class UC_V3_S1 {

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
        support.Wifi(false);//вырубаем wifi
        support.RunApp();//запускаем приложение
        support.Valid_Enter();//валидно логинимся
        device.pressBack();
        Thread.sleep(2000);


        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/fl_click_view", 3000);//ждем загрузки
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/fl_click_view")).click();
        NeedInternetDialog dialog = new NeedInternetDialog();


        support.TrySearch_by_id(dialog.root_lay, 3000);//проверяем наличие диалога
        Assert.assertEquals("ОТМЕНА", device.findObject(By.res(dialog.btn_abort)).getText());
        Assert.assertEquals("ПРОДОЛЖИТЬ ПРОСМОТР", device.findObject(By.res(dialog.btn_accept_wath)).getText());
        Assert.assertEquals("Для просмотра видео используется мобильный интернет", device.findObject(By.res(dialog.message)).getText());

        device.pressBack();//уходим с диалога
        device.findObject(By.text("Для просмотра подлкючитесь к сети Wifi"));//ищем текст
        device.pressBack();

        Toolbar toolbar = new Toolbar(device);//инициализация навигационного тулбара
        toolbar.click_on_SubMenu();//вызываем подменю
        MainMenu_auth menu = new MainMenu_auth(device);//инициализируем менюшку

        support.TrySearch_by_id(menu.user_id, 3000);//ждем появления
        menu.Settings_click();//идем в настройки
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/swc_allow_cellular", 3000);

        UiObject2 inet_switch = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/swc_allow_cellular"));
        String text_no = inet_switch.getText();//опрос

        Assert.assertEquals("Разрешить просматривать каналы с использованием мобильного интернета ОТКЛ." , text_no);
        inet_switch.click();//переключаем в разрешенное состояние
        String text_ok = inet_switch.getText();//опрос
        Assert.assertEquals("Разрешить просматривать каналы с использованием мобильного интернета ВКЛ." , text_ok);

        toolbar.click_on_SubMenu();//вызываем подменю.
        menu.MainScreen_click();//выходим на главный экран

        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/fl_click_view", 3000);//ждем загрузки
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/fl_click_view")).click();



        boolean status = true;
        try {
            support.TrySearch_by_id(dialog.root_lay, 2500);
            status = true;
        } catch (Exception e) {
            status = false;
        }
        Assert.assertEquals(false, status);//проверяем тот факт что диалога не было.


        //возвращаем в исходное состояние переключатель
        device.pressBack();
        toolbar.click_on_SubMenu();//вызываем подменю

        support.TrySearch_by_id(menu.user_id, 3000);//ждем появления
        menu.Settings_click();//идем в настройки
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/swc_allow_cellular", 3000);

        UiObject2 inet_switch1 = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/swc_allow_cellular"));
        inet_switch1.click();//переключаем в разрешенное состояние


        //готовимся к выходу
        toolbar.click_on_SubMenu();
        support.TrySearch_by_id(menu.user_id, 3000);
        menu.MainScreen_click();
    }



    @After
    public void after() throws InterruptedException, RemoteException {
        support.Wifi(true);//врубаем wifi
        support.sign_out();
        closeApp();
    }

    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем приложение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }
}
