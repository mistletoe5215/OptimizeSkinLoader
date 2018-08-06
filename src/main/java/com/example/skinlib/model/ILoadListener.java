package com.example.skinlib.model;

/**
 * 资源加载监听
 * Created by Mistletoe on 2018/07/02 10:59.
 */

public interface ILoadListener {
    void onStart(); //开始加载回调
    void onSuccess();//加载成功回调
    void onFailed();//加载失败回调
}
