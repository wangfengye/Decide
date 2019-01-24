package com.ascend.wangfeng.latte.delegates.bottom;

import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

/**
 * Created by fengye on 2017/8/24.
 * email 1040441325@qq.com
 */

public class BottomBean {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_TOP = 1;
    private final int ICON;
    private int ICON_SELECTED;
    private final CharSequence TITLE;
    private  int mType;

    public BottomBean(@DrawableRes int icon, @DrawableRes int iconSelected, CharSequence title) {
        this.ICON = icon;
        this.ICON_SELECTED = iconSelected;
        this.TITLE = title;
        mType = TYPE_NORMAL;
    }

    public BottomBean(int ICON, CharSequence TITLE, int type) {
        this.ICON = ICON;
        this.TITLE = TITLE;
        mType = type;
    }

    public int getIconSelected() {
        return ICON_SELECTED;
    }

    public void setIconSelected(int ICON_SELECTED) {
        this.ICON_SELECTED = ICON_SELECTED;
    }

    public int getIcon(){
        return ICON;
    }
    public CharSequence getTitle() {
        return TITLE;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
