package com.example.skinlib.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.skinlib.model.ILoadListener;
import com.example.skinlib.view.ISkinUpdate;

/**
 * 皮肤切换管理器
 * Created by Mistletoe on 2018/06/29 16:34.
 */

public class SkinLoadPresenter implements ISkinLoadPresenter {
    private static final String TAG = SkinLoadPresenter.class.getSimpleName();
    private Context mContext;
    private String  mSkinPkgName;
    private Resources mResource;
    private boolean isDefaultSkin = false; //默认皮肤标志位
    private List<ISkinUpdate>  observers ;//内部维护一个需要更新皮肤的页面集
    //将当前页面加入缓存页面集
    @Override
    public void attach(ISkinUpdate observer) {
        if(null==observers){
            observers =  new ArrayList<>();
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    //将当前页面从缓存页面集移除
    @Override
    public void detach(ISkinUpdate observer) {
        if(observers==null) return;
        if(observers.contains(observer)){
            observers.remove(observer);
        }
    }
    //通知所有缓存页面集的页面更新主题皮肤`
    @Override
    public void notifySkinUpdate() {
        if(observers==null) return;
        for(ISkinUpdate observer:observers){
            observer.onThemeUpdate();
        }
    }
    //Singleton
    private static class SkinManagerHolder{
        private static final SkinLoadPresenter INSTANCE =new SkinLoadPresenter();
    }
    private SkinLoadPresenter(){
    }
    public static SkinLoadPresenter getInstance(){
        return SkinManagerHolder.INSTANCE;
    }
    /**
     * 初始化
     * @param ctx 上下文
     */
    public void init(Context ctx){
        mContext = ctx.getApplicationContext();
    }

    /**
     * 重载一个异步加载皮肤资源文件的方法,适应静默加载的场景
     * @param mSkinFilePath 资源文件路径
     */

    public void loadSkin(String mSkinFilePath){
        this.loadSkin(mSkinFilePath,null);
    }
    /**
     * 异步加载皮肤资源文件
     * @param mSkinFilePath 资源文件路径
     * @param iLoadListener 加载皮肤资源文件结果回调
     */
    @SuppressLint("StaticFieldLeak")
    public void loadSkin(String mSkinFilePath, final ILoadListener iLoadListener){
         new AsyncTask<String,Void,Resources>(){
             @Override
             protected void onPreExecute() {
                 super.onPreExecute();
                 Log.i(TAG,"开始加载资源文件");
                 if(iLoadListener!=null) {
                     iLoadListener.onStart();
                 }
             }

             @Override
            protected Resources doInBackground(String... strings) {
                 try {
                     if (strings.length == 1) {
                         String skinPkgPath = strings[0];
                         File file = new File(skinPkgPath);
                         if(!file.exists()){
                             return null;
                         }
                         PackageManager mPm = mContext.getPackageManager();
                         PackageInfo mInfo = mPm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);
                         mSkinPkgName = mInfo.packageName;
                         //由于AssetsManager的addAssetPath可以做到添加外部资源路径,而该API未公开,所以此处通过反射获取
                         AssetManager assetManager = AssetManager.class.newInstance();
                         Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                         addAssetPath.invoke(assetManager, skinPkgPath);
                         Resources superRes = mContext.getResources();
                         //根据新的AssetManager构造一个新的Resources对象
                         Resources skinResource = new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());
                         isDefaultSkin = false;
                         return skinResource;
                     }
                     return null;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return null;
                 }
            }

             @Override
             protected void onPostExecute(Resources resources) {
                 super.onPostExecute(resources);
                 mResource = resources;
                 if (mResource != null) {
                     if (iLoadListener != null) {
                         iLoadListener.onSuccess();
                     }
                     notifySkinUpdate();//通知视图发生更新改变
                 }else{
                     isDefaultSkin = true;
                     if (iLoadListener != null) {
                         iLoadListener.onFailed();
                     }
                 }
             }
         }.execute(mSkinFilePath);
    }

    /**
     * 根据图片资源Id索引皮肤资源中的图片
     * @param resId 资源Id
     * @return 图片drawable对象
     */
    public Drawable getDrawable(int resId){
        Resources originResources = mContext.getResources();
        Drawable originDrawable = originResources.getDrawable(resId);
        if(null == mResource || TextUtils.isEmpty(mSkinPkgName)) {
            return originDrawable;
        }
        //这里决定了换肤文件中的资源命名需要和宿主app资源命名相同
        String entryName =  originResources.getResourceEntryName(resId);
        int resourceId =mResource.getIdentifier(entryName, "drawable", mSkinPkgName);
        try {
            return mResource.getDrawable(resourceId);
        } catch (Exception e) {
            // do nothing
        }
        return originDrawable;
    }

    /**
     * 根据字符串资源Id索引皮肤资源中的strings.xml中的值
     * @param resId 字符串资源Id
     * @return strings.xml中对应的值
     */
    public String getTextString(int resId){
        Resources originResources = mContext.getResources();
        String originText = originResources.getString(resId);
        if(null == mResource || TextUtils.isEmpty(mSkinPkgName)) {
            return originText;
        }
        //这里决定了换肤文件中的资源命名需要和宿主app资源命名相同
        String entryName = originResources.getResourceEntryName(resId);
        int resourceId =mResource.getIdentifier(entryName, "string", mSkinPkgName);
        try {
            return mResource.getString(resourceId);
        } catch (Exception e) {
            // do nothing
        }
        return originText;
    }

    /**
     * 根据颜色的资源id索引皮肤资源中的color.xml中的值
     * @param resId 颜色的资源id
     * @return 皮肤资源中的color.xml中对应的值
     */
    public int getColor(int resId){
        Resources originResources = mContext.getResources();
        int originColor = originResources.getColor(resId);
        if(null == mResource || TextUtils.isEmpty(mSkinPkgName)) {
            return originColor;
        }
        String entryName = originResources.getResourceEntryName(resId);
        int resourceId =mResource.getIdentifier(entryName, "color", mSkinPkgName);
        try {
            return mResource.getColor(resourceId);
        } catch (Exception e) {
            // do nothing
        }
        return originColor;
    }
    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。</br>
     * 无皮肤包资源返回默认主题颜色
     * @param resId 颜色Id
     * @return 指定Selector的颜色集合
     */
    public ColorStateList convertToColorStateList(int resId) {
        boolean isExtendSkin = true;

        if (mResource == null || isDefaultSkin) {
            isExtendSkin = false;
        }
        String resName = mContext.getResources().getResourceEntryName(resId);
        if (isExtendSkin) {
            int trueResId = mResource.getIdentifier(resName, "drawable", mSkinPkgName);
            ColorStateList trueColorList ;
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    return  mContext.getResources().getColorStateList(resId);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    trueColorList = mResource.getColorStateList(trueResId);
                    return trueColorList;
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                return mContext.getResources().getColorStateList(resId);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }

        }
        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[] { mContext.getResources().getColor(resId) });
    }

    /**
     * 是否为第三方皮肤
      * @return true:第三方皮肤 false:默认皮肤
     */
    public boolean isExternalSkin(){
        return !isDefaultSkin&&mResource!=null;
    }

    /**
     * 存储默认皮肤主题
     */
    public void restoreDefaultTheme(){
        isDefaultSkin = true;
        mResource = mContext.getResources();
        notifySkinUpdate();
    }
}
