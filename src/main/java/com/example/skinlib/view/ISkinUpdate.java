package com.example.skinlib.view;

/**
 * 皮肤更新监听,需要对应的Activity或Fragment去继承
 * Created by Mistletoe on 2018/07/02 11:31.
 */
public interface ISkinUpdate {
    void onThemeUpdate(); //通知页面更新主题回调
}
