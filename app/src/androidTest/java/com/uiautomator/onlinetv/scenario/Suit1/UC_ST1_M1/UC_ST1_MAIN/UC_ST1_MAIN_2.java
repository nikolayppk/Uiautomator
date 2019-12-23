package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1.UC_ST1_MAIN;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

//проверка списка каналов внутри произвольной категории
public class UC_ST1_MAIN_2 {

    //Подсчет каналов внутри категорий.
    UiDevice device;
    Login login;

    public Support support;
    public HashMap<String, String> control;
    public ArrayList<String> result_channels;
    public ArrayList<Object> exp_result;

    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();

        this.exp_result = new ArrayList<>();
        exp_result.add("ПОБЕДА");
        exp_result.add("КиноПремиум HD");
        exp_result.add("Кино ТВ HD");
        exp_result.add("Рыжий");
        exp_result.add("Охотник и рыболов");
        exp_result.add("Эврика HD");
        exp_result.add("Моторспорт ТВ HD");
        exp_result.add("Телеканал Е HD");
        exp_result.add("Еда");
        exp_result.add("Zee TV");
        exp_result.add("M-1 GLOBAL");
    }

    @Test
    public void Test() throws RemoteException, InterruptedException {
        support.RunApp();//запуск приложения
        PageAuth auth = new PageAuth(device);
        support.Valid_Enter();
        Thread.sleep(500);
        device.pressBack();

        List<UiObject2> category = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_categories_list")).findObjects(By.clazz("android.widget.LinearLayout"));

        this.control = new HashMap<String, String>();
        this.result_channels = new ArrayList<String>();

        for (int i = 0; i < 20; i++){
            grabCategory_channels(category.get(2));
            scroll_right(category.get(2));//разметка корневого слоя категории
        }
        Log.e("AUTO", Integer.toString(this.result_channels.size()));
        for (String item : this.result_channels)
        {
            Log.e("AUTO", item);
        }
        Assert.assertEquals(11, this.result_channels.size());
        Assert.assertEquals(this.exp_result, this.result_channels);

    }

    public void Run()
    {
        List<UiObject2> category = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_categories_list")).findObjects(By.clazz("android.widget.LinearLayout"));

        this.control = new HashMap<String, String>();
        this.result_channels = new ArrayList<String>();

        for (int i = 0; i < 20; i++){
            grabCategory_channels(category.get(2));
            scroll_right(category.get(2));//разметка корневого слоя категории
        }
        Log.e("AUTO", Integer.toString(this.result_channels.size()));
        for (String item : this.result_channels)
        {
            Log.e("AUTO", item);
        }
    }

    private void grabCategory_channels(UiObject2 category)
    {
        UiObject2 scroll = category.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_list"));//получаем объект ленты
        List<UiObject2> channels_name = scroll.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name"));
        Log.e("AUTO", "Размер banners: " + Integer.toString(channels_name.size()));

        for (UiObject2 name : channels_name)
        {
            String text = name.getText();

            if (!control.containsKey(text))
            {
                this.result_channels.add(text);
                this.control.put(text, text);
                Log.e("AUTO", text);
            }

        }
    }

    private void scroll_right(UiObject2 channels)
    {
        UiObject2 channel_scroll = channels.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_list"));
        Rect rect = channel_scroll.getVisibleBounds();
        int y = (int) rect.exactCenterY();
        support.swipe_right(y);
    }
    private void scroll_left(UiObject2 channels)
    {
        UiObject2 channel_scroll = channels.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_list"));
        Rect rect = channel_scroll.getVisibleBounds();
        int y = (int) rect.exactCenterY();
        support.swipe_left(y);
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
