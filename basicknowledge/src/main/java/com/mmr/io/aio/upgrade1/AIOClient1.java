package com.mmr.io.aio.upgrade1;

/**
 * Description: 升级版， client中加一个Handler, read()方法不准用channel.read(buffer)，而是使用read(dst, attachment, handler)来实现;  并且可以实现循环(轮询输入)
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月06日 22:57
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class AIOClient1 {
}
