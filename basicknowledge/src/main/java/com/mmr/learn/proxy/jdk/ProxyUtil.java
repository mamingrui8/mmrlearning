package com.mmr.learn.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Proxy工具类
 *
 * @author mamr
 * @date 2020/11/16 10:05 下午
 */
public class ProxyUtil {
    /**
     * 将根据类信息动态生成的二进制字节码保存到硬盘中，默认的是clazz目录下
     * @param clazz 需要生成动态代理类的类的Class
     * @param proxyName 动态生成的代理类的名称
     */
    public static void generateClassFile(Class clazz, String proxyName) {
        // 根据类信息和提供的代理类名称，生成字节码 (字节数组)
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());

        String path = clazz.getResource(".").getPath();
        System.out.println("path=" + path);

        try(FileOutputStream out = new FileOutputStream(path + proxyName + ".class")) {
            out.write(classFile);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
