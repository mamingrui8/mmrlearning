package com.mmr.learn.thread.lesson14.t1;

import java.util.ArrayList;
import java.util.List;

public class MyList {
    private List list = new ArrayList();

    public void add(){
        list.add("李雷");
    }

    public int size(){
        return list.size();
    }
}
