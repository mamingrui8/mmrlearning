package com.mmr.learn.thread.lesson14.t14;

import java.io.PipedWriter;
import java.io.IOException;

/**
 * Description: 通过管道进行线程间通信 --- 字符流 写入数据
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 15:21
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class WriteData {
    public void writeData(PipedWriter writer){
        try {
            System.out.println("write: ");
            for (int i = 0; i < 300; i++) {
                String outData = "" + (i+1);
                writer.write(outData);
                System.out.print(outData);
            }
            System.out.println();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
