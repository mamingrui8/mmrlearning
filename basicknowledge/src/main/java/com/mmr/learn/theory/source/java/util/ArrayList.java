package com.mmr.learn.theory.source.java.util;

import com.mmr.learn.theory.source.java.util.question.Demo1;

import java.util.*;

/** TODO 待续...
 * Cloneable -> 具有克隆的能力
 * java.io.Serializable -> 具有序列化的能力
 * @param <E>
 */
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable{
    private static final long serialVersionUID = 8683452581122892189L;

    /**
     *  默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 能够分配给数组的最大容量
     * 数组对象的Header中会保存数组的长度信息，如果尝试请求超出MAX_ARRAY_SIZE的容量，
     * 将报错: OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     *  仅仅是一个普通的空数组而已
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 与EMPTY_ELEMENTDATA数组不同的是，当第一个元素加入至数组时，DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * 知道该如何扩张数组。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 用于存储ArrayList元素的数组缓冲区
     * ArrayList的容量就是这个数组缓冲区的长度
     * 若elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA，那么在清空ArrayList并添加第一个元素时，数组将会扩展到
     * DEFAULT_CAPACITY这么大的容量大小。
     * 反之，若elementData == EMPTY_ELEMENTDATA，则数组的容量永远只有0 //FIXME
     *
     * 由此可以看出，ArrayList的底层是使用数组来实现的
     */
    transient Object[] elementData; // non-private to simplify nested class access

    /**
     * ArrayList实际包含元素的个数
     */
    private int size;

    /**
     * 根据给定的初始容量来初始化List集合
     *
     * @param  initialCapacity  初始化容量
     * @throws IllegalArgumentException 如果初始化容量是负数，则报错
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity]; //初始化定长数组
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    /**
     * 构建一个容量为初始值10的空集合
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 使用集合来初始化ArrayList
     * 既然是用其它集合来初始化ArrayList，那么首先需要同步长度，接着需要同步数据
     * @see Demo1#test1() //6260652
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            // c.toArray 有可能不会返回 Object[] (详见 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * 清除数组中的空元素，返回List实际大小
     * 程序中可以通过这个方法减少ArrayList占据堆的空间
     *
     * 比如集合初始容量为12，那么当存入第13个数时，集合会自动扩张12/2 = 6个元素的长度，但很有可能我们只小存入13个或14个元素，
     * 这就导致ArrayList自动扩充的位置被浪费了，但这是实实在在的申请并占据了堆空间的。又由于数组本身可能一直被引用，导致jvm无法对其进行垃圾回收。
     * 所以这里就需要用到trimToSize()了。
     */
    public void trimToSize() {
        //modCount: 计算List被修改的次数，这个变量一般用于并发时保证数据的一致性
        modCount++;
        if (size < elementData.length) {   //List实际包含元素的个数 < 数组的长度 说明数组中有空元素
            elementData = (size == 0)
                    ? EMPTY_ELEMENTDATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * (自定义)
     * 去除数组中的空元素，返回压缩后的新数组
     * 比如 [1,2,3,null,5]  ====>   [1,2,3,5]
     */
    private void removeEmptyData(){
        elementData = Arrays.stream(elementData).filter(Objects::nonNull).toArray();
        size = elementData.length;
    }

    /**
     * 扩大ArrayList的容量，至少要保证能容纳一定数量的元素，至于扩大到多少由最小容量参数来决定
     * @param   minCapacity   必须容纳的最小容量
     */
    public void ensureCapacity(int minCapacity) {
        //毕竟是在扩大ArrayList容量，多出来的空间会实实在在的占据内存，因此应该尽可能的少申请空间。
        //所以这里计算的是最少扩容多少 FIXME 以下语句没有理解
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                // any size if not default element table
                ? 0
                // larger than default for default empty table. It's already
                // supposed to be at default size.
                : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) { //说明最小扩容量已经不够使用了，需要申请更多的空间
            ensureExplicitCapacity(minCapacity);
        }
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            //必须容纳的最小容量 > 目前数组的长度
            //出现这种情况只有一种可能，那就是ArrayList中存放数据的elementData数组已经装不下了！
            //此时就必须扩容了
            grow(minCapacity);
    }

    /**
     * (核心)
     * 动态扩展集合的容量
     *
     * 比如:
     * 1. 原先数组内的元素是10个，再次添加元素后，minCapacity=11，集合容量将膨胀到10 + (10 >> 1)=15
     * 2. 原先数组内的元素是12个，再次添加元素后，minCapacity=13，集合容量将膨胀到12 + (12 >> 1)=18
     * @param minCapacity 为了存储数据所需的最小容量
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length; //原先的数组容量
        int newCapacity = oldCapacity + (oldCapacity >> 1); //新的数组容量  >>1相当于除以2  TODO 为什么有人喜欢用右移，不用除法?
        if (newCapacity - minCapacity < 0) //扩容后的新数组的容量仍然无法满足存储数据的最小需求
            newCapacity = minCapacity; //直接用存储元素的最小容量来进行初始化
        //但通过观察以上四行代码不难发现，minCapacity太肆意妄为了，那如果minCapacity无穷大，系统也会满足吗? 当然不是。
        if (newCapacity - MAX_ARRAY_SIZE > 0) //如果超出了上限，程序会按照上限值(MAX_ARRAY_SIZE)来初始化一个最大的数组，尽可能的装数据
            newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    /**
     * 以最大容量尽最大可能扩容数组
     * @param minCapacity 为了存储数据所需的最小容量
     * @return 本次数组扩容后建议的容量
     */
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // minCapacity是int类型，因此有其极限值Integer.MAX_VALUE,如果超过则数据会溢出变成负数
            throw new OutOfMemoryError();
        //由于MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8， 因此minCapacity的确有可能大于MAX_ARRAY_SIZE
        return (minCapacity > MAX_ARRAY_SIZE) ? //这里我认为是做了一层保险，实际上，如果能调用hugeCapacity()，则说明newCapacity > MAX_ARRAY_SIZE ，
                                                //因此直接返回Integer.MAX_VALUE完全就可以了，但java.lang.ArrayList的发明者为了严谨，还是使用了三元表达式做了一层保险。
        Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    /**
     * 返回ArrayList实际包含元素的个数(不包含由于扩容但没有实际元素占领的数组子元素)
     */
    public int size() {
        return size;
    }

    /**
     * 集合是否有数据
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 集合中是否包含指定元素
     * @param o
     * @return
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    /**
     * 集合中最后一次匹配(出现)指定元素的位置(下标)
     * @param o 待匹配的元素
     * @return 匹配的位置
     */
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * 将集合转换成数组并返回
     * @return 返回的是一个全新的数组对象，而非现有数组的引用
     */
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /**
     * TODO
     * @param a
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    /**
     * TODO
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        return null;
    }

}


