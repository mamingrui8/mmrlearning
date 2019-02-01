package com.mmr.learn.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 读取配置文件
 * 注意:
 * 1. 此
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年01月28日 17:56
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
//@Service
//@Scope("singleton")
@DisconfFile(filename = "apptest.properties")
@Configuration
public class AppTestConfig {
    //@Value("name")
    private String name;

    //@Value("password")
    private String password;

    @DisconfFileItem(name = "user.name", associateField = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DisconfFileItem(name = "user.passwd", associateField = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return String.format("名字: %s, 密码: %s", getName(), getPassword());
    }
}
