package com.uiautomator.onlinetv.Demo;


import android.support.test.InstrumentationRegistry;

import androidx.test.uiautomator.UiDevice;

import com.uiautomator.onlinetv.PageAuth;
import com.uiautomator.onlinetv.support.Login;
import com.uiautomator.onlinetv.support.Support;

import org.junit.Before;
import org.junit.Test;

class TestText {
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
        public void testText() {








        }


    }
}




