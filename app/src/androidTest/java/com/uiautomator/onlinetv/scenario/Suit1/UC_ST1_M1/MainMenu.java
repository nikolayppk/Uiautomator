package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;


import com.uiautomator.onlinetv.MainScreen.MainMenu_auth;
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

public class MainMenu {
    UiDevice device;
    Login login;

    Support support;
    PageAuth auth;

    @Before
    public void init() throws InterruptedException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
        support.RunApp();//старт приложения.
        this.auth = new PageAuth(device);
        support.TrySearch_by_id(auth.Enter_btn, 8000);
    }

    @Test
    public void flow() throws RemoteException, InterruptedException {
        support.Valid_Enter();//осуществляем корректный вход в приложение.
        device.pressBack();//скрываем шторку

        main_menu_ui();//тест ui подменю.
        navigation_test();//опрос навигации по подменю
    }

    public void main_menu_ui() throws InterruptedException {
        Toolbar toolbar = new Toolbar(device);//тулбар
        toolbar.click_on_SubMenu();//Вызываем подменю.

        MainMenu_auth menu = new MainMenu_auth(device);
        support.TrySearch_by_id(menu.user_id,9000);

        String current_id = menu.getUserID();//получаем текущий id, который светится.
        Assert.assertEquals("ID: " + login.login, current_id);//сравниваем с ожидаемым.

        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName(); //получаем имя устройства - блютуз должен быть включен на МУ
        String model = Build.BOARD;//получаем модель устройства
        String manufacture = Build.MANUFACTURER.toUpperCase();//получаем производителя.
        String device_name = "Устройство: " + deviceName;
        String current_device_name = menu.getDevice_name();//получаем текущее имя устройства из ui.
        Assert.assertEquals(device_name, current_device_name);//сравниваем с тем, что ожидаем.

        String btn_sign_out = device.findObject(By.res("com.gsgroup.tricoloronline:id/sign_out_button")).getClassName();
        Assert.assertEquals("android.widget.ImageButton", btn_sign_out);

        //проверяем состав подменю. Там должно быть 4 пункта
        List<UiObject2> sub_menu = device.findObject(By.res("com.gsgroup.tricoloronline:id/drawer_list")).findObjects(By.res("com.gsgroup.tricoloronline:id/drawer_item_layout"));
        Assert.assertEquals(3, sub_menu.size());//проверяем что в подменю три блока

        HashMap<String, String> items = new HashMap<>();
        items.put("Главный экран", "Главный экран");
        items.put("Таймеры", "Таймеры");
        items.put("Настройки", "Настройки");

        for (UiObject2 item :  sub_menu)
        {
            List<UiObject2> children = item.getChildren();
            item.findObject(By.res("com.gsgroup.tricoloronline:id/drawer_item_image_view")).getClassName();
            String name = item.findObject(By.res("com.gsgroup.tricoloronline:id/drawer_item_text_view")).getText();
            boolean status = items.containsKey(name);
            Assert.assertEquals(true, status);
        }

        device.pressBack();
    }

    public void navigation_test() throws InterruptedException {

        Toolbar toolbar = new Toolbar(device);
        toolbar.click_on_SubMenu();
        Thread.sleep(3000);

        MainMenu_auth menu = new MainMenu_auth(device);

        device.findObject(By.text(menu.Settings)).getParent().click();
        Thread.sleep(3000);
        support.TrySearch_by_id("com.gsgroup.tricoloronline:id/text", 3000);
        device.findObject(By.res("com.gsgroup.tricoloronline:id/text")).getText();//чекаем что это настройки. Элемент уникальный

        toolbar.click_on_SubMenu();
        Thread.sleep(3000);
        device.findObject(By.text(menu.Timers)).getParent().click();
        support.TrySearch_by_text("Нет запланированных таймеров", 3000);//чекаем, что это таймеры. В них не должно быть таймеров на текущий момент.

        toolbar.click_on_SubMenu();
        Thread.sleep(3000);
        device.findObject(By.text(menu.MainScreen)).getParent().click();
        support.TrySearch_by_id("com.gsgroup.tricoloronline:id/rv_channel_categories_list", 3000);

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
