package com.mmr.learn.genericity.entity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: mmr
 * Date: 2018-09-02
 * Time: 10:54
 */
public class Pair<T> {
    private T first;
    private T second;

    public Pair(){first = null;second=null;}
    public Pair(T first, T second){this.first = first; this.second = second;}

    public T getFirst(){return this.first;}
    public T getSecond(){return this.second;}

    public void setFirst(T newValue){this.first = newValue;}
    public void setSecond(T newValue) {
        System.out.println("泛型类型擦除后继承/覆盖而来的方法");
        this.second = newValue;
    }


    public static <T> Pair<T> makePair(Class<T> cl){
        try{
            return new Pair<T>(cl.newInstance(), cl.newInstance());
        }catch (Exception e){
            return null;
        }
    }
}
