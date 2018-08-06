package com.example.skinlib.bean;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import com.example.skinlib.presenter.SkinLoadPresenter;
import com.example.skinlib.bean.base.BaseAttr;

/**
 * 背景属性实现
 * Created by Mistletoe on 2018/07/02 14:06.
 */

public class BackgroundAttr extends BaseAttr{
    @Override
    public void apply(View view) {
        if("color".equals(entryType)){
            view.setBackgroundColor(SkinLoadPresenter.getInstance().getColor(attrValue));
        }else if("drawable".equals(entryType)){
            Drawable bg = SkinLoadPresenter.getInstance().getDrawable(attrValue);
            view.setBackground(bg);
        }
    }
}
