package com.mmr.learn.serializable.copy.shallow;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 原型模式
 * @author mamr
 * @date 2020/5/26 10:28 上午
 */
@Data
public class WorkLog implements Serializable, Cloneable {
    private Integer age;
    private LocalDateTime createTime;

    private Attachment attachment;

    public WorkLog clone() {
        Object obj = null;
        try {
            obj = super.clone();
            return (WorkLog)obj;
        } catch (CloneNotSupportedException e) {
            System.out.println("不支持复制！");
            return null;
        }
    }
}
