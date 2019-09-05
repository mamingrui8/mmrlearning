package com.mmr.learn.theory.thinkinginjava.part13;

/**
 * 使用javap -c 生成JVM字节码
 */
public class WhitherStringBuilder {
    public String implicit(String[] fields){
        String result = "";
        for(int i = 0; i<fields.length; i++)
            result += fields[i]; //每次循环时都要重新创建一个StringBuilder对象
        return result;
    }

    public String explicit(String[] fields){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<fields.length; i++)
            result.append(fields[i]);
        return result.toString();
    }

}
