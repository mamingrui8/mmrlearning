package com.mmr.learn.theory.thinkinginjava.part8;

/**
 * 在构造器中调用被子类覆盖的方法
 * 思考:
 * Glyph作为RoundGlyph的基类，会优先初始化，但在此之前，这两个类编译后的代码都会被加载至内存当中，因此已经构成了"方法覆盖"。
 * 于是，在Glyph()中调用了RoundGlyph类的draw()
 */
public class PolyConstructors {
    public static void main(String[] args) {
        new RoundGlyph(5);
        System.out.println("解决之道... 构造器尽可能的简化或只调用final、private修饰的方法");
        new RectangularGlyph(5);
    }
}

class Glyph {
    void draw(){
        System.out.println("Glyph.draw()");
    }

    Glyph() {
        System.out.println("Glyph() before draw()");
        draw();
        scrawl();
        System.out.println("Glyph() after draw()");
    }

    final void scrawl(){
        System.out.println("Glyph.scrawl()");
    }
}

class RoundGlyph extends Glyph{
    private int radius = 1;
    RoundGlyph(int r){
        radius = r;
        System.out.println("RoundGlyph() radius = " + radius);
    }

    void draw(){
        System.out.println("RoundGlyph.draw(), radius = " + radius);
    }
}

class RectangularGlyph extends Glyph{
    private int radius = 1;

    RectangularGlyph(int r){
        radius = r;
        System.out.println("RectangularGlyph() radius = " + radius);
    }

//    void scrawl(){  //无法覆盖基类的final方法
//        System.out.println("RectangularGlyph.scrawl()");
//    }
}