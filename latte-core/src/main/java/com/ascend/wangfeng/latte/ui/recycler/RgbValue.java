package com.ascend.wangfeng.latte.ui.recycler;




/**
 * Created by fengye on 2017/8/28.
 * email 1040441325@qq.com
 */

public class RgbValue {
    public int red;
    public int green;
    public int blue;
    public static RgbValue create(int red,int green,int blue){
        return new RgbValue(red,green,blue);
    }

    private RgbValue(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
}
