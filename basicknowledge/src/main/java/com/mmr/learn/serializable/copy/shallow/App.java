package com.mmr.learn.serializable.copy.shallow;

/**
 * Shallow Clone 浅拷贝
 * @author mamr
 * @date 2020/5/26 10:33 上午
 */
public class App {
    public static void main(String[] args) {
        WorkLog workLog = new WorkLog();
        Attachment attachment = new Attachment();
        workLog.setAttachment(attachment);

        WorkLog clone = workLog.clone();

        System.out.println(workLog == clone);
        System.out.println(clone.getAttachment() == attachment);
        // 由于Shallow Clone拷贝的是内存地址   对于值类型数据倒是可以拷贝，但是引用类型数据仅仅只是克隆引用对象的内存地址
        // 没有克隆引用对象指向的实际对象
    }
}
