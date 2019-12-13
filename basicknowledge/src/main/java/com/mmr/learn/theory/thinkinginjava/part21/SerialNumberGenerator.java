package com.mmr.learn.theory.thinkinginjava.part21;

/**
 * @author mamr
 * @date 2019/12/13 12:19
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;

    /**
     * 此处会报错: Non-atomic operation on volatile field 'serialNumber'
     * 原因: volatile虽然能增加属性的可视性，但并不能保证同步，也就是说，线程虽然能"看"的到
     *       serialNumber值的变化，但却不能及时从主存中取出来
     *       此外，serialNumber++;并不是一个原子性的操作。
     *
     * 所以我们在使用volatile时，一定要联想到同步。
     */
    public static int nextSerialNumber() {
        return serialNumber++;
    }
}
