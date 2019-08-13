package com.mmr.learn.theory.thinkinginjava.part8.practice16;

import java.util.HashMap;
import java.util.Map;

public class TicketServiceImpl {
    public static void main(String[] args) {
        TicketCenter ticketCenter = new TicketCenter();
        ticketCenter.handler();
        System.out.println("======");
        ticketCenter.changeHandler("管理工单");
        ticketCenter.handler();
    }
}

class TicketCenter {
    private TicketHandler ticketHandler = new TicketEventHandler();
    //ticketHandlerMap的值可以来自普通的rdbms,也可以来自noSql如redis
    private Map<String, String> ticketHandlerMap = new HashMap<String, String>(){{
        put("事件工单", "com.mmr.learn.theory.thinkinginjava.part8.practice16.TicketEventHandler");
        put("管理工单", "com.mmr.learn.theory.thinkinginjava.part8.practice16.TicketManagerHandler");
        put("变更工单", "com.mmr.learn.theory.thinkinginjava.part8.practice16.TicketChangeHandler");
    }};

    public void changeHandler(String name){
        try {
            ticketHandler = (TicketHandler)Class.forName(ticketHandlerMap.get(name)).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void handler(){ ticketHandler.handlerTicket();}
}

class TicketHandler{
    public void handlerTicket(){}
}

class TicketEventHandler extends TicketHandler{
    public void handlerTicket(){
        System.out.println("处理事件工单...");
    }
}

class TicketManagerHandler extends TicketHandler{
    public void handlerTicket(){
        System.out.println("处理管理工单...");
    }
}

class TicketChangeHandler extends TicketHandler{
    public void handlerTicket(){
        System.out.println("处理变更工单...");
    }
}