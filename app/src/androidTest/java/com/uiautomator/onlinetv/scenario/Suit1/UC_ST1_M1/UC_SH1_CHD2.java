package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.uiautomator.onlinetv.moreAboutChannel;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

public class UC_SH1_CHD2 {

    UiDevice device;
    Login login;

    Support support;
    private moreAboutChannel more_channel;

    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
    }

    @Test
    public void test() throws RemoteException, InterruptedException, UiObjectNotFoundException {
        support.RunApp();//запускаем приложение
        support.Valid_Enter();//валидная авторизация.
        device.pressBack();//скрываем шторку

        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/rv_channel_list",30000);

        Thread.sleep(1000);//ждем отработки анимации
        support.swipe_down(5);//опускаем фокус до нужной категории
        Thread.sleep(400);


        support.go_to_channel("HD", "Первый канал HD");//входим в канал Первый канал HD.
        Thread.sleep(1500);
        ChannelExpand();

        epg_Check();//проверяем активность переключения дня епг.
        scroll_epg_check();//проверяем скролл
    }


    public void ChannelExpand() throws InterruptedException {
        //работа в новом окне.
        this.more_channel = new moreAboutChannel();//инициализируем PageObject
        String current_epg_title = device.findObject(By.res(more_channel.epg_title)).getText();//получаем имя передачи
        String current_epg_desc = device.findObject(By.res(more_channel.epg_description)).getText();//поучаем текущий текст
        Log.e("AUTO", current_epg_title);
        Log.e("AUTO", current_epg_desc);
        device.findObject(By.res(more_channel.epg_expand)).click();

        Thread.sleep(1500);
        boolean status = true;
        try {
            UiObject2 container = device.findObject(By.res(more_channel.epg_container));
            String current_epg_desc1 = container.findObject(By.res(more_channel.epg_description)).getText();//поучаем текущий текст
            Log.e("AUTO", current_epg_desc1);
        } catch (Exception e)
        {
            status = false;
        }
        Assert.assertEquals(false, status);
    }

    public ArrayList<String> visible_epg()
    {
        ArrayList<String> epg = new ArrayList<String>();

        UiObject2 epg_list = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_epg_group_list"));// получаем епг лист
        List<UiObject2> epg_events = epg_list.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_epg_description"));//получаем все видимые ивенты.

        for (UiObject2 event : epg_events)
        {
            String desc = event.getText();
            Log.e("AUTO", desc);
            epg.add(desc);
        }

        return epg;
    }

    public void epg_Check() throws InterruptedException {
        ArrayList<String> visible_epg1 = visible_epg();//получаем список передач
        device.findObject(By.text("Вчера")).click();//переключаем ленту epg на вчера.
        Thread.sleep(1000);
        ArrayList<String> visible_epg2 = visible_epg();//получаем список передач

        boolean status = visible_epg1.equals(visible_epg2);
        Assert.assertEquals(false, status);
    }

    public void scroll_epg_check() throws UiObjectNotFoundException {

        String current_epg_title = device.findObject(By.res(more_channel.epg_title)).getText();//получаем имя передачи
        device.findObject(By.res(more_channel.player)).getClass();//проверяем что плеер не скролится.
        device.findObject(By.res(more_channel.epg_container)).getClass();//проверяем что контейнер не скролится.


        ArrayList<String> visible_epg1 = visible_epg();//получаем список передач
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.gsgroup.tricoloronline.mobile:id/rv_epg_group_list"));
        scroll.scrollForward(100);
        ArrayList<String> visible_epg2 = visible_epg();//получаем список передач
        boolean status = visible_epg1.equals(visible_epg2);
        Assert.assertEquals(false, status);

        String current_epg_title2 = device.findObject(By.res(more_channel.epg_title)).getText();//получаем имя передачи
        device.findObject(By.res(more_channel.player)).getClass();//проверяем что плеер не скролится.
        device.findObject(By.res(more_channel.epg_container)).getClass();//проверяем что контейнер не скролится.
        Assert.assertEquals(current_epg_title, current_epg_title2);//сравниваем, что передача не изменилась
    }

    @After
    public void after() throws InterruptedException, RemoteException {
        device.pressBack();
        support.sign_out();
        closeApp();
    }

    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем прилоение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }
}
