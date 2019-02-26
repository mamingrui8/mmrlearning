package com.mmr.learn.thread.lesson14.t13;

import java.io.IOException;
import java.io.PipedInputStream;

/**
 * Description: 通过管道进行线程间通信：字节流 --- 从管道内读取数据
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 14:48
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ReadData {
    public void readMethod(PipedInputStream input){
        try {
            System.out.println("read: ");
            byte[] byteArrays = new byte[20];
            int readLength = input.read(byteArrays); //这是一个阻塞方法
            while(readLength != -1){
                String newData = new String(byteArrays, 0, readLength);
                System.out.print(newData);
                readLength = input.read(byteArrays);
            }
            System.out.println();
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
