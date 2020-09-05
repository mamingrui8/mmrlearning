package com.mmr.learn.proxy.jdk;

/**
 * 实际主题角色
 * @author mamr
 * @date 2020/9/5 14:20
 */
public class DocumentDao implements AbstractDocumentDao{

    @Override
    public Boolean deleteDocumentById(String documentId) {
        return "D001".equals(documentId);
    }
}
