package com.mmr.learn.theory.thinkinginjava.part6;

/**
 * Description: TODO
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年07月28日 22:51
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
class Test{
    /**
     * 当编译一个.java文件后，由于每一个.java文件中的类都会输出一个class文件，因此会得到许多.class文件，
     *
     * 一个可执行程序由若干个类库打包而成，一个类库由若干个群组(package)组成，一个群组由若干个类文件组成，一个类文件由若干个
     *
     * Java解释器将所有的class文件整合成了若干个类文件，每一个类文件都包含一个public类以及任意数量的非public类，
     * 非public类输出的.class文件或许有很多，但它们都没有自己独立的.java文件。因此，一个类文件有且仅有一个构件，
     * 如果希望这些类文件同属于一个群组，就必须使用到关键字"package"
     */

}
