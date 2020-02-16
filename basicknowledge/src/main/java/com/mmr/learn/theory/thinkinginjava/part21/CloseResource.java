package com.mmr.learn.theory.thinkinginjava.part21;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 等待I/O资源时的操作是不能被中断的，这种行为很有可能导致系统多线程死锁，为了解决这个问题，我们想出了以下办法:
 * 关闭任务在其上发生阻塞的底层资源。
 */
public class CloseResource {
    public static void main(String[] args) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8080);
        InputStream socketInput = new Socket("localhost", 8080).getInputStream();

        IoBlocked t1 = new IoBlocked(socketInput);
        IoBlocked t2 = new IoBlocked(System.in);
        exec.execute(t1);
        exec.execute(t2);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Shutting down all threads");
        //优先终止线程。虽然把interrupt()命令都分发下去了，但由于任务被阻塞了，因此没办法终止线程。
        //所以必须关闭发生阻塞的底层资源，才能让interrupt()方法起作用(或者说，发送到任务上)。
        exec.shutdownNow();
        TimeUnit.SECONDS.sleep(1);

        //关闭底层资源
        System.out.println("Closing " + socketInput.getClass().getName());
        socketInput.close();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Closing " + System.in.getClass().getName());
        System.in.close();
    }
}
