package com.mmr.learn.theory.thinkinginjava.part7.practice6;

public class Chess extends BoardGame{
    Chess(){
        super(11);
        System.out.println("Chess Constructor");
    }

    public static void main(String[] args) {
        Chess x = new Chess();
    }
}

class Game{
    Game(int i){
        System.out.println("Game constructor");
    }
}

class BoardGame extends Game{
    BoardGame(int k){
        super(k);
        System.out.println("BoardGame constructor");
    }
}

