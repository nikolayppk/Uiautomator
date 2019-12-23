package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.MainScreen.MainMenu_auth;
import com.uiautomator.onlinetv.MainScreen.Toolbar;
import com.uiautomator.onlinetv.SettingsScreen;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

public class UC_Settings_parent_Off {

    UiDevice device;
    Login login;

    Support support;
    private SettingsScreen settings;


    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
        this.settings = new SettingsScreen(device);
    }


    @Test
    public void flow() throws RemoteException, InterruptedException {
        support.RunApp();//запуск приложения
        support.Valid_Enter();//авторизуемся
        device.pressBack();//скрываем шторку
        Thread.sleep(1500);

        Toolbar toolbar = new Toolbar(device);
        toolbar.click_on_SubMenu();

        MainMenu_auth menu = new MainMenu_auth(device);
        support.TrySearch_by_id(menu.user_id, 3000);//отработка анимации
        menu.Settings_click();


        support.TrySearch_by_id(settings.app_version_lbl, 3000);// убеждаемся что мы в нужном месте

        String parent_text_exp = "Родительский контроль";
        String actual_parent_text_title = settings.getTitle_parent_rate_permission();
        Assert.assertEquals(parent_text_exp, actual_parent_text_title);

        String state_par = settings.getParent_rate_permission();
        Assert.assertEquals("Активировать пин-код для просмотра контента 18+ ОТКЛ.",state_par);

        //Активируем пин код защиту
        settings.click_Parent_rate_permission();
        this.Activate_DialogChecker();
        Thread.sleep(1000);
        state_par = settings.getParent_rate_permission();
        Assert.assertEquals("Деактивировать пин-код для просмотра контента 18+ ВКЛ.", state_par);

        //Старт процедуры снятия пин-кода.

        //Проверка работоспособности кнопки Отмена.
        settings.click_Parent_rate_permission();
        Abort_Deactivate_DialogChecker();
        Thread.sleep(1000);
        state_par = settings.getParent_rate_permission();
        Assert.assertEquals("Деактивировать пин-код для просмотра контента 18+ ВКЛ.", state_par);

        //Проверка вторичного диалога отмены  при неверном коде
        settings.click_Parent_rate_permission();
        Second_Try_Deactivate_DialogChecker();


    }

    public void Activate_DialogChecker() throws InterruptedException {

        support.TrySearch_by_text("Задайте пин-код", 3000);
        device.findObject(By.text("Задайте пин-код")).getText();

        UiObject2 btn_abort = device.findObject(By.text("Отмена"));
        String btn_abort_class = btn_abort.getClassName();
        Assert.assertEquals("android.widget.Button", btn_abort_class);
        Assert.assertEquals(true, btn_abort.isEnabled());

        UiObject2 btn_ok = device.findObject(By.text("OK"));
        String btn_ok_class = btn_ok.getClassName();
        Assert.assertEquals("android.widget.Button", btn_ok_class);
        Assert.assertEquals(false, btn_ok.isEnabled());

        UiObject2 input_pin = device.findObject(By.text("Пин-код"));
        input_pin.setText("1234");
        Thread.sleep(1000);
        boolean focus = input_pin.isFocused();
        Assert.assertEquals(false, focus);
        Assert.assertEquals(false, btn_ok.isEnabled());

        UiObject2 input_pin2 = device.findObject(By.text("Повтор пин-кода"));
        input_pin2.setText("1111");
        Assert.assertEquals(false, btn_ok.isEnabled());
        device.findObject(By.text("Пин-коды не совпадают")).getText();

        input_pin.clear();
        input_pin.setText("1111");
        Assert.assertEquals(true, btn_ok.isEnabled());
        btn_ok.click();
    }

    public void Abort_Deactivate_DialogChecker()
    {
        UiObject2 title_parent_diact = device.findObject(By.text("Введите пин-код для подтверждения"));


        UiObject2 input = device.findObject(By.text("Пин-код"));
        UiObject2 d_btn_abort = device.findObject(By.text("Отмена"));
        Assert.assertEquals(true, d_btn_abort.isEnabled());

        UiObject2 d_btn_ok = device.findObject(By.text("OK"));
        Assert.assertEquals(false, d_btn_ok.isEnabled());

        //чекблок
        input.setText("1");
        Assert.assertEquals(false, d_btn_ok.isEnabled());
        Assert.assertEquals(true, d_btn_abort.isEnabled());
        input.clear();
        input.setText("1111");
        Assert.assertEquals(true, d_btn_ok.isEnabled());
        Assert.assertEquals(true, d_btn_abort.isEnabled());
        d_btn_abort.click();
    }


    public void Second_Try_Deactivate_DialogChecker() throws InterruptedException {
        FailDiactPin();//Процедура вызова второго диалога
        //появление диалога №2
        //Настоящая цель сего действа
        UiObject2 title = device.findObject(By.text("Введён неверный пин-код"));
        UiObject2 message = device.findObject(By.text("Введите еще раз или сбросьте пин-код. При сбросе будет выполнен выход из аккаунта"));
        UiObject2 input_d = device.findObject(By.text("Пин-код"));

        UiObject2 btn_ok = device.findObject(By.text("OK"));
        Assert.assertEquals(false, btn_ok.isEnabled());

        UiObject2 btn_abort = device.findObject(By.text("Отмена"));
        Assert.assertEquals(true, btn_abort.isEnabled());

        UiObject2 btn_sbros = device.findObject(By.text("Сбросить"));
        Assert.assertEquals(true, btn_sbros.isEnabled());

        //Проверка второго ввода неверных данных
        input_d.setText("5555");
        Assert.assertEquals(true, btn_ok.isEnabled());
        btn_ok.click();
        Thread.sleep(500);
        //Снова мапим диалог
        SecondDiactDialog();
        input_d.setText("1111");
        Assert.assertEquals(true, btn_ok.isEnabled());
        btn_abort.click();
        //Диалог полностью закрывается. Видим просто UI экранна настройки.
        support.TrySearch_by_id(settings.app_version_lbl,4000);
        settings.click_Parent_rate_permission();//Снова вызываем процедуру деактивации
        Thread.sleep(800);
        FailDiactPin();//снова вызываем второй диалог диактивации
        Thread.sleep(800);

        title = device.findObject(By.text("Введён неверный пин-код"));
        message = device.findObject(By.text("Введите еще раз или сбросьте пин-код. При сбросе будет выполнен выход из аккаунта"));
        input_d = device.findObject(By.text("Пин-код"));

        btn_sbros = device.findObject(By.text("Сбросить"));
        Assert.assertEquals(true, btn_sbros.isEnabled());

        btn_sbros.click();//Сброс пин-кода
    }


    public void SecondDiactDialog()
    {
        UiObject2 title = device.findObject(By.text("Введён неверный пин-код"));
        UiObject2 message = device.findObject(By.text("Введите еще раз или сбросьте пин-код. При сбросе будет выполнен выход из аккаунта"));
        UiObject2 input_d = device.findObject(By.text("Пин-код"));

        UiObject2 btn_ok = device.findObject(By.text("OK"));
        Assert.assertEquals(false, btn_ok.isEnabled());

        UiObject2 btn_abort = device.findObject(By.text("Отмена"));
        Assert.assertEquals(true, btn_abort.isEnabled());

        UiObject2 btn_sbros = device.findObject(By.text("Сбросить"));
        Assert.assertEquals(true, btn_sbros.isEnabled());
    }

    public void FailDiactPin() throws InterruptedException {
        UiObject2 title_parent_diact = device.findObject(By.text("Введите пин-код для подтверждения"));


        UiObject2 input = device.findObject(By.text("Пин-код"));
        UiObject2 d_btn_abort = device.findObject(By.text("Отмена"));
        Assert.assertEquals(true, d_btn_abort.isEnabled());

        UiObject2 d_btn_ok = device.findObject(By.text("OK"));
        Assert.assertEquals(false, d_btn_ok.isEnabled());

        //чекблок
        input.setText("1");
        Assert.assertEquals(false, d_btn_ok.isEnabled());
        Assert.assertEquals(true, d_btn_abort.isEnabled());
        input.clear();
        input.setText("2222");
        Assert.assertEquals(true, d_btn_ok.isEnabled());
        Assert.assertEquals(true, d_btn_abort.isEnabled());
        d_btn_ok.click();
        Thread.sleep(800);
    }

    @After
    public void closeApp() throws RemoteException, InterruptedException {
        //закрываем приложение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }


}
