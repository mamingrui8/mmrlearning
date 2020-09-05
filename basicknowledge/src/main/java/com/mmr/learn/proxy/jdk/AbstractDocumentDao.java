package com.mmr.learn.proxy.jdk;

/**
 * 抽象主题角色
 * @author mamr
 * @date 2020/9/5 14:18
 */
public interface AbstractDocumentDao {
    Boolean deleteDocumentById(String documentId);
}
