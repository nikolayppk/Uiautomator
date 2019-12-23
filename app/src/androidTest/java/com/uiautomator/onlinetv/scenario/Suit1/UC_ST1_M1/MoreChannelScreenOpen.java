package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.moreAboutChannel;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

public class MoreChannelScreenOpen {

    UiDevice device;//объект экрана
    Login login;//объект данных пользователя
    Support support;//класс ui поддержки
    private moreAboutChannel aboutChannel;

    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
    }

    @Test
    public void flow() throws InterruptedException, RemoteException {

        support.RunApp();//запуск приложения
        PageAuth auth = new PageAuth(device);
        support.Valid_Enter();
        device.pressBack();
        //магия


        Try1();//входим в экран из главного экрана
        Try2();//входим в экран из экрана плеера
        Try3();

        Thread.sleep(1000);
        device.setOrientationNatural();
    }

    public void Try1() throws InterruptedException {
        support.go_to_channel("Рекомендуем", "Рыжий");
        aboutChannel = new moreAboutChannel();
        support.TrySearch_by_id(aboutChannel.channel_name, 4000);
        ChannelCheck("Рыжий", aboutChannel);
    }

    public void Try2() throws RemoteException, InterruptedException {
        device.setOrientationLeft();
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/coordinator_touch_background", 3000);//ищем плеер
        device.setOrientationNatural();
        support.TrySearch_by_id(aboutChannel.channel_name, 4000);
        ChannelCheck("Рыжий", aboutChannel);
    }
    public void Try3() throws InterruptedException, RemoteException {
        device.pressBack();
        Thread.sleep(1300);
        device.setOrientationLeft();
        Thread.sleep(800);
        support.go_to_channel("Рекомендуем", "Рыжий");
        Thread.sleep(1000);
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/coordinator_touch_background", 3000);//ищем плеер
        device.pressBack();
    }

    private void ChannelCheck(String name, moreAboutChannel moreAbout)
    {
        String channel_title = device.findObject(By.res(moreAbout.channel_name)).getText();
        Assert.assertEquals(name, channel_title);//проверяем заголовок канала
        device.findObject(By.res(moreAbout.epg_description)).getText();//проверяем наличие контейнера
        device.findObject(By.res(moreAbout.player)).getClass();//проверяем наличие плеера
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
