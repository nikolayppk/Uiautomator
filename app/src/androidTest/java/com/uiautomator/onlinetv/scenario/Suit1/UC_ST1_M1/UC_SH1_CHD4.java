package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.uiautomator.onlinetv.EpgCard;
import com.uiautomator.onlinetv.MainScreen.MainMenu_auth;
import com.uiautomator.onlinetv.MainScreen.Toolbar;
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

//установка таймеров напоминаний
public class UC_SH1_CHD4 {

    UiDevice device;
    Login login;
    Support support;

    //Перечень лайк каналов
    public String tv_channel_1 = "FAN HD";//Канал 1
    public String tv_channel_2 = "Кино ТВ HD";//Канал 2
    public String tv_category = "HD";//категория

    private String event_name_channel_1;//запоминаем имя события таймера_1
    private String event_name_channel_2;//запоминаем имя события таймера_2


    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
    }

    @Test
    public void flow() throws RemoteException, InterruptedException {
        support.RunApp();//запуск приложения
        support.Valid_Enter();//авторизуемся
        device.pressBack();//скрываем шторку
        Thread.sleep(1500);//ждем конца обработки
        //теперь перед нами главный экран

        //Ставим лайк на канал FAN HD
        support.swipe_down(5);
        add_channel_to_favorite(this.tv_channel_1);//помещаем канал в фавориты
        //перед нами экран подробного описания канала FAN HD
        moreAboutChannel channel_screen = new moreAboutChannel();
        device.findObject(By.res(channel_screen.epg_expand)).click();//скрываем подробное описание канала

        List<UiObject2> visible_epg = visible_epg();//получаем все видимое
        int goal = visible_epg.size()-1;//берем последний индекс
        UiObject2 epg_event = visible_epg.get(goal);//получаем ласт объект
        epg_event.click();//кликаем на ивент

        EpgCard card_event = new EpgCard();//карточка ивента
        support.TrySearch_by_id(card_event.action, 5000);

        this.event_name_channel_1 = device.findObject(By.res(card_event.title)).getText();//запоминаем имя события для таймера


        device.findObject(By.res(card_event.action)).click();//добавляем таймер
        Thread.sleep(500);

        device.pressBack();//скрываем карточку передачи
        Thread.sleep(800);//отработка анимации
        device.findObject(By.desc("Перейти вверх")).click();
        Thread.sleep(2000);

        //перед нами главный экран
        go_to_channel(tv_channel_2);//топаем в канал 2
        channel_screen = new moreAboutChannel();
        device.findObject(By.res(channel_screen.epg_expand)).click();//скрываем подробное описание канала

        visible_epg = visible_epg();//получаем все видимое
        goal = visible_epg.size()-1;//берем последний индекс
        epg_event = visible_epg.get(goal);//получаем ласт объект
        epg_event.click();//кликаем на ивент

        card_event = new EpgCard();//карточка ивента
        support.TrySearch_by_id(card_event.action, 5000);
        this.event_name_channel_2 = device.findObject(By.res(card_event.title)).getText();
        device.findObject(By.res(card_event.action)).click();//добавляем таймер
        Thread.sleep(500);
        device.pressBack();
        Thread.sleep(800);//отработка анимации
        device.findObject(By.desc("Перейти вверх")).click();
        Thread.sleep(2000);
        //Перед нами главный экран приложения

        Toolbar toolbar = new Toolbar(device);
        toolbar.click_on_SubMenu();
        MainMenu_auth menu = new MainMenu_auth(device);
        support.TrySearch_by_id(menu.user_id, 5000);
        menu.Timers_click();//переходим в экран таймеров


        //Блок проверки UI таймера



        Log.e("AUTO", this.event_name_channel_1);
        Log.e("AUTO", this.event_name_channel_2);

        Thread.sleep(1500);
        toolbar.click_on_icon_like();
        Thread.sleep(600);

        checkTimer(tv_channel_1, event_name_channel_1);
        //удаляем таймер
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/delete_timer")).click();
        Thread.sleep(900);
        device.findObject(By.res("android:id/button1")).click();
        Thread.sleep(500);
        toolbar.click_on_icon_like();
        Thread.sleep(500);

        checkTimer(tv_channel_2, event_name_channel_2);
        //удаляем таймер
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/delete_timer")).click();
        Thread.sleep(900);
        device.findObject(By.res("android:id/button1")).click();
        Thread.sleep(500);
        toolbar.click_on_icon_like();
        Thread.sleep(500);

        toolbar.click_on_SubMenu();
        support.TrySearch_by_id(menu.user_id, 3000);
        menu.MainScreen_click();
        Thread.sleep(1000);
        //Перед нами главный экран

        //снимаем лайк с канала
        toolbar.click_on_icon_search();
        support.TrySearch_by_id("com.gsgroup.tricoloronline.mobile:id/search_src_text", 3000);
        device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/search_src_text")).setText(this.tv_channel_1);
        Thread.sleep(600);
        device.pressBack();
        UiObject2 clck_chan = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name")).getParent();
        clck_chan.click();//входим в канал
        Thread.sleep(800);
        toolbar.click_on_icon_like();//снимаем лайк
        device.findObject(By.desc("Перейти вверх")).click();
        Thread.sleep(700);
        device.findObject(By.desc("Свернуть")).click();
        Thread.sleep(1500);
    }

    private void checkTimer(String exp_channel, String exp_epg)
    {
        String actual_channel_name = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/channel_name")).getText();//грабим текст имени канала в ui
        String actual_epg_event = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tvName")).getText();//грабим текст ивента epg
        Assert.assertEquals(exp_channel, actual_channel_name);
        exp_epg = exp_epg.substring(6, exp_epg.length());
        Assert.assertEquals(exp_epg, actual_epg_event);
    }

    private void add_channel_to_favorite(String tv_channel_1) throws InterruptedException {
        UiObject2 category = grabCategory();//грабим категорию HD
        UiObject2 channel = null;
        do {

            scroll_right(category);//проверка скрола.
            channel = grabCategory_channels(category, tv_channel_1);//грабим канал

        } while(channel == null);

        channel.click();
        AboutChannel(tv_channel_1);
    }

    private void go_to_channel(String tv_channel_2) throws InterruptedException {
        UiObject2 category = grabCategory();//грабим категорию HD
        UiObject2 channel = null;
        do {

            scroll_right(category);//проверка скрола.
            channel = grabCategory_channels(category, tv_channel_2);//грабим канал

        } while(channel == null);

        channel.click();

        moreAboutChannel screen = new moreAboutChannel();
        support.TrySearch_by_id(screen.channel_name, 5000);
        String name = device.findObject(By.res(screen.channel_name)).getText();
        Assert.assertEquals(tv_channel_2, name);
    }

    private void AboutChannel(String channel_name) throws InterruptedException {
        moreAboutChannel screen = new moreAboutChannel();
        support.TrySearch_by_id(screen.channel_name, 5000);
        String name = device.findObject(By.res(screen.channel_name)).getText();
        Assert.assertEquals(channel_name, name);

        device.findObject(By.res(screen.like_icon)).click();//помещаем канал в понравившийся

    }

    private UiObject2 grabCategory_channels(UiObject2 category, String channel)
    {
        UiObject2 scroll = category.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_channel_list"));//получаем объект ленты
        List<UiObject2> channels_name = scroll.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_channel_name"));
        Log.e("AUTO", "Размер banners: " + Integer.toString(channels_name.size()));

        for (UiObject2 name : channels_name)
        {
            String text = name.getText();
            if (text.equals(channel)) return name.getParent();
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


    public List<UiObject2> visible_epg()
    {
        //способ получения видимого epg на странице
        ArrayList<String> epg = new ArrayList<String>();

        UiObject2 epg_list = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/rv_epg_group_list"));// получаем епг лист
        List<UiObject2> epg_events = epg_list.findObjects(By.res("com.gsgroup.tricoloronline.mobile:id/tv_epg_description"));//получаем все видимые ивенты.

        return epg_events;
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
