package com.mmr.learn.theory.thinkinginjava.part8;

/**
 * 协变返回类型
 *
 * 导出类中覆盖基类的方法可以返回"基类方法原本返回类型的导出类型"
 */
public class CovariantReturn {
    public static void main(String[] args) {
        Mill mill = new Mill();
        Grain grain = mill.process();

        System.out.println("Mill.process() -> " + grain);

        mill = new WheatMill();
        grain = mill.process();
        System.out.println(grain); //导出类可以被看作是基类的特殊类型
    }
}

class Grain{
    public String toString(){ return "Grain";}
}

class Wheat extends Grain{
    public String toString(){ return "Wheat";}
}

class Mill {
    Grain process(){ return new Grain();}
}

class WheatMill extends Mill{
    Wheat process(){return new Wheat();} //JavaSE5之前，不允许通过改变返回值类型实现方法的覆盖
}