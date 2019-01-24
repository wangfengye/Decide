package com.ascend.wangfeng.latte;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testGson() throws Exception {
        Demo demo = new Demo(0.02);
        System.out.println(JSON.toJSONString(demo));

    }
    @Test
    public void sort() throws Exception{
        /*ArrayList<String> strs = new ArrayList<>();
        strs.add("北京市");strs.add("天津市");*/
        String[] strs = new String[]{"北京市","天津市","上海市","长庆市"};
        Arrays.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Collator com = Collator.getInstance(Locale.TRADITIONAL_CHINESE);
                return com.compare(o1, o2);
            }
        });
        System.out.println(Arrays.toString(strs).toString());
    }
    @Test
    public  void test(){
        List<List<Integer>> a = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            a.add(new ArrayList<Integer>());
        }
        a.get(0).add(1);
        a.get(1).add(2);
        a.get(2).add(3);

        canVisitAllRooms(a);
    }
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        ArrayList<Integer> set= new ArrayList();
        set.add(0);
        int i = 0;
        while(set.size()>i){
            int index = set.get(i);
            List<Integer> list = rooms.get(index);
            for(int j=0;j<list.size();j++){
                int key = list.get(j);
                if (set.contains(key))set.add(key);
            }
            i++;
        }
        if(set.size()==rooms.size())return true;
        return false;

    }

    public class Demo{
        private double x;

        public Demo(double x) {
            this.x = x;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        @Override
        public String toString() {
            return "Demo{" +
                    "x=" + x +
                    '}';
        }
    }
}