package com.example.administrator.meet.utils;

import android.app.Activity;
import android.content.Context;

import com.tencent.gcloud.voice.GCloudVoiceEngine;

public class GvoiceManager {
    private GCloudVoiceEngine mVoiceEngine;
    private boolean bEngineInit = false;     // 是否已经初始化
    private static GvoiceManager gvoiceManager = null;

    /**
     * GCloudVoice 初始化时需要使用到的参数
     */
    private static String appID = "1936637659";    // 开通业务页面获取的 appID
    private static String appKey = "d578f1e7851215ca63e878b8ef276584";      // 开通业务页面获取的 appKey
    // 玩家唯一标识符，如从手Q或者微信获得到的OpenID
    private static String openID = Long.toString(System.currentTimeMillis());
    private Context mContext;
    private Activity mActivity;

    private GvoiceManager() {
    }

    // 单例
    public static GvoiceManager getInstance() {
        if (gvoiceManager == null) {
            gvoiceManager = new GvoiceManager();
        }
        return gvoiceManager;
    }

    /**
     * 初始化 GCloudVoice SDK
     * <p>
     * 注意！使用引擎前必须先按如下步骤完成引擎的初始化工作
     */
    public void initGvoice(Context context, Activity activity) {
        if (!bEngineInit) {
            bEngineInit = true;
            mContext = context;
            mActivity = activity;
            mVoiceEngine = GCloudVoiceEngine.getInstance();     // 获取 GCloudVoice 引擎实例
            mVoiceEngine.init(mContext, mActivity);             // 添加上下文信息
            mVoiceEngine.SetAppInfo(appID, appKey, openID);     // 设置基本业务信息
            mVoiceEngine.Init();                                // 初始化引擎
        }
    }

    public GCloudVoiceEngine getVoiceEngine() {
        return mVoiceEngine;
    }

    public boolean isEngineInit() {
        return bEngineInit;
    }
}
