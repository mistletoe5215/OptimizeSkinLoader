package com.example.skinlib.bean;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import com.example.skinlib.presenter.SkinLoadPresenter;
import com.example.skinlib.bean.base.BaseAttr;
import com.example.skinlib.utils.AttrUtil;

/**
 * 文本框文字颜色属性
 * Created by Mistletoe on 2018/07/02 14:58.
 */

public class TextColorAttr extends BaseAttr {
    @Override
    public void apply(View view) {
        if(view instanceof TextView){
            TextView textView=( TextView)view;
            if(AttrUtil.TEXT_COLOR.equalsIgnoreCase(attrName)){
                textView.setTextColor(SkinLoadPresenter.getInstance().getColor(attrValue));
            }
        }
        if(view instanceof AppCompatTextView){
            AppCompatTextView appCompatTextView=(AppCompatTextView)view;
            appCompatTextView.setTextColor(SkinLoadPresenter.getInstance().getColor(attrValue));
        }
    }
}
