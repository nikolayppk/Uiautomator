package com.uiautomator.onlinetv.MainScreen;


import com.uiautomator.onlinetv.PageObject;

import androidx.test.uiautomator.UiDevice;

public class SubMenu extends PageObject {

    public String main_screen = "Главный экран";
    public String timers = "Таймеры";
    public String settings = "Настройки";


    public SubMenu(UiDevice _d) {
        super(_d);
    }
}
