package com.example.skinlib.bean;

import android.view.View;

import com.example.skinlib.bean.base.BaseAttr;
import com.example.skinlib.presenter.SkinLoadPresenter;
import com.example.skinlib.utils.AttrUtil;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * 底部切换栏图标颜色属性实现
 * Created by Mistletoe on 2018/07/03 12:34.
 */

public class BottomNavigationViewIconColorAttr extends BaseAttr {
    @Override
    public void apply(View view) {
        if(view instanceof BottomNavigationViewEx){
            BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx)view;
            if(AttrUtil.ITEM_ICON_TINT.equalsIgnoreCase(attrName)){
                for(int i=0;i<bottomNavigationViewEx.getItemCount();i++){
                    bottomNavigationViewEx.setIconTintList(i, SkinLoadPresenter.getInstance().convertToColorStateList(attrValue));
                }
            }
        }
    }
}
