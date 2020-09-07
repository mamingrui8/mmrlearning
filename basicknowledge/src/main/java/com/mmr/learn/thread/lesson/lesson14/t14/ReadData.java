package com.mmr.learn.thread.lesson.lesson14.t14;

import java.io.IOException;
import java.io.PipedReader;

/**
 * Description: 通过管道进行线程间通信 --- 字符流 读取数据
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 15:26
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class ReadData {
    public void readData(PipedReader reader){
        try {
            System.out.println("read: ");
            char[] charArrays = new char[20];
            int readLength = reader.read(charArrays);
            while(readLength != -1){
                String newData = new String(charArrays, 0, readLength);
                System.out.print(newData);
                readLength = reader.read(charArrays);
            }
            System.out.println();
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
