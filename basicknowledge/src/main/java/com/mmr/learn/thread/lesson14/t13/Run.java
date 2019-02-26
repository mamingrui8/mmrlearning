package com.mmr.learn.thread.lesson14.t13;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Description: 通过管道进行线程间通信：字节流
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 14:53
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        try {
            WriteData writeData = new WriteData();
            ReadData readData = new ReadData();

            PipedInputStream inputStream = new PipedInputStream();
            PipedOutputStream outputStream = new PipedOutputStream();

            outputStream.connect(inputStream);
            //或者
            //inputStream.connect(outputStream);

             ThreadRead threadRead = new ThreadRead(readData, inputStream);
             threadRead.start();

            TimeUnit.SECONDS.sleep(2);

            ThreadWrite threadWrite = new ThreadWrite(writeData, outputStream);
            threadWrite.start();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
