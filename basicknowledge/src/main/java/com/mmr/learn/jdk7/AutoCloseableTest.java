package com.mmr.learn.jdk7;

import java.io.IOException;

/**
 * @author mamr
 * @date 2020/2/29 7:40 下午
 */
public class AutoCloseableTest {
    public static void main(String[] args) throws Exception{
        // "关闭异常"把"读取异常"给覆盖了
        // new TraditionalCloseable().demonstrate();

        // 演示jdk7中自动关闭流的操作
        try (NewVersionCloseable newVersionCloseable = new NewVersionCloseable()){
            newVersionCloseable.read();
        }

        /*
         * 读取资源
            关闭资源
            Exception in thread "main" java.io.IOException: 读取异常
                at com.mmr.learn.jdk7.NewVersionCloseable.read(AutoCloseableTest.java:59)
                at com.mmr.learn.jdk7.AutoCloseableTest.main(AutoCloseableTest.java:16)
                Suppressed: java.io.IOException: 关闭异常
                    at com.mmr.learn.jdk7.NewVersionCloseable.close(AutoCloseableTest.java:65)
                    at com.mmr.learn.jdk7.AutoCloseableTest.main(AutoCloseableTest.java:17)
         */
        // 上述操作中，读取异常没有被关闭异常给覆盖掉，此时的关闭存在于"Suppressed"抑制异常中
    }
}

/**
 * 传统关闭流的演示操作
 */
class TraditionalCloseable{
    public void read() throws IOException{
        System.out.println("读取资源");
        throw new IOException("读取异常");
    }

    public void close() throws IOException{
        System.out.println("关闭资源");
        throw new IOException("关闭异常");
    }

    /**
     * 演示传统关闭流的操作
     */
    public void demonstrate() throws Exception{
        try {
            read();
        }  catch (IOException e1) {
            throw e1;
        } finally {
            try {
                close();
            } catch (Exception e2) {
                throw e2;
            }
        }
    }
}

/**
 * jdk7中try-autocloseable 对资源释放时异常的处理
 */
class NewVersionCloseable implements AutoCloseable{
    public void read() throws IOException{
        System.out.println("读取资源");
        throw new IOException("读取异常");
    }

    @Override
    public void close() throws Exception {
        System.out.println("关闭资源");
        throw new IOException("关闭异常");
    }
}

