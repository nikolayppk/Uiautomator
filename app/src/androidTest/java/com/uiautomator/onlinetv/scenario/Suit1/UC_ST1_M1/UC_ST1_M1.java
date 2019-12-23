package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

public class UC_ST1_M1 {

    UiDevice device;
    Login login;

    Support support;
    private HashMap<String, String> result_channels;
    private HashMap<String, String> control;

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
        PageAuth auth = new PageAuth(device);
        support.Valid_Enter();
        device.pressBack();

        Action();//ищем известный неоплаченный канал
    }

    private void Action() throws InterruptedException {
        Toolbar toolbar = new Toolbar(device);
        toolbar.click_on_icon_search();
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/search_src_text", 3000);
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/search_src_text")).setText("матч премьер");

        List<UiObject2> category = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_categories_list")).findObjects(By.clazz("android.widget.LinearLayout"));

        this.control = new HashMap<String, String>();
        this.result_channels = new HashMap<String, String>();

        UiObject2 channel_final = null;

        for (int i = 0; i < 3; i++){
            channel_final = grabCategory_channels(category.get(0));
            scroll_right(category.get(0));//разметка корневого слоя категории
        }

        Assert.assertEquals(true, result_channels.containsKey("МАТЧ ПРЕМЬЕР HD"));

        channel_final.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/iv_unpaid_icon")).getClassName();
    }

    private UiObject2 grabCategory_channels(UiObject2 category)
    {
        UiObject2 scroll = category.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_list"));//получаем объект ленты
        List<UiObject2> channels_name = scroll.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name"));
        Log.e("AUTO", "Размер banners: " + Integer.toString(channels_name.size()));

        for (UiObject2 name : channels_name)
        {
            String text = name.getText();

            if (!control.containsKey(text))
            {
                this.result_channels.put(text,text);
                this.control.put(text, text);
                Log.e("AUTO", text);
            }
            if (text.equals("МАТЧ ПРЕМЬЕР HD")) return name.getParent();

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

    @After
    public void after() throws InterruptedException, RemoteException {
        Toolbar toolbar = new Toolbar(device);
        device.pressBack();
        Thread.sleep(800);
        device.pressBack();
        support.TrySearch_by_id(toolbar.search_icon,5000);
        support.sign_out();
        closeApp();
    }

    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем прилоение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }
}
