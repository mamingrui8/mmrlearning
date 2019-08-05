package com.mmr.learn.theory.thinkinginjava.part8;

/**
 * TODO 到底怎样做，才能让 Shared的finalized()执行？
 */
public class ReferenceCounting {
    public static void main(String[] args) throws Throwable {
        Shared shared = new Shared();
        Composing[] composings = {
                new Composing(shared),
                new Composing(shared),
                new Composing(shared),
                new Composing(shared)
        };

        for(Composing c : composings){
            c.dispose();
            c = null;
        }

        composings = null;

        shared = null;
        System.gc();
    }
}

class Shared{
    private int refcount = 0;
    private static long counter = 0;
    private static final long id = counter++;

    public Shared(){
        System.out.println("Creating " + this);
    }

    protected void dispose(){
        if(--refcount == 0){
            System.out.println("Dispose " + this);
        }
    }

    public void  addRef(){
        refcount++;
    }

    public String toString(){
        return "Shared " + id;
    }

//    protected void finalize() throws Throwable {
//        super.finalize();
//        System.out.println("refcount=" + refcount);
//    }

    protected void finalize() throws java.lang.Throwable {
        super.finalize();
        System.out.println("Cake Object " + id + "is disposed");
    }
}

class Composing{
    private Shared shared;
    private static long counter = 0;
    private final long id = counter++;

    public Composing(Shared shared){
        System.out.println("Creating " + this);
        this.shared = shared;
        this.shared.addRef();  //新增了一个对Shared持有的引用
    }

    /**
     * 不仅释放掉自身，还释放掉自己持有的共享对象
     */
    protected void dispose() throws Throwable {
        System.out.println("disposing " + this);
        shared.dispose();
    }

    public String toString(){
        return "Composing " + id;
    }
}
