package com.mmr.learn.theory.thinkinginjava.part21.practice28;

import com.mmr.learn.theory.thinkinginjava.part21.LifeOff;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 *
 */
public class E28_TestBlockingQueue2 {
    private static void getKey() {
        try{
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void getKey(String message) {
        System.out.println("message");
        getKey();
    }
    private static void test(String message, BlockingQueue<LifeOff> queue) {
        ExecutorService exec = Executors.newFixedThreadPool(2);
        System.out.println(message);
        LiftOffRunner runner = new LiftOffRunner(queue);
        LifeOffProducer producer = new LifeOffProducer(runner);
        exec.execute(runner);
        exec.execute(producer);
        getKey("Press 'ENTER' (" + message + ")");
        exec.shutdownNow();
        System.out.println("Finished " + message + " test");
    }

    public static void main(String[] args) {
        test("LinkedBlockingQueue", new LinkedBlockingQueue<>());
        test("ArrayBlockingQueue", new ArrayBlockingQueue<>(3));
        test("SynchronousQueue", new SynchronousQueue<>());
    }
}

class LiftOffRunner implements Runnable {
    private BlockingQueue<LifeOff> rockets;
    public LiftOffRunner(BlockingQueue<LifeOff> queue) {
        rockets = queue;
    }
    public void add(LifeOff lo) throws InterruptedException{
        rockets.put(lo);
    }
    @Override
    public void run() {
        try {
            LifeOff lifeOff = rockets.take();
            lifeOff.run();
        } catch (InterruptedException e) {
            System.out.println("Waking from take()");
        }
        System.out.println("Exiting LifeOffRunner");
    }
}

class LifeOffProducer implements Runnable {
    private LiftOffRunner runner;
    public LifeOffProducer(LiftOffRunner runner) {
        this.runner = runner;
    }
    @Override
    public void run() {
        try {
            for(int i = 0; i < 5; i++) {
                runner.add(new LifeOff(5));
            }
        } catch (InterruptedException e) {
            System.out.println("Waking from put()");
        }
        System.out.println("Exiting LifeOffProducer");
    }
}