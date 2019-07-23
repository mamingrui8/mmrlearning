package com.mmr.learn.theory.thinkinginjava.part11.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AsListInterface {
    public static void main(String[] args) {
        List<Snow> snow1 = Arrays.asList(new Crusty(), new Slush(), new Powder());
        List<Powder> snow2 = Arrays.asList(new Light(), new Heavy());

        List<Snow> snow3 = new ArrayList<>();
        List<Snow> list = Arrays.<Snow>asList(new Light(), new Heavy());
        snow3.addAll(snow2);

        System.out.println(Arrays.toString(new String[]{"1", "2"}));
    }
}


class Snow{}
class Powder extends Snow{}
class Light extends Powder{}
class Heavy extends Powder{}
class Crusty extends Snow{}
class Slush extends Snow{}