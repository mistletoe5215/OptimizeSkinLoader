package com.example.skinlib.bean;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.example.skinlib.bean.base.BaseAttr;

/**
 * 皮肤视图类
 * view 当前视图对象
 * attrs 视图对象的属性集合
 * Created by Mistletoe on 2018/06/29 15:17.
 */

public class SkinView {
    public View view;
    public List<BaseAttr> attrs;
    public void apply(){
        if(null!=view&& null!=attrs){
            for(BaseAttr attr:attrs){
                attr.apply(view);
            }
        }
    }
    public SkinView(){
        attrs = new ArrayList<>();
    }
    public void clean(){
        if(attrs.isEmpty()){
            return;
        }
        for(BaseAttr baseAttr : attrs){
            baseAttr = null;
        }
    }
}
