package com.mmr.learn.theory.thinkinginjava.part15.practice17;

import com.mmr.learn.theory.thinkinginjava.part15.Generators;
import net.mindview.util.Generator;
import java.util.*;

/**
 * 银行-柜员模型 用于讲述"匿名内部类"
 * @author Charles Wesley
 * @date 2019/11/24 19:54
 */
public class BankTeller {
    public static void serve(Teller t, Customer c){
        System.out.println(t + " serves " + c);
    }

    public static void main(String[] args) {
        Random rand = new Random(47);
        Queue<Customer> line = new LinkedList<>();
        Generators.fill(line, Customer.generator(), 15);
        List<Teller> tellers = new ArrayList<>();
        Generators.fill(tellers, Teller.generator, 4);
        for(Customer c : line){
            serve(tellers.get(rand.nextInt(tellers.size())), c);
        }
    }
}

class Customer {
    private static long counter = 1;
    private final long id = counter++;
    private Customer(){}
    public String toString(){return "Customer " + id;}

    public static Generator<Customer> generator(){
        return Customer::new;
    }
//    public static Generator<Customer> generator(){
//        return Person.generator(new Customer());
//    }
}

class Teller{
    private static long counter = 1;
    private final long id = counter++;
    private Teller(){}
    public String toString(){return "Teller " + id;}

    /**
     * 对比Customer不难发现，Customer的generator()方法每次调用后，都会产生一个全新的Generator<Customer>对象，但我们并不需要这么多的Generator，因此可以采用Teller的写法
     * 加载类时，生成一个静态Generator<Teller>对象即可
     */
    public static Generator<Teller> generator = Teller::new;
    //public static Generator<Teller> generator = Person.generator(new Teller());
}

interface Person {
    static <T> Generator<T> generator(T t) {
        return () -> {
            try {
                return (T) t.getClass().newInstance();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };
    }
}
