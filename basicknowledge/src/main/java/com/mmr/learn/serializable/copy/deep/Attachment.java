package com.mmr.learn.serializable.copy.deep;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mamr
 * @date 2020/5/26 10:37 上午
 */
@Data
public class Attachment implements Serializable {
    private String name;
}
