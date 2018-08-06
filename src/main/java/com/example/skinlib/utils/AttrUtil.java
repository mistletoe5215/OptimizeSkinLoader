package com.example.skinlib.utils;

import com.example.skinlib.bean.BackgroundAttr;
import com.example.skinlib.bean.BottomNavigationViewIconColorAttr;
import com.example.skinlib.bean.BottomNavigationViewTextColorAttr;
import com.example.skinlib.bean.ClassicsHeaderSrcPriCorAttr;
import com.example.skinlib.bean.ImageAttr;
import com.example.skinlib.bean.TextAttr;
import com.example.skinlib.bean.TextColorAttr;
import com.example.skinlib.bean.base.BaseAttr;

/**
 * 属性判别工具
 * Created by Mistletoe on 2018/07/02 13:59.
 */

public class AttrUtil {

    public  static final String BACKGROUND = "background";
    public static final String TEXT_COLOR = "textColor";
    public static final String TEXT_VALUE = "text";
    public static final String IMAGE_SRC  = "src";
    public static final String ITEM_ICON_TINT ="itemIconTint";
    public static final String ITEM_TEXT_COLOR ="itemTextColor";
    public static final String SRC_PRI_COLOR ="srlPrimaryColor";

    /**
     * 根据不同的属性名称创建不同的属性实例对象
     * @param attrName 属性名
     * @param attrValueRefId 属性值
     * @param attrValueRefName 属性值名称
     * @param typeName 属性值类型
     * @return 属性实例对象
     */

    public static BaseAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName){

        BaseAttr mSkinAttr ;
        if(BACKGROUND.equals(attrName)){
            mSkinAttr = new BackgroundAttr();
        }else if(TEXT_COLOR.equals(attrName)){
            mSkinAttr = new TextColorAttr();
        }else if(TEXT_VALUE.equals(attrName)){
            mSkinAttr = new TextAttr();
        }else if(IMAGE_SRC.equals(attrName)){
            mSkinAttr =  new ImageAttr();
        } else if (ITEM_ICON_TINT.equals(attrName)) {
            mSkinAttr = new BottomNavigationViewIconColorAttr();
        } else if(ITEM_TEXT_COLOR.equals(attrName)){
            mSkinAttr = new BottomNavigationViewTextColorAttr();
        } else if(SRC_PRI_COLOR.equals(attrName)){
            mSkinAttr = new ClassicsHeaderSrcPriCorAttr();
        }else {
            return null;
        }
        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValue = attrValueRefId;
        mSkinAttr.entryName = attrValueRefName;
        mSkinAttr.entryType = typeName;
        return mSkinAttr;
    }

    /**
     * 检查当前属性是否支持替换
     * @param attrName 属性名
     * @return true: 支持 false: 不支持
     */
    public static boolean isSupportedAttr(String attrName){
        if(BACKGROUND.equals(attrName)){
            return true;
        }
        if(TEXT_COLOR.equals(attrName)){
            return true;
        }
        if(TEXT_VALUE.equals(attrName)) {
            return true;
        }
        if(IMAGE_SRC.equals(attrName)) {
            return true;
        }
        if(ITEM_TEXT_COLOR.equals(attrName)){
            return true;
        }
        if(ITEM_ICON_TINT.equals(attrName)){
            return true;
        }
        if(SRC_PRI_COLOR.equals(attrName)){
            return true;
        }
        return false;
    }
}
