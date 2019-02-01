package com.mmr.learn.config;

import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.springframework.stereotype.Component;

/**
 * Description:
 * 无论是当配置项还是配置文件更新后，不仅仅会更新对应的属性值，还会触发一个回调方法，来供客户端监听。
 * 譬如当数据库的配置文件更新后，我需要在回调里做一些重新连接等等事情，那么就需要来监听对应的配置文件更改事件。
 *
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月29日 11:24
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
@Component
@DisconfUpdateService(classes = {AppTestConfig.class}, itemKeys = {""})
public class AppTestConfigUpdateCallback implements IDisconfUpdate {
    /**
     *  如果需要监听多份配置文件的多个属性，我们该怎么做呢？
     */
    @Override
    public void reload() throws Exception {
        System.out.println("配置文件发生了更改");
    }
}
