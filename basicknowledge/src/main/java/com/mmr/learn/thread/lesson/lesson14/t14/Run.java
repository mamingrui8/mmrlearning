package com.mmr.learn.thread.lesson.lesson14.t14;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.TimeUnit;

/**
 * Description: 通过管道进行线程间通信：字符流
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月26日 15:51
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class Run {
    public static void main(String[] args){
        try{
            ReadData readData = new ReadData();
            WriteData writeData = new WriteData();

            //搭管子
            PipedReader pipedReader = new PipedReader();
            PipedWriter pipedWriter = new PipedWriter();
            pipedReader.connect(pipedWriter);

            ThreadRead threadRead = new ThreadRead(readData, pipedReader);
            threadRead.start();

            TimeUnit.SECONDS.sleep(2);

            ThreadWriter threadWriter = new ThreadWriter(writeData, pipedWriter);
            threadWriter.start();
        } catch (IOException | InterruptedException e){
           e.printStackTrace();
        }

    }
}
