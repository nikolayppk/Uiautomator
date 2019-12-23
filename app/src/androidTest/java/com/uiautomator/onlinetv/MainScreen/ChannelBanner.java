package com.uiautomator.onlinetv.MainScreen;


import com.uiautomator.onlinetv.PageObject;

import java.util.HashMap;

import androidx.test.uiautomator.UiDevice;

public class ChannelBanner extends PageObject {
    //корневой родительский слой
    public String root = "android.widget.FrameLayout";//в данном случае это имя класса
    //Элементы
    public String channel_name = "com.gsgroup.tricoloronline.mobile:id/tv_channel_name";//resource_id
    public String click_banner_listener = "com.gsgroup.tricoloronline.mobile:id/v_disabled_state_view";//resource_id кликабельного баннера клик-переход
    public String image = "com.gsgroup.tricoloronline.mobile:id/iv_channel_logo";//логотип канала
    public String no_pay_no_play = "com.gsgroup.tricoloronline.mobile:id/iv_unpaid_icon";//иконка статуса не оплачено

    //Упрощенная карта связки контролов
    public HashMap<String, String> ui_controls;
    public int index;

    public ChannelBanner(UiDevice _d, int _index) {
        super(_d);
        this.ui_controls = new HashMap<String, String>();
        this.ui_controls.put("Имя канала", this.channel_name);
        this.ui_controls.put("Кликабельный баннер", this.click_banner_listener);
        this.ui_controls.put("Изображение канала", this.image);
        this.ui_controls.put("Иконка не оплачено", this.no_pay_no_play);
        this.index = _index;//индекс канала внутри карусели
    }


}
