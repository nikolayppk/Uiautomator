package com.uiautomator.onlinetv.scenario.Suit1.UC_ST1_M1;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;

import com.uiautomator.onlinetv.Dialogs.FailAuthDialog;
import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;


//кейс фейл авторизации по валидному логину но невалидному паролю.
public class UC_ST1_AC3_2 {

    UiDevice device;
    Login login;

    Support support;

    @Before
    public void init()
    {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());//просто надо и все тут!
        this.support = new Support(device);
        this.login = new Login();
    }

    @Test
    public void Test() throws InterruptedException {
        support.RunApp();
        PageAuth auth = new PageAuth(device);
        support.TrySearch_by_id(auth.Enter_btn, 10000);

        auth.LoginField(login.login);
        auth.PasswordField("12345678");
        auth.Enter();

        FailAuthDialog fail = new FailAuthDialog(device);
        support.TrySearch_by_id(fail.without_authorisation,10000);

        String message = device.findObject(By.res(fail.message)).getText();
        String try_again_lbl = device.findObject(By.res(fail.try_again)).getText();
        String without_auth = device.findObject(By.res(fail.without_authorisation)).getText();

        Assert.assertEquals("Неверный логин или пароль (ошибка 42).",message);
        Assert.assertEquals("Попробовать еще раз",try_again_lbl);
        Assert.assertEquals("Продолжить без авторизации",without_auth);

        device.findObject(By.res(fail.try_again)).click();
        support.TrySearch_by_id(auth.Enter_btn, 8000);

    }


    private void closeApp() throws RemoteException, InterruptedException {
        //закрываем прилоение и чистим его из памяти девайса
        device.pressHome();
        support.closeApp_with_swipe();
    }

    @After
    public void after() throws InterruptedException, RemoteException {
        closeApp();
    }
}

