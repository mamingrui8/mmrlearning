package com.mmr.learn.serializable.copy.deep;

import lombok.Data;

import java.io.*;
import java.time.LocalDateTime;

@Data
public class WorkLog implements Serializable, Cloneable {
    private Integer age;
    private LocalDateTime createTime;

    private Attachment attachment;

    public WorkLog deepClone() throws IOException, ClassNotFoundException {
        // 内存 -> 流   对象 -> oos -> bao   写入
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bao);
        oos.writeObject(this);

        // 流 -> 内存  bis -> ois -> 对象
        ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (WorkLog) ois.readObject();
    }
}