package com.example.skinlib.bean;

import android.view.View;

import com.example.skinlib.bean.base.BaseAttr;
import com.example.skinlib.presenter.SkinLoadPresenter;
import com.example.skinlib.utils.AttrUtil;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 下拉刷新头基底颜色属性实现
 * Created by Mistletoe on 2018/07/04 16:21.
 */

public class ClassicsHeaderSrcPriCorAttr extends BaseAttr{

    @Override
    public void apply(View view) {
        if(view instanceof ClassicsHeader){
            ClassicsHeader classicsHeader=(ClassicsHeader)view;
            if(AttrUtil.SRC_PRI_COLOR.equals(attrName)){
                classicsHeader.setPrimaryColor(SkinLoadPresenter.getInstance().getColor(attrValue));
            }
        }
    }
}
