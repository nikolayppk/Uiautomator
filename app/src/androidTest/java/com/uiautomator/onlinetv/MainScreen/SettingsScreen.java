package com.uiautomator.onlinetv.MainScreen;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;

public class SettingsScreen {

    public UiObject2 menu_button;//кнопка подменю
    public UiObject2 Title_screen;//заголовок экрана

    public UiObject2 Title_internet_perm;//заголовок переключателя управления интернетом
    public UiObject2 switch_internet_perm;//переключатель переключения доступа к мобильному интернету

    public UiObject2 Title_parent_rate;//заголовок переключателя родительского контроля
    public UiObject2 switc_parent_rate;//переключатель активации родительского контроля

    public UiObject2 version_text;//версия приложения
    public UiObject2 link_text;//кликабельная ссылка на группу ВК

    UiDevice device;

    public SettingsScreen(UiDevice device)
    {
        this.device = device;//инициализация
        this.menu_button = device.findObject(By.desc("Открыть меню"));
        this.Title_screen = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/drawer_layout")).
                getChildren().get(0).getChildren().
                get(0).getChildren().get(0).getChildren().get(1);
        this.Title_internet_perm = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/cl_settings_cellular")).getChildren().get(0);
        this.switch_internet_perm = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/cl_settings_cellular")).getChildren().get(1);

        this.Title_parent_rate = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/cl_settings_parent_control")).getChildren().get(0);
        this.switc_parent_rate = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/cl_settings_parent_control")).getChildren().get(1);

        this.version_text = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_app_version"));
        this.link_text = device.findObject(By.res("com.gsgroup.tricoloronline.mobile:id/tv_vk_link"));

    }


}
