package com.example.skinlib.bean;

import android.view.View;
import android.widget.TextView;

import com.example.skinlib.presenter.SkinLoadPresenter;
import com.example.skinlib.bean.base.BaseAttr;
import com.example.skinlib.utils.AttrUtil;

/**
 * 文本框文字属性实现
 * Created by Mistletoe on 2018/07/02 14:23.
 */

public class TextAttr  extends BaseAttr {
    @Override
    public void apply(View view) {
        if(view instanceof TextView){
            TextView textView=( TextView)view;
            if(AttrUtil.TEXT_VALUE.equalsIgnoreCase(attrName)){
                textView.setText(SkinLoadPresenter.getInstance().getTextString(attrValue));
            }
        }
    }
}
