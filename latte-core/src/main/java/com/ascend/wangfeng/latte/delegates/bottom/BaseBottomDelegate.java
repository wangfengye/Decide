package com.ascend.wangfeng.latte.delegates.bottom;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ascend.wangfeng.latte.R;
import com.ascend.wangfeng.latte.R2;
import com.ascend.wangfeng.latte.delegates.LatteDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by fengye on 2017/8/24.
 * email 1040441325@qq.com
 */

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final ArrayList<BottomBean> TAB_BEANS = new ArrayList<>();
    private final LinkedHashMap<BottomBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0; // 正在展示的页面
    private int mIndexDelegate = 0; // 初始页面
    private int mClickedColor = Color.RED;

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar;


    public abstract LinkedHashMap<BottomBean, BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        mClickedColor = setClickedColor();
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);

        }
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        final int size = ITEMS.size();
        mCurrentDelegate = mIndexDelegate;
        final SupportFragment[] deleagates = ITEM_DELEGATES.toArray(new SupportFragment[size]);

        // 防止 activity销毁重建时, 重复创建item
        if (getChildFragmentManager().getFragments() == null||getChildFragmentManager().getFragments().size()<=0 ) {
            loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, deleagates);
        } else {
            // 恢复时,将栈中fragment 赋值给ITEM_DELEGATES,并改变当前选中的mCurrentDelegate
            ITEM_DELEGATES.clear();
            for (int i =0;i<getChildFragmentManager().getFragments().size();i++) {
                BottomItemDelegate fragment = (BottomItemDelegate) getChildFragmentManager().getFragments().get(i);
                ITEM_DELEGATES.add(fragment);
            }
        }
        // 无法判断哪个子页面处于显示状态,重置显示状态
        showHideFragment(ITEM_DELEGATES.get(mIndexDelegate));
        for (int i = 0; i < size; i++) {
            final BottomBean bean = TAB_BEANS.get(i);
            switch (bean.getType()) {
                case BottomBean.TYPE_NORMAL:
                    inflateIcon(bean, i);
                    break;
                case BottomBean.TYPE_TOP:
                    inflateTopIcon(bean, i);
                    break;
            }

        }

    }

    private void inflateTopIcon(BottomBean bean, int i) {
        LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_top_layout, mBottomBar);
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
        //设置每个item的点击事件
        item.setTag(i);
        item.setOnClickListener(this);
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setText(bean.getIcon());
        if (i == mIndexDelegate) {
            itemIcon.setTextColor(mClickedColor);
        }
    }

    public void inflateIcon(BottomBean bean, int i) {
        LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
        //设置每个item的点击事件
        item.setTag(i);
        item.setOnClickListener(this);
        final ImageView itemIcon = (ImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemIcon.setImageResource(bean.getIcon());
        itemTitle.setText(bean.getTitle());
        if (i == mCurrentDelegate) {
          /*  ColorStateList colorStateList = ColorStateList.valueOf(mClickedColor);
            itemIcon.setImageTintList(colorStateList);*/
            itemIcon.setImageResource(bean.getIconSelected());
            itemTitle.setTextColor(mClickedColor);
        } else {
            ColorStateList colorStateList = ColorStateList.valueOf(Color.GRAY);
            itemIcon.setImageResource(bean.getIcon());
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    public void goContainer(int i) {
        final RelativeLayout view = (RelativeLayout) mBottomBar.getChildAt(i);
        final int tag = (int) view.getTag();
        resetColor();
        final RelativeLayout item = view;
        final ImageView itemIcon = (ImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        ColorStateList colorStateList = ColorStateList.valueOf(mClickedColor);
        itemIcon.setImageTintList(colorStateList);
        itemTitle.setTextColor(mClickedColor);
        showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final ImageView itemIcon = (ImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
          //  ColorStateList colorStateList = ColorStateList.valueOf(Color.GRAY);
            itemIcon.setImageResource(TAB_BEANS.get(i).getIcon());
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View view) {
        final int tag = (int) view.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) view;
        final ImageView itemIcon = (ImageView) item.getChildAt(0);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        ColorStateList colorStateList = ColorStateList.valueOf(mClickedColor);
        itemIcon.setImageResource(TAB_BEANS.get(tag).getIconSelected());
        itemTitle.setTextColor(mClickedColor);
        showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        if (TAB_BEANS.get(tag).getType() == BottomBean.TYPE_TOP) {
            ITEM_DELEGATES.get(mCurrentDelegate).onSupportVisible();
        }
        mCurrentDelegate = tag;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }
}
