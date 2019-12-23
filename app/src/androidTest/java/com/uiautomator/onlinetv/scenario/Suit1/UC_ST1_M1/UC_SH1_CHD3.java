package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.uiautomator.onlinetv.EpgCard;
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

public class UC_SH1_CHD3 {

    UiDevice device;
    Login login;

    Support support;
    private moreAboutChannel moreScreen;

    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
    }

    @Test
    public void flow() throws RemoteException, InterruptedException {
        prepare();
        Action();//сам тест

    }

    public void prepare() throws InterruptedException, RemoteException {
        support.RunApp();//запускаем приложение
        support.Valid_Enter();//валидная авторизация.
        device.pressBack();//скрываем шторку
        Thread.sleep(1000);//ждем отработки анимации
        support.swipe_down(5);//опускаем фокус до нужной категории
        Thread.sleep(400);//ждем пока анимация прокрутки отработает
        support.go_to_channel("HD", "Первый канал HD");//входим в канал Первый канал HD.
        Thread.sleep(1500);//ждем пока осуществиться вход
    }

    public void Action() throws InterruptedException {
        moreScreen = new moreAboutChannel();
        String current_epg_event = device.findObject(By.res(moreScreen.epg_title)).getText();//запоминаем текущее epg имя.
        current_epg_event = current_epg_event.substring(8, current_epg_event.length());
        Log.e("AUTO", current_epg_event);

        UiObject2 container = device.findObject(By.res(moreScreen.epg_container));
        String current_epg_desc1 = container.findObject(By.res(moreScreen.epg_description)).getText();//поучаем текущий текст передачи
        Log.e("AUTO", current_epg_desc1);

        device.findObject(By.res(moreScreen.epg_expand)).click();

        UiObject2 current_epg_desc = null;
        List<UiObject2> visible_epg = this.visible_epg();

        for (UiObject2 event : visible_epg)
        {
            String title_desc = event.getText();
            String adapt_title_desc = title_desc.substring(6);
            Log.e("AUTOF", adapt_title_desc);
            if (current_epg_event.equals(adapt_title_desc)) {
                Log.e("AUTOF", title_desc);
                current_epg_desc = event;
            } else {
                Log.e("AUTOF", title_desc);
                event.click();
                Thread.sleep(1500);
                //Блок работы с карточкой Epg.
                EpgCard card = new EpgCard();//открывается карточка EPG
                card.CheckCardUI(device);//проверяем что карточка открыта
                String card_title = device.findObject(By.res(card.title)).getText();//грабим текст карточки
                Assert.assertEquals(title_desc, card_title);//првоеряем совпадение.
                device.click(device.getDisplayWidth()/9,device.getDisplayHeight()/9);
                Thread.sleep(800);
            }

        }

        current_epg_desc.click();//кликаем по текущему активному ивенту.

        container = device.findObject(By.res(moreScreen.epg_container));
        String OpenEPG = container.findObject(By.res(moreScreen.epg_description)).getText();//поучаем текущий текст
        Log.e("AUTO", current_epg_desc1);
        Assert.assertEquals(current_epg_desc1, OpenEPG);
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

    public List<UiObject2> visible_epg()
    {
        ArrayList<String> epg = new ArrayList<String>();

        UiObject2 epg_list = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_epg_group_list"));// получаем епг лист
        List<UiObject2> epg_events = epg_list.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_epg_description"));//получаем все видимые ивенты.

        return epg_events;
    }
}
