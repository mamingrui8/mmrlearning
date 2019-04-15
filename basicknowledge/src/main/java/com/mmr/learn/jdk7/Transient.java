package com.mmr.learn.jdk7;

import java.io.*;

public class Transient {
    /**
     * Transient的概念:
     *      让对象中的某些成员属性不被序列化。
     *
     * 使用的场景如下:
     *      某些字段可以被其它属性字段推导出来，则可以用Transient来修饰。
     *      序列化和反序列化是有时间和空间成本的，比如一个长方形对象有三个属性: 长、宽和面积，那么面积就完全没有必要参与序列化，因为哪怕是对象数据传输后，我们想要获得该长方形的面积，也可以通过长 * 宽来得到。
     */

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //比如下例Rectangle

        Rectangle rectangle = new Rectangle(3,4);
        System.out.println("1.原始对象\n"+rectangle);

        ObjectOutputStream o =  new ObjectOutputStream(new FileOutputStream("Rectangle"));
        o.writeObject(rectangle);
        o.flush();
        o.close();

        ObjectInputStream i = new ObjectInputStream(new FileInputStream("Rectangle"));
        Rectangle rectangle1 = (Rectangle)i.readObject();
        System.out.println("2.反序列化后的对象\n"+rectangle1);  //反序列化后，面积的值为null
        i.close();
    }
}

class Rectangle implements Serializable{
    /**
     *
     */
    private Integer width;
    private Integer height;
    private transient Integer area;



    public Rectangle (Integer width, Integer height){
        this.width = width;
        this.height = height;
        this.area = width * height;
    }

    public void setArea(){
        this.area = this.width * this.height;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer(40);
        sb.append("width : ");
        sb.append(this.width);
        sb.append("\nheight : ");
        sb.append(this.height);
        sb.append("\narea : ");
        sb.append(this.area);
        return sb.toString();
    }
}
