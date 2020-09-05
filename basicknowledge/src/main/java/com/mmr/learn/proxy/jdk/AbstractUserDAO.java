package com.mmr.learn.proxy.jdk;

/**
 * 抽象主题角色
 * @author mamr
 * @date 2020/9/5 13:34
 */
public interface AbstractUserDAO {
    Boolean findUserById(String userId);
}
