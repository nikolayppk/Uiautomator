package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1.UC_V1_M2;

import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.util.Log;


import com.uiautomator.onlinetv.PageAuth;
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

//серфинг по категории, выбор канала для просмотра.
public class UC_V1_M2 {

    public UiDevice device;
    public Login login;

    public Support support;

    public String tv_channel = "FAN HD";
    public String tv_category = "HD";

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
        support.Valid_Enter();//осуществляем вход в систему.
        device.pressBack();//скрываем шторку.
        Thread.sleep(1000);
        support.swipe_down(5);

        UiObject2 category = grabCategory();//грабим категорию HD


        UiObject2 channel = null;
        do {

            scroll_right(category);//проверка скрола.
            channel = grabCategory_channels(category);//грабим канал

        } while(channel == null);

        channel.click();
        Thread.sleep(3000);

        String name = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name")).getText();//грабим текст с заголовка канала
        Assert.assertEquals(this.tv_channel, name);
    }

    public void Action() throws InterruptedException {
        support.swipe_down(5);

        UiObject2 category = grabCategory();//грабим категорию HD


        UiObject2 channel = null;
        do {

            scroll_right(category);//проверка скрола.
            channel = grabCategory_channels(category);//грабим канал

        } while(channel == null);

        channel.click();
        Thread.sleep(3000);

        String name = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name")).getText();//грабим текст с заголовка канала
        Assert.assertEquals(this.tv_channel, name);
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

    public UiObject2 grabCategory()
    {
        List<UiObject2> curent_category = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_categories_list")).findObjects(By.clazz("android.widget.LinearLayout"));
        for (UiObject2 item : curent_category)
        {
            try {
                UiObject2 category_lbl = item.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_category_name"));
                String name = category_lbl.getText();
                if (name.equals(this.tv_category)){
                    return item;
                }

            } catch (Exception e) {
            }
        }
        return null;
    }
    private void scroll_right(UiObject2 channels)
    {
        UiObject2 channel_scroll = channels.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_list"));
        Rect rect = channel_scroll.getVisibleBounds();
        int y = (int) rect.exactCenterY();
        support.swipe_right(y);
    }

    private UiObject2 grabCategory_channels(UiObject2 category)
    {
        UiObject2 scroll = category.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_list"));//получаем объект ленты
        List<UiObject2> channels_name = scroll.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name"));
        Log.e("AUTO", "Размер banners: " + Integer.toString(channels_name.size()));

        for (UiObject2 name : channels_name)
        {
            String text = name.getText();
            if (text.equals(this.tv_channel)) return name.getParent();
        }
        return null;
    }

}
