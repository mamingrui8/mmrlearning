package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.*;

public class NonCollectionSequence extends PetSequence{
    /**
     *  正序遍历
     */
    public Iterator<Pet> iterator(){
        return new Iterator<Pet>() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < pets.length;
            }

            @Override
            public Pet next() {
                return pets[index++];
            }
            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }


    /**
     * 正序遍历
     */
    public Iterable<Pet> ordinaryIterator(){
        return new Iterable<Pet>(){
            @Override
            public Iterator<Pet> iterator(){
                return new Iterator<Pet>() {
                    int index = 0;
                    @Override
                    public boolean hasNext() {
                        return index < pets.length;
                    }

                    @Override
                    public Pet next() {
                        return pets[index++];
                    }
                };
            }
        };
    }

    /**
     * 倒叙遍历
     */
    public Iterable<Pet> reverseIterator(){
        return new Iterable<Pet>() {
            @Override
            public Iterator<Pet> iterator() {
                return new Iterator<Pet>() {
                    int index = pets.length - 1;
                    @Override
                    public boolean hasNext() {
                        return index > -1;
                    }

                    @Override
                    public Pet next() {
                        return pets[index--];
                    }
                };
            }
        };
    }

    /**
     * 乱序遍历
     */
    public Iterable<Pet> randomIterator(){
        Random random = new Random(47);
        return new Iterable<Pet>(){
            @Override
            public Iterator<Pet> iterator(){
                List<Pet> petList = new ArrayList<>(Arrays.asList(pets));
                Collections.shuffle(petList, random);
                return petList.iterator();
            }
        };
    }


    public static void main(String[] args) {
        NonCollectionSequence nc = new NonCollectionSequence();
        InterfaceVSIterator.display(nc.iterator());
        System.out.println();

        System.out.println("正序排序");
        nc.display(nc.ordinaryIterator());
        System.out.println();

        System.out.println("倒叙排序");
        nc.display(nc.reverseIterator());
        System.out.println();

        System.out.println("乱序排序");
        nc.display(nc.randomIterator());
        System.out.println();
    }

    private void display(Iterable<Pet> it){
        for(Pet pet : it){
            System.out.print(pet + " ");
        }
    }
}

class PetSequence{
    protected Pet[] pets = Pets.createArray(8);
}
