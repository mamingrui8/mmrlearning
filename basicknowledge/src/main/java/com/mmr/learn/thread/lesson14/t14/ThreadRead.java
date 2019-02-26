package com.mmr.learn.thread.lesson14.t14;

import java.io.PipedReader;

public class ThreadRead extends Thread{
    private ReadData readData;
    private PipedReader reader;

    public ThreadRead(ReadData readData, PipedReader reader){
        super();
        this.readData = readData;
        this.reader = reader;
    }

    @Override
    public void run(){
        readData.readData(reader);
    }
}
