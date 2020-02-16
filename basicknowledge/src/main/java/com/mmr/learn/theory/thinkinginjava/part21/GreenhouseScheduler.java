package com.mmr.learn.theory.thinkinginjava.part21;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示SchedulerExecutor
 *
 * 本例场景: 温室的控制系统。可以控制各种设施的开关。
 * 重点掌握:
 * 1. ScheduledThreadPoolExecutor schedule(Runnable runnable, long delay, TimeUnit unit);
 *    延时执行 仅执行一次
 * 2. ScheduledThreadPoolExecutor scheduleAtFixedRate(Runnable runnable, long initialDelay, long period, TimeUnit unit);
 *    按照指定的时间间隔，在指定时间后，重复执行
 * 适用场景:
 * 1. 任务数量多、种类多
 * 2. 任务可以重复执行
 * 3. 任务的执行时间点不同，时间间隔
 * @author mamr
 * @date 2020/1/28 10:03 下午
 */
public class GreenhouseScheduler {
    /**
     * 灯光
     */
    private volatile boolean light = false;
    /**
     * 湿度
     */
    private volatile boolean water = false;
    private String thermostat = "Day";
    public synchronized String getThermostat() {
        return thermostat;
    }
    public synchronized void setThermostat(String value) {
        thermostat = value;
    }
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
    /**
     * 将事件加入至线程池中
     * @param event 待驱动的事件(实现了Runnable接口)
     * @param delay 执行等待时长 (单位是: 毫秒)
     */
    public void schedule(Runnable event, long delay) {
        scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
    }
    public void repeat(Runnable event, long initialDelay, long period) {
        scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
    }
    class LightOn implements Runnable {
        @Override
        public void run() {
            // 通过输入硬件编码，从物理上开灯
            System.out.println("Turning on lights");
            light = true;
        }
    }
    class LightOff implements Runnable {
        @Override
        public void run() {
            // 通过输入硬件编码，从物理上关灯
            System.out.println("Turning off lights");
            light = false;
        }
    }
    class WaterOn implements Runnable {
        @Override
        public void run() {
            // 增湿
            System.out.println("Turning greenhouse water on");
        }
    }
    class WaterOff implements Runnable {
        @Override
        public void run() {
            // 除湿
            System.out.println("Turning greenhouse water off");
        }
    }
    class ThermostatNight implements Runnable {
        @Override
        public void run() {
            System.out.println("Thermostat to night setting");
            setThermostat("Night");
        }
    }
    class ThermostatDay implements Runnable {
        @Override
        public void run() {
            System.out.println("Thermostat to day setting");
            setThermostat("Day");
        }
    }
    class Bell implements Runnable {
        @Override
        public void run() {
            System.out.println("Bing!");
        }
    }
    class Terminate implements Runnable {
        @Override
        public void run() {
            System.out.println("Terminating");
            scheduler.shutdownNow();
            // 此处需要开启一个独立的线程来驱动新的任务
            // 因为scheduler已经关闭了
            new Thread(){
                @Override
                public void run() {
                    for (DataPoint d : data) {
                        System.out.println(d);
                    }
                }
            }.start();
        }
    }
    // New feature: data collection
    static class DataPoint {
        final Calendar time;
        final float temperature;
        final float humidity;
        public DataPoint(Calendar d, float temp, float hum) {
            time = d;
            temperature = temp;
            humidity = hum;
        }
        @Override
        public String toString() {
            return time.getTime() + String.format(" temperature: %1$.1f humidity: %2$.2f", temperature, humidity);
        }
    }
    private Calendar lastTime = Calendar.getInstance();
    {
        // 把当前时间调整到半小时
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 0);
    }
    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random rand = new Random(47);
    List<DataPoint> data = Collections.synchronizedList(new ArrayList<>());
    class CollectData implements Runnable {
        @Override
        public void run() {
            System.out.println("Collecting data");
            synchronized (GreenhouseScheduler.this) {
                // Pretend the interval is longer than it is:
                lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
                // One in 5 chances of reversing the direction
                if (rand.nextInt(5) == 4) {
                    tempDirection = -tempDirection;
                }
                // Store previous value:
                lastTemp = lastTemp + tempDirection * (1.0f + rand.nextFloat());
                if(rand.nextInt(5) == 4) {
                    humidityDirection = - humidityDirection;
                }
                lastHumidity = lastHumidity + humidityDirection * rand.nextFloat();
                // Calendar must be cloned, otherwise all DataPoints hold references
                // to the same lastTime
                // For a basic object like Calendar,clone() is OK.
                data.add(new DataPoint((Calendar)lastTime.clone(), lastTemp, lastHumidity));
            }
        }
    }

    public static void main(String[] args) {
        GreenhouseScheduler gh = new GreenhouseScheduler();
        gh.schedule(gh.new Terminate(), 5000);
        // Former "Restart" class not necessary:
        gh.repeat(gh.new Bell(), 0, 1000);
    }
}