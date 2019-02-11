package com.mmr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月11日 23:21
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class PropertiesUtil {
    //ProertiesUtil能够解析的文件只能以.properties结尾
    private static final String SUFFIX = ".properties";

    /**
     * @param fileName 待解析的文件名
     * @return Properties
     */
    public static Properties getProperties(String fileName) throws IOException,NullPointerException {
        InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName + SUFFIX);
        Properties properties = new Properties();
        properties.load(Objects.requireNonNull(resourceAsStream));
        return properties;
    }
}
