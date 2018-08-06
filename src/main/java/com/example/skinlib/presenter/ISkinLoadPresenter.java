package com.example.skinlib.presenter;

import com.example.skinlib.view.ISkinUpdate;

/**
 * 皮肤加载关联接口
 * Created by Mistletoe on 2018/07/02 11:36.
 */

public interface ISkinLoadPresenter {
    void attach(ISkinUpdate observer);// 绑定需要更新皮肤视图
    void detach(ISkinUpdate observer);// 解绑需要更新皮肤视图
    void notifySkinUpdate();// 通知所有需要更新皮肤的视图做出更新回调
}
