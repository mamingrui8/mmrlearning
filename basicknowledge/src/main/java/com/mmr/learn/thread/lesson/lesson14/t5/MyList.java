package com.mmr.learn.thread.lesson.lesson14.t5;

import java.util.ArrayList;
import java.util.List;

public class MyList {
    private static List list = new ArrayList();

    public static void add(){
        list.add("李雷");
    }

    public static int size(){
        return list.size();
    }
}
