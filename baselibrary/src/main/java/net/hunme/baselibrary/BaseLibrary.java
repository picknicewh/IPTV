package net.hunme.baselibrary;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.lzy.okhttputils.OkHttpUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者： ZLL
 * 时    间： 2016/7/8
 * 描    述：
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class BaseLibrary {
    /**
     * 整体activity同时销毁的
     */
    private static List<Activity> activitys = null;
    public static Application app;
    public static void initializer(Application application) {
        //初始化okhttp
        OkHttpUtils.init(application);
        //图片缓存
        initImageLoader(application);
        activitys = new ArrayList<>();
        app=application;
    }

    // 添加Activity到容器中
    public static void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
            }
        } else {
            activitys.add(activity);
        }
    }

    // 遍历所有Activity并finish
    public static void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    //图片缓存
    private static void initImageLoader(Context context) {
        // 缓存文件的目录
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "ChatFile");//设置内存卡的路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // .memoryCacheExtraOptions(480, 800)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // 设置当前线程优先级
                .denyCacheImageMultipleSizesInMemory() // 缓存显示不同 大小的同一张图片
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // 你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
                // int i = 50 * 1024 * 1024;
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                // 由原先的discCache -> diskCache
                .diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // 连接超时5s
                // 下载时间30s
                .writeDebugLogs() // Remove for release app
                .build();
        // 全局初始化此配置
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
