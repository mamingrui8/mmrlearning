package com.mmr.learn.theory.thinkinginjava.part5;

public class Book {
    boolean checkedOut = false; //是否签入
    Book(boolean checkOut) {
        checkedOut = checkOut;
    }

    void checkIn(){
        checkedOut = false;
    }

    protected void finalize(){
        if(checkedOut) {
            System.out.println("Error: Checked out");
            try {
                super.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
