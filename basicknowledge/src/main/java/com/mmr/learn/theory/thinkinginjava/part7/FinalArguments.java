package com.mmr.learn.theory.thinkinginjava.part7;

public class FinalArguments {
    void with(final Gizmo gizmo){
        //gizmo = new Gizmo();  //can not assign value to final variable
    }
}

class Gizmo{
    public void spin(){

    }
}
