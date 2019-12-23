package com.uiautomator.onlinetv;

import java.util.HashMap;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

//экран авторизации
public class PageAuth extends PageObject {

    //Блок описания локаторов контролов
    public String id_field = "com.gsgroup.tricoloronline:id/authLogin";//resource_id
    public String pass_field = "com.gsgroup.tricoloronline:id/authPass";//resource_id
    public String Enter_btn = "com.gsgroup.tricoloronline:id/startAuth";//resource_id
    public String getPassBySMS = "com.gsgroup.tricoloronline:id/getPassBySMS";//resource_id
    public String EnterWithoutRegistration = "com.gsgroup.tricoloronline:id/authContinueWithoutAuthorisation";//resource_id

    //Titles, labels
    public String lbl_enter = "com.gsgroup.tricoloronline:id/tv_title_login";//надпись ВХОД на экране.
    public String lbl_abonent = "com.gsgroup.tricoloronline:id/tv_subtitle_part2";//надпись для абонентов.
    public String lbl_for_watch = "com.gsgroup.tricoloronline:id/tv_login_subtitle2";//для просмотра...



    //Упрощенная карта связки контролов
    public HashMap<String, String> ui_controls;


    public PageAuth(UiDevice _d) {
        super(_d);//конструктор
        this.ui_controls = new HashMap<String, String>();

        ui_controls.put("Поле логина",this.id_field);
        ui_controls.put("Поле пароля",this.pass_field);
        ui_controls.put("Войти", this.Enter_btn);
        ui_controls.put("Получить код SMS", this.getPassBySMS);
        ui_controls.put("Продолжить без авторизации", this.EnterWithoutRegistration);

    }

    public void LoginField(String login)
    {
        //поле логина
        this.device.findObject(By.res(id_field)).setText(login);
    }
    public  void PasswordField(String password)
    {
        //поле пароля
        this.device.findObject(By.res(pass_field)).setText(password);
    }
    public void Enter()
    {
        //кнопка Войти
        this.device.findObject(By.res(Enter_btn)).click();
    }
    public void EnterWithoutAutho()
    {
        //текстовая ссылка войти без авторизации
        this.device.findObject(By.res(EnterWithoutRegistration)).click();
    }
}
