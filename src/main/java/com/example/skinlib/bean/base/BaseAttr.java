package com.example.skinlib.bean.base;

import android.view.View;

/**
 * 属性抽象基类
 * Created by Mistletoe on 2018/06/29 15:08.
 */

public abstract  class BaseAttr {
    public String attrName;//属性名
    public int attrValue;//属性值
    public String entryName;
    public String entryType;
    public abstract void apply(View view);//视图刷新调用
}
