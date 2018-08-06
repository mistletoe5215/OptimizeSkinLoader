package com.example.skinlib.model;

import android.content.Context;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.skinlib.presenter.SkinLoadPresenter;
import com.example.skinlib.bean.SkinView;
import com.example.skinlib.bean.base.BaseAttr;
import com.example.skinlib.utils.AttrUtil;
import com.example.skinlib.config.SkinConfig;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * 皮肤工厂，继承LayoutInflater.Factory接口，onCreateView为替换View的入口
 * Created by Mistletoe on 2018/06/29 11:52.
 */

public class SkinFactory implements LayoutInflater.Factory {
    private static final String TAG = SkinFactory.class.getSimpleName();

    private List<SkinView> mSkinViews = new ArrayList<>();
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        final boolean skinEnable = attrs.getAttributeBooleanValue(SkinConfig.DEFAULT_SCHEMA_NAME, SkinConfig.DEFAULT_ATTR_NAME, false);
        if(skinEnable) {
            view = createView(context,name, attrs);
            if(null != view) {
                parseSkinAttr(context, attrs, view);
            }
        }
        return view;
    }

    /**
     * 重新创建视图
     * @param context 当前上下文
     * @param name 视图控件名称
     * @param attrs 视图控件属性集合(key-value的set)
     * @return 重新创建后的视图对象
     */
    private View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
               //基础控件走这里
            if (-1 == name.indexOf('.')){
                 if("TextView".equals(name)){
                        view = new TextView(context,attrs);
                }else if("Button".equals(name)){
                        view = new Button(context,attrs);
                 }else if("LinearLayout".equals(name)){
                        view = new LinearLayout(context,attrs);
                 }else if("ImageView".equals(name)){
                        view = new ImageView(context, attrs);
                 }else if("RelativeLayout".equals(name)){
                        view = new RelativeLayout(context,attrs);
                 }
            }else {
                // AppCompat类控件走这里
                if("android.support.v7.widget.Toolbar".equals(name)){
                        view = new Toolbar(context,attrs);
                } else if("android.support.v7.widget.AppCompatImageView".equals(name)){
                        view = new AppCompatImageView(context,attrs);
                }else if("com.scwang.smartrefresh.layout.header.ClassicsHeader".equals(name)){
                        view  = new ClassicsHeader(context,attrs);
                }else if("android.support.v7.widget.AppCompatTextView".equals(name)){
                        view  = new AppCompatTextView(context,attrs);
                }else if("android.support.v7.widget.AppCompatButton".equals(name)){
                        view  = new AppCompatButton(context,attrs);
                }else if("android.support.constraint.ConstraintLayout".equals(name)){
                        view  = new ConstraintLayout(context,attrs);
                }else{
                    //自定义控件(不继承自AppCompat类)走这里
                    view = LayoutInflater.from(context).createView(name, null, attrs);
                }

            }
            Log.i(TAG,name+"重新创建成功!");
        } catch (Exception e) {
            for(int i=0;i<attrs.getAttributeCount();i++){
                Log.i(TAG,attrs.getAttributeName(i)+":"+attrs.getAttributeValue(i)+"\n");
            }
            e.printStackTrace();
            Log.i(TAG,name+"重新创建失败!原因:"+e.getMessage());
            view = null;
        }
        return view;
    }

    /**
     * 解析换肤视图的属性集合,将需要换肤的属性对应的第三方资源设置上去
     * @param context 当前上下文
     * @param attrs 换肤视图控件的属性集合(key-value的set)
     * @param view 视图对象
     */
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<BaseAttr> viewAttrs = new ArrayList<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++){
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            if(!AttrUtil.isSupportedAttr(attrName)){
                continue;
            }

            if(attrValue.startsWith("@")){
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    BaseAttr mSkinAttr = AttrUtil.get(attrName, id, entryName, typeName);
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException | Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if(!viewAttrs.isEmpty()){
            SkinView skinView = new SkinView();
            skinView.view = view;
            skinView.attrs = viewAttrs;
            mSkinViews.add(skinView);
            if(SkinLoadPresenter.getInstance().isExternalSkin()){
                skinView.apply();
            }
        }
    }

    /**
     * 缓存在需要换肤的视图集合里的视图进行换肤
     */
    public void applySkin() {
        if(null != mSkinViews) {
            for(SkinView skinView : mSkinViews) {
                if(null != skinView.view) {
                    skinView.apply();
                }
            }
        }
    }

    /**
     * 释放全局缓存
     */
    public void clean(){
        if(mSkinViews.isEmpty()){
            return;
        }
        for(SkinView skinView : mSkinViews){
            if(skinView.view == null){
                continue;
            }
            skinView.clean();
        }
    }
}
