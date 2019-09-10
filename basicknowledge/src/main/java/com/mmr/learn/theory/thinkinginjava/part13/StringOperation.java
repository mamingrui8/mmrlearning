package com.mmr.learn.theory.thinkinginjava.part13;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * String上的操作
 */
public class StringOperation {
    public static void main(String[] args) throws Exception {
        String str = "String上的操作";

        //1. length()  字符串的长度
        int length = str.length();
        System.out.println("length() --- 字符串的长度:" + length);

        //2. charAt() 取得处于索引上的字符
        char c = str.charAt(0);
        System.out.println("charAt() --- 目标字符:" + c);

        //3. getChars(), getBytes()
        char[] targetChars = new char[str.length()];
        str.getChars(0, str.length(), targetChars, 0);  //srcBegin和srcEnd  左闭右开  srcEnd值得注意，取的是要取的那个数的后一个位置
        System.out.println("getChars():" + new ObjectMapper().writeValueAsString(targetChars));
        byte[] bytes = str.getBytes();
        System.out.println("getBytes():" + new ObjectMapper().writeValueAsString(bytes));

        //4. toCharArray() 生成一个char[],包含了String中的所有字符
        char[] chars = str.toCharArray();
        System.out.println("toCharArray():" + new ObjectMapper().writeValueAsString(chars));

        //5.equals(),equalsIgnoreCase()  比较两个String的内容是否相同，equalsIgnoreCase()忽略字母大小写
        System.out.println("equals():" + "string上的操作".equals(str));
        System.out.println("equalsIgnoreCase():" + "string上的操作".equalsIgnoreCase(str));

        //6. compareTo() 按词典顺序比较String的内容，比较结果为负数、正数或零。 注意: 大小写并不等价
        //               它会比较对应字符的ASCII码的顺序，如果第一个字符和参数的第一个字符不等，则结束比较！并返回二者的ascII差值！
        //               若比较的字符串长度不相等，则差几个字符就返回多少数字。 如下例当中"String上的操作ssss" 比"String上的操作" 多了4个字符("s","a","b","c")，因此返回4
        System.out.println("compareTo():" + "String上的操作sabc".compareTo(str));

        //7. contains() 如果该String包含参数的内容，则返回true，否则返回false
        System.out.println("contains():" + str.contains("操作"));

        //8. contentEquals() 用于StringBuffer或实现了CharSequence接口的任意类 如果该String与参数的内容完全相同(哪怕是大小写不同都不行)，则返回true，否则返回false
        StringBuffer sb = new StringBuffer();
        sb.append("String上的操作");
        System.out.println("contentEquals():" + str.contentEquals(sb));

        //9.
    }
}
