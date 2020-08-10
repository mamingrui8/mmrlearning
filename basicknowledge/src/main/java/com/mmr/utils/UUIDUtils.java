package com.mmr.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.util.UUID;

/**
 * 随机器
 *
 * @author mamr
 * @date 2020/1/20 9:36 上午
 */
public class UUIDUtils {
    public static void main(String[] args) {
        // 默认随机器算法版本是4
        System.out.println(UUID.randomUUID().version());
        // nameUUIDFromBytes()算法版本是3 生产环境中，倾向于使用算法3或算法5
        System.out.println(UUID.nameUUIDFromBytes("123".getBytes()).version());
        System.out.println(UUID.nameUUIDFromBytes("123".getBytes()));

        // 传入网卡信息，基于时间制作出生成器
        TimeBasedGenerator generator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        UUID uuid = generator.generate();
        // 通过计算当前时间戳、随机数和机器MAC地址得到，由于使用了MAC地址，因此这个版本的随机数能够保证全球唯一
        // 算法版本1
        System.out.println(uuid.version());
        System.out.println(uuid.toString().replaceAll("-", ""));
        System.out.println(randomUUID());
    }

    public static String randomUUID() {
        TimeBasedGenerator timeBasedGenerator
                = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
        return timeBasedGenerator.generate().toString().replaceAll("-", "");
    }
}
