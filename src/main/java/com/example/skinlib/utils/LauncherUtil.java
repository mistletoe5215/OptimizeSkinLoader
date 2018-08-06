package com.example.skinlib.utils;

import android.content.ComponentName;
import android.content.pm.PackageManager;

/**
 * 切换不同应用图标和应用名称工具类
 * Created by Mistletoe on 2018/07/11 10:52.
 */

public class LauncherUtil {
    /**
     * 控制组件开启
     * 不可用状态：COMPONENT_ENABLED_STATE_DISABLED
     * 可用状态：COMPONENT_ENABLED_STATE_ENABLED
     * 默认状态：COMPONENT_ENABLED_STATE_DEFAULT
     * @param packageManager 包管理对象
     * @param componentName 组件名称

     */
    public static  void enableComponent(PackageManager packageManager,ComponentName componentName){
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 控制组件禁用
     * 不可用状态：COMPONENT_ENABLED_STATE_DISABLED
     * 可用状态：COMPONENT_ENABLED_STATE_ENABLED
     * 默认状态：COMPONENT_ENABLED_STATE_DEFAULT
     * @param packageManager 包管理对象
     * @param componentName   组件名称
     */
    public static void  disableComponent(PackageManager packageManager,ComponentName componentName){
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED ,
                PackageManager.DONT_KILL_APP);
    }
}
