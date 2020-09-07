package com.mmr.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 公共类
 * @author mamr
 * @date 2020/9/5 20:09
 */
public class CommonUtil {
    /**
     * List<String>转换成Map<Sting, Boolean> 其中Boolean全部设置成true
     */
    public static Map<String, Boolean> listConvertToMap(List<String> sourceList) {
        return sourceList.parallelStream().collect(Collectors.toMap(e->e, e-> true));
    }
}
