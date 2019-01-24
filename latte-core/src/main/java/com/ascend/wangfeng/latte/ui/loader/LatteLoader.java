package com.ascend.wangfeng.latte.ui.loader;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by fengye on 2017/8/16.
 * email 1040441325@qq.com
 */

public class LatteLoader {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    public static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();

    public static void showLoading(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView = LoadCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);
        dialog.setCanceledOnTouchOutside(false);// click outside do nothing
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            //调整偏移
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER);
    }
    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog :
                LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    try {//常发生这类Exception的情形都是，有一个费时的线程操作，需要显示一个Dialog，在任务开始的时候显示一个对话框，然后当任务完成了在Dismiss对话框，如果在此期间如果Activity因为某种原因被杀掉且又重新启动了
                        dialog.cancel();
                    }catch (IllegalArgumentException e){
                        Log.e("LoaderVer", "stopLoading: ", e);
                    }

                }
            }
        }
    }
}
