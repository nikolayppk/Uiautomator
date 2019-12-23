package com.uiautomator.onlinetv.support;

import android.content.Context;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.PageAuth;

import org.junit.Assert;

import java.util.List;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

public class Support {

    UiDevice device;
    private static String name_app = "Онлайн ТВ";
    private Login login = new Login();

    public Support(UiDevice _d)
    {
        this.device = _d;
    }

    public void RunApp()
    {
        device.findObject(By.text(name_app)).click();


    }

    public void login()
    {
        Login login = new Login();
        PageAuth page = new PageAuth(device);
        page.LoginField(login.login);
        page.PasswordField(login.password);
        page.Enter();
    }

    public void TrySearch_by_id(String id, long time) throws InterruptedException {
        boolean state = true;
        int n = (int) (time/100);

        int count = 0;
        do {
            count++;
            try {
                device.findObject(By.res(id)).getClassName();
                state = false;
            } catch (Exception e) {
                state = true;
                if (count == n) state = false;
            }
            Thread.sleep(100);
        } while (state);
        device.findObject(By.res(id)).getClassName();
    }

    public void TrySearch_by_text(String text, long time) throws InterruptedException {
        boolean state = true;
        int n = (int) (time/100);

        int count = 0;
        do {
            count++;
            try {
                device.findObject(By.text(text)).getClassName();
                state = false;
            } catch (Exception e) {
                state = true;
                if (count == n) state = false;
            }
            Thread.sleep(100);
        } while (state);
        device.findObject(By.text(text)).getClassName();
    }

    public void Wifi(boolean status)
    {
        Context context = InstrumentationRegistry.getContext();
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(status);
    }

    public void closeApp_with_swipe() throws RemoteException, InterruptedException {
        device.pressRecentApps();//вызов диспетчера
        Thread.sleep(1500);//смотрим красивую анимацию
        device.swipe(device.getDisplayWidth()/2, device.getDisplayHeight()/2, 0, 0, 20);//свайпим закрытие приложухи
        device.pressBack();//возвращаемся на рабочий стол
    }

    public void swipe_down(int h)
    {
        device.swipe(device.getDisplayWidth()/2, device.getDisplayHeight()/2, device.getDisplayWidth()/2, 0+h, 100);
    }
    public void swipe_Up(int h)
    {
        device.swipe(device.getDisplayWidth()/2, device.getDisplayHeight()/2, device.getDisplayWidth()/2, device.getDisplayHeight(), 100);
    }
    public void swipe_right(int y) {
        device.swipe(device.getDisplayWidth() / 2, y, 30, y, 100);
    }
    public void swipe_left(int y) {
        device.swipe(device.getDisplayWidth() / 2, y, device.getDisplayWidth(), y, 100);
    }

    public void Valid_Enter() throws RemoteException, InterruptedException {
        Auth();
        toolbar();
        checkID();
    }

    private void Auth() throws InterruptedException, RemoteException {
        //создаем обвес страницы авторизации
        PageAuth auth = new PageAuth(device);
        this.TrySearch_by_id(auth.Enter_btn, 20000);
        auth.LoginField(login.login);
        auth.PasswordField(login.password);
        auth.Enter();
    }

    private void checkID() throws InterruptedException {
        //Чекаем id на submenu
        this.TrySearch_by_id("com.gsgroup.tricoloronline:id/user_id", 8000);
        String result = device.findObject(By.res("com.gsgroup.tricoloronline:id/user_id")).getText();
        Assert.assertEquals("ID: "+login.login, result);
    }
    private void toolbar() throws InterruptedException {

        Toolbar toolbar = new Toolbar(device);
        this.TrySearch_by_id(toolbar.search_icon, 15000);
        toolbar.click_on_SubMenu();
    }
    public void sign_out() throws InterruptedException {

        Toolbar toolbar = new Toolbar(device);
       // this.TrySearch_by_id(toolbar.search_icon, 15000);
        toolbar.click_on_SubMenu();
        PageAuth auth = new PageAuth(device);
        Thread.sleep(100);
        UiObject2 exit = device.findObject(By.res("com.gsgroup.tricoloronline:id/sign_out_button"));
        exit.click();
//        this.TrySearch_by_id("android:id/message", 3000);
//        device.findObject(By.res("android:id/button1")).click();
//        this.TrySearch_by_id(auth.id_field, 10000);

        Thread.sleep(3000);
    }

    public void go_to_channel (String tv_category, String tv_channel) throws InterruptedException {
        UiObject2 category = grabCategory(tv_category);//грабим категорию HD


        UiObject2 channel = null;
        do {

            scroll_right(category);//проверка скрола.
            channel = grabCategory_channels(category, tv_channel);//грабим канал

        } while(channel == null);

        channel.click();
        Thread.sleep(3000);

        String name = device.findObject(By.res("com.gsgroup.tricoloronline:id/tv_channel_name")).getText();//грабим текст с заголовка канала
        Assert.assertEquals(tv_channel, name);
    }

    private void scroll_right(UiObject2 channels)
    {
        UiObject2 channel_scroll = channels.findObject(By.res("com.gsgroup.tricoloronline:id/rv_channel_list"));
        Rect rect = channel_scroll.getVisibleBounds();
        int y = (int) rect.exactCenterY();
        this.swipe_right(y);
    }

    private UiObject2 grabCategory(String tv_category)
    {
        List<UiObject2> curent_category = device.findObject(By.res("com.gsgroup.tricoloronline:id/rv_channel_categories_list")).findObjects(By.clazz("android.widget.LinearLayout"));
        for (UiObject2 item : curent_category)
        {
            try {
                UiObject2 category_lbl = item.findObject(By.res("com.gsgroup.tricoloronline:id/tv_category_name"));
                String name = category_lbl.getText();
                if (name.equals(tv_category)){
                    return item;
                }

            } catch (Exception e) {
            }
        }
        return null;
    }
    private UiObject2 grabCategory_channels(UiObject2 category, String tv_channel)
    {
        UiObject2 scroll = category.findObject(By.res("com.gsgroup.tricoloronline:id/rv_channel_list"));//получаем объект ленты
        List<UiObject2> channels_name = scroll.findObjects(By.res("com.gsgroup.tricoloronline:id/tv_channel_name"));
        Log.e("AUTO", "Размер banners: " + Integer.toString(channels_name.size()));

        for (UiObject2 name : channels_name)
        {
            String text = name.getText();
            if (text.equals(tv_channel)) return name.getParent();
        }
        return null;
    }

}
