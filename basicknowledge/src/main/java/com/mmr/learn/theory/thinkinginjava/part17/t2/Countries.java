package com.mmr.learn.theory.thinkinginjava.part17.t2;

import java.util.*;

/**
 * 本例用于演示通过继承java.util.AbstractXXX来创建定制的Map和Collection到底有多简单
 */
public class Countries {
    /*
        世界各国和首都
     */
    public static final String[][] DATA = {
            {"ALGERIA", "Algiers"},
            {"ANGOLA", "Luanda"}
    };

    private static class FlyweightMap extends AbstractMap<String, String> {
        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return null;
        }

        private static class Entry implements Map.Entry<String, String> {
            int index;
            Entry(int index) {this.index = index;}

            //当前遍历对象的首都和形参的是否一致
            public boolean equals(Object o){
                return DATA[index][0].equals(o);
            }

            @Override
            public String getKey() {
                return DATA[index][0];
            }

            @Override
            public String getValue() {
                return DATA[index][1];
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }

            public int hashCode() {
                return DATA[index][0].hashCode();
            }
        }

        static class EntrySet extends AbstractSet<Map.Entry<String, String>> {
            private int size;
            EntrySet(int size) {
                if(size < 0)
                    this.size = 0;
                else if (size > DATA.length)
                    this.size = DATA.length;
                else
                    this.size = size;
            }

            private class Iter implements Iterator<Map.Entry<String, String>> {
                private Entry entry = new Entry(-1);

                @Override
                public boolean hasNext() {
                    return entry.index < size - 1;
                }

                @Override
                public Map.Entry<String, String> next() {
                    entry.index ++;
                    return entry;
                }

                public void remove(){
                    throw new UnsupportedOperationException();
                }
            }

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new Iter();
            }

            @Override
            public int size() {
                return size;
            }

            private static Set<Map.Entry<String, String>> entries = new EntrySet(DATA.length);
            public Set<Map.Entry<String, String>> entrySet() {
                return entries;
            }
        }

        //TODO static Map<String, String> select(final int size)
    }
}
