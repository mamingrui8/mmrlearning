package com.mmr.learn.thread.lesson.lesson14.t13;

import java.io.PipedOutputStream;
import java.io.IOException;

/**
 * Description: 通过管道进行线程间通信：字节流 --- 向管道内写入数据
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 14:42
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class WriteData {

    public void writeMethod(PipedOutputStream out){
        try{
            System.out.println("write: ");
            for (int i = 0; i < 300; i++) {
                String outData = "" + (i+1);
                out.write(outData.getBytes());
                System.out.print(outData);
            }
            System.out.println();
            out.close();
        } catch (IOException e){
           e.printStackTrace();
        }

    }
}
