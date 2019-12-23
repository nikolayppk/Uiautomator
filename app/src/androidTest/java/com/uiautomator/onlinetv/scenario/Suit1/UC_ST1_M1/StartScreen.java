package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;


//в данном кейсе производится проверка UI стартового экрана.
public class StartScreen {

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
    public void flow() throws InterruptedException {
        this.TitlesCheck();//1
        this.login_screen_keyboard();//2
        this.password_screen_keyboard();//3
        Thread.sleep(900);
        this.enable_field();//4
    }

    public void login_screen_keyboard() throws InterruptedException {
        //в тесте проверяется вызов экранной клавиатуры после клика по полю логин.
        device.findObject(By.res(auth.id_field)).click();
        Thread.sleep(2000);
        boolean status = true;

        try {
            device.findObject(By.res(auth.EnterWithoutRegistration)).click();
            status = true;//если нашли, то истина
        } catch (Exception e) {
            status = false;//если нет, то ложь
        }
        Assert.assertEquals(false, status);
        device.pressBack();//скрываем клаву
        device.findObject(By.res(auth.EnterWithoutRegistration)).getText();//чекаем что клавиатура скрылась
    }

    public void password_screen_keyboard() throws InterruptedException {
        //в тесте проверяется вызов экранной клавиатуры после клика по полю пароля.
        device.findObject(By.res(auth.pass_field)).click();
        Thread.sleep(2000);
        boolean status = true;

        try {
            device.findObject(By.res(auth.EnterWithoutRegistration)).click();
            status = true;//если нашли, то истина
        } catch (Exception e) {
            status = false;//если нет, то ложь
        }
        Assert.assertEquals(false, status);
        device.pressBack();//скрываем клаву
        device.findObject(By.res(auth.EnterWithoutRegistration)).getText();//чекаем что клавиатура скрылась
    }

    public void TitlesCheck()
    {
        String enter_text = device.findObject(By.res(auth.lbl_enter)).getText();
        Assert.assertEquals("Просмотр телеканалов и фильмов", enter_text);

        String lbl_for_watch = device.findObject(By.res(auth.lbl_for_watch)).getText();
        Assert.assertEquals("для клиентов Триколора", lbl_for_watch);

        String lbl_abonent = device.findObject(By.res(auth.lbl_abonent)).getText();
        Assert.assertEquals("доступен только клиентам", lbl_abonent);

        String btn_enter = device.findObject(By.res(auth.Enter_btn)).getText();
        Assert.assertEquals("Войти", btn_enter);
        boolean btn_enter_status = device.findObject(By.res(auth.Enter_btn)).isEnabled();
        Assert.assertEquals(false, btn_enter_status);//чекаем то что поле в дизейбл

        String login = device.findObject(By.res(auth.id_field)).getText();
        Assert.assertEquals("Логин", login);

        String password = device.findObject(By.res(auth.pass_field)).getText();
        Assert.assertEquals("Пароль",password);

        UiObject2 sms = device.findObject(By.res(auth.getPassBySMS));
        Assert.assertEquals("Получить код СМС–сообщением", sms.getText());
        Assert.assertEquals(false, sms.isEnabled());//чекаем что поле в дизейбл

        String without_auth = device.findObject(By.res(auth.EnterWithoutRegistration)).getText();
        Assert.assertEquals("Продолжить без авторизации", without_auth);
    }

    public void enable_field()
    {
        auth.LoginField("12");

        UiObject2 sms = device.findObject(By.res(auth.getPassBySMS));
        Assert.assertEquals("Получить код СМС–сообщением", sms.getText());
        Assert.assertEquals(true, sms.isEnabled());//чекаем что поле в энейбл

        String btn_enter = device.findObject(By.res(auth.Enter_btn)).getText();
        Assert.assertEquals("Войти", btn_enter);
        UiObject2 btn_enter_status = device.findObject(By.res(auth.Enter_btn));
        Assert.assertEquals(false, btn_enter_status.isEnabled());//чекаем то что поле в дизейбл
        auth.PasswordField("12");
        Assert.assertEquals(true, btn_enter_status.isEnabled());//чекаем энейбл


    }
    @After
    public void after() throws RemoteException, InterruptedException {
        support.closeApp_with_swipe();//закрываем приложение.
    }
}
