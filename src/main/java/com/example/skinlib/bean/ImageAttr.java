package com.example.skinlib.bean;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

import com.example.skinlib.presenter.SkinLoadPresenter;
import com.example.skinlib.bean.base.BaseAttr;
import com.example.skinlib.utils.AttrUtil;

/**
 * 图片属性实现
 * Created by Mistletoe on 2018/06/29 15:12.
 */

public class ImageAttr extends BaseAttr {

    @Override
    public void apply(View view) {
          if(view instanceof ImageView){
              ImageView imageView=(ImageView)view;
              if(AttrUtil.IMAGE_SRC.equalsIgnoreCase(attrName)){
                  imageView.setImageDrawable(SkinLoadPresenter.getInstance().getDrawable(attrValue));
              }

          }
          if(view instanceof AppCompatImageView){
              AppCompatImageView appCompatImageView = (AppCompatImageView) view;
            if(AttrUtil.IMAGE_SRC.equalsIgnoreCase(attrName)) {
                appCompatImageView.setImageDrawable(SkinLoadPresenter.getInstance().getDrawable(attrValue));
            }
          }
    }
}
