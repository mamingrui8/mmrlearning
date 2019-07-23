package com.mmr.learn.theory.source.java.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * java.util.Collection 源码
 * 目前只收录非常经典的方法
 */
public interface Collection<E> extends Iterable<E> {
    /**
     * 返回容器中元素的个数
     */
    int size();

    /**
     * 容器中是否没有包含元素
     */
    boolean isEmpty();

    /**
     * 容器中是否包含指定元素
     */
    boolean contains(Object o);

    /**
     * 返回容器的迭代器
     * 不能保证容器内元素的顺序(除非容器的实现类保证了顺序)
     */
    Iterator<E> iterator();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    boolean add(E e);

    boolean remove(Object o);

    boolean containsAll(java.util.Collection<?> c);

    boolean addAll(java.util.Collection<? extends E> c);

    boolean removeAll(java.util.Collection<?> c);

    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    boolean retainAll(java.util.Collection<?> c);

    void clear();

    boolean equals(Object o);

    int hashCode();

//    default Spliterator<E> spliterator() {
//        return Spliterators.spliterator(this, 0);
//    }

    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
