package com.uiautomator.onlinetv.MainScreen;


import com.uiautomator.onlinetv.PageObject;

import java.util.HashMap;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;


public class Toolbar extends PageObject {
    //Панель инструментов
    public String root = "com.gsgroup.tricoloronline:id/toolbar";//корневой слой панели
    //элементы слоя по индексам
    private int index_SubMenu = 0;//Можно использовать также локатор индекса по root корню. Сейчас применяется локатор content-desk
    private int index_Title = 1;//Заголовок экрана Триколор Онлайн ТВ
    private int index_icon_group = 2;//элемент разметки иконок локатор индекс
    //Элементы слоя по более понятным уникальным локаторам, кторые не привязаны к root разметке
    public String SubMenu = "Открыть меню";//локатор content-desk
    public String Title = "Триколор Онлайн ТВ";//локатор просто текст text
    public String like_icon = "com.gsgroup.tricoloronline:id/action_menu_favorite";//локатор resource_id
    public String search_icon = "com.gsgroup.tricoloronline:id/action_menu_search";//локатор resource_id
    public String view_icon = "com.gsgroup.tricoloronline:id/action_menu_view_mode";//локатор resource_id

    //простой hashmap
    //Упрощенная карта связки контролов
    public HashMap<String, String> ui_controls;


    public Toolbar(UiDevice _d) {
        super(_d);
        this.ui_controls = new HashMap<String, String>();
        ui_controls.put("Вызов подменю", this.SubMenu);//desk
        ui_controls.put("Триколор Онлайн ТВ", this.Title);//text
        ui_controls.put("Иконка лайка", this.like_icon);//resource_id
        ui_controls.put("Иконка поиска", this.search_icon);//resource_id
        ui_controls.put("Иконка смена вида категорий", this.view_icon);//resource_id
    }

    public  void click_on_SubMenu(){
        device.findObject(By.res(root)).getChildren().get(this.index_SubMenu).click();
    }
    public String get_title()
    {
        String result =  device.findObject(By.res(root)).getChildren().get(this.index_Title).getText();
        return result;
    }
    public void click_on_icon_changeView()
    {
        device.findObject(By.res(root)).getChildren().get(this.index_icon_group).findObject(By.res(this.view_icon)).click();
    }
    public void click_on_icon_like()
    {
        device.findObject(By.res(root)).getChildren().get(this.index_icon_group).findObject(By.res(this.like_icon)).click();
    }
    public void click_on_icon_search()
    {
        device.findObject(By.res(root)).getChildren().get(this.index_icon_group).findObject(By.res(this.search_icon)).click();
    }


}
