package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1.UC_ST1_MAIN;

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


//проверка категорий каналов
public class UC_ST1_MAIN_1 {

    //подсчет всех категорий
    UiDevice device;
    Login login;

    Support support;

    ArrayList<String> category;//ожидаемые категории
    private ArrayList<String> names;//фактические категории
    private HashMap<String, String> control;

    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
        //формирование списка проверяемых категорий и порядка их следования
        this.category = new ArrayList<String>();
        this.category.add("Все");
        this.category.add("Инфоканалы");
        this.category.add("Рекомендуем");
        this.category.add("HD");
        this.category.add("Общероссийские");
        this.category.add("Кино и сериалы");
        this.category.add("Телемагазины");
        this.category.add("Развлекательные");
        this.category.add("Познавательные");
        this.category.add("Детские");
        this.category.add("Спортивные");
        this.category.add("Музыкальные");
        this.category.add("Информационные");
        this.category.add("Региональные");
    }

    @Test
    public void Test() throws RemoteException, InterruptedException {
        support.RunApp();//запуск приложения
        PageAuth auth = new PageAuth(device);
        support.Valid_Enter();
        device.pressBack();
        //магия
        Action();
    }

    public void Action()
    {
        Support support = new Support(device);
        this.names = new ArrayList<String>();
        this.control = new HashMap<String, String>();

        grabCategory(names);

        for (int i = 0; i < 10; i++)
        {
            support.swipe_down(5);
            try {
                grabCategory(names);
            } catch (Exception e)
            {

            }
        }

        for (int i = 0; i < this.category.size(); i++)
        {
            Assert.assertEquals(this.category.get(i), this.names.get(i));
        }
    }

    public void grabCategory(ArrayList<String> names)
    {
        List<UiObject2> curent_category = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_categories_list")).findObjects(By.clazz("android.widget.LinearLayout"));
        for (UiObject2 item : curent_category)
        {
            try {
                UiObject2 category_lbl = item.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_category_name"));
                String name = category_lbl.getText();

                if (!control.containsKey(name))
                {
                    names.add(name);
                    control.put(name, name);
                }

            } catch (Exception e) {
            }
        }
        Log.e("AUTO", Integer.toString(names.size()));
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
