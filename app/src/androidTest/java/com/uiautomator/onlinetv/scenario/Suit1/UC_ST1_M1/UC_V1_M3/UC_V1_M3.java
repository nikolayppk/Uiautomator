package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1.UC_V1_M3;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1.UC_V1_M2.UC_V1_M2;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

public class UC_V1_M3 {

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
    public void Test() throws RemoteException, InterruptedException {
        support.RunApp();//запуск приложения
        support.Valid_Enter();//валидная авторизация
        device.pressBack();//скрываем шторку

        Toolbar toolbar = new Toolbar(device);//объект тулбара
        support.TrySearch_by_id(toolbar.like_icon, 5000);//ищем иконку лайка
        Thread.sleep(500);
        toolbar.click_on_icon_like();//кликаем по лайку. Каналов нет. Проблема! иконка лайка меняется, но словить смену нельзя. локатор не меняется.

        String no_favorite = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_status_text")).getText();
        Assert.assertEquals("В списке «Любимые» нет каналов", no_favorite);//чекаем надпись. В списке любимых нет каналов десу.

        toolbar.click_on_icon_like();//снимаем фильтр лайка.
        //много кода вредно. Используем то что есть
        UC_V1_M2 go_to_channel = new UC_V1_M2();//Идем в канал FAN HD
        go_to_channel.device = this.device;//инициализация
        go_to_channel.support = this.support;//инициализация
        go_to_channel.Action();//действие. Входим в канал.

        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/action_menu_favorite")).click();//ставим лайк на канал
        String like_channel = go_to_channel.tv_channel;//запоминаем канал.
        device.findObject(By.desc("Перейти вверх")).click();//возвращаемся в главный экран.

        support.TrySearch_by_id(toolbar.like_icon, 5000);//ищем иконку лайка
        toolbar.click_on_icon_like();//ставим фильтр лайка.
        Thread.sleep(1000);//время на отработку анимации.

        List<UiObject2> names = device.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name"));

        for (UiObject2 item : names)
        {
            Assert.assertEquals("FAN HD", item.getText());//проверяем что везде лишь наш любимый канал.
        }

        names.get(0).click();//входим в канал.
        Thread.sleep(1000);//снимаем лайк с канала
        support.TrySearch_by_id(toolbar.like_icon, 5000);//ищем иконку лайка
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/action_menu_favorite")).click();
        Thread.sleep(1500);
        device.pressBack();//выходим в главный экран.
        Thread.sleep(1500);
        String no_favorite_1 = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_status_text")).getText();
        Assert.assertEquals("В списке «Любимые» нет каналов", no_favorite_1);//чекаем надпись. В списке любимых нет каналов десу.
    }

    @After
    public void after() throws InterruptedException, RemoteException {
        support.sign_out();
        closeApp();
    }

    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем прилоение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }

}
