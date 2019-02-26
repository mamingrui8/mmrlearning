package com.mmr.learn.thread.lesson14.t14;

import java.io.PipedWriter;

public class ThreadWriter extends Thread{
    private WriteData writeData;
    private PipedWriter writer;

    public ThreadWriter(WriteData writeData, PipedWriter writer){
        super();
        this.writeData = writeData;
        this.writer = writer;
    }

    @Override
    public void run(){
        writeData.writeData(writer);
    }
}
