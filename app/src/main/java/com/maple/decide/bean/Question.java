package com.maple.decide.bean;

import java.util.ArrayList;

/**
 * @author maple on 2019/1/21 9:09.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class Question {
    private String title;
    private ArrayList<String> options = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
    public void addOption(String option){
        if (this.options==null)this.options = new ArrayList<>();
        this.options.add(option);
    }
}
