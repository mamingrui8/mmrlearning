package com.mmr.learn.proxy.jdk;

/**
 * 实际主题角色
 * @author mamr
 * @date 2020/9/5 14:19
 */
public class UserDao implements AbstractUserDAO{
    @Override
    public Boolean findUserById(String userId) {
        if("小李".equals(userId)) {
            System.out.println("查询userId: " + userId + "成功");
            return true;
        } else {
            System.out.println("查询userId: " + userId + "失败，查无此人");
            return false;
        }
    }
}
