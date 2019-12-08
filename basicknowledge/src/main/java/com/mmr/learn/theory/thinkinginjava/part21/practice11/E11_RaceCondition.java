package com.mmr.learn.theory.thinkinginjava.part21.practice11;

/**
 * @author Charles Wesley
 * @date 2019/12/8 22:28
 */
public class E11_RaceCondition {

}

class Tank{
    enum State {EMPTY, LOADED};
    private State state = State.EMPTY;
    private int current_load = 0;
    public void validate() {
        if ((state == State.EMPTY && current_load != 0) || (state == State.LOADED && current_load == 0)) {

        }
    }
}
