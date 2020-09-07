package com.mmr.learn.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 客户端测试类
 * @author mamr
 * @date 2020/9/5 16:43
 */
public class Client {
    public static void main(String[] args) {
        InvocationHandler handler = null;
        AbstractUserDAO userDao = new UserDao();
        handler =new DaoLogHandler(userDao);
        AbstractUserDAO proxy = null;

        // 抽象主题角色
        proxy = (AbstractUserDAO)Proxy.newProxyInstance(AbstractUserDAO.class.getClassLoader(), new Class[]{AbstractUserDAO.class}, handler);
        proxy.findUserById("小男");

        System.out.println("--------------------------------------------");

        AbstractDocumentDao documentDao = new DocumentDao();
        handler =new DaoLogHandler(documentDao);
        AbstractDocumentDao proxyDocument = null;

        proxyDocument = (AbstractDocumentDao)Proxy.newProxyInstance(AbstractDocumentDao.class.getClassLoader(), new Class[]{AbstractDocumentDao.class}, handler);
        proxyDocument.deleteDocumentById("D001");

        /*
            jdk动态代理必须基于接口，根据接口生成对应的代理对象，并与invocationhandler绑定。
            InvocationHandler将多个真实主题角色的被代理的接口进行了统一的管理
         */
    }
}
