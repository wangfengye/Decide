package com.maple.decide;

import android.app.Application;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.maple.decide.bean.QuestionMap;
import com.tencent.bugly.Bugly;

/**
 * @author maple on 2019/1/22 13:32.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class MainApp extends Application {
    private static final String LOGINED = "old_user";

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this).configure();
        if (!BuildConfig.DEBUG) Bugly.init(getApplicationContext(), "a3da5be6d3", false);
        if (!LattePreference.getAppFlag(LOGINED)) {
            LattePreference.setAppFlag(LOGINED, true);
            String data = FileUtil.getRawFile(R.raw.data);
            LattePreference.setJson(MainActivity.QUESTIONS, JSON.parseObject(data, QuestionMap.class));
        }

    }
}
