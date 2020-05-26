package com.mmr.learn.serializable.copy.deep;

import java.io.IOException;

/**
 * @author mamr
 * @date 2020/5/26 10:46 上午
 */
public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        WorkLog workLog = new WorkLog();
        Attachment attachment = new Attachment();
        workLog.setAttachment(attachment);

        WorkLog clone = workLog.deepClone();

        System.out.println(workLog == clone);
        System.out.println(workLog.getAttachment() == clone.getAttachment());
    }
}
