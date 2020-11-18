package com.mmr.learn.proxy.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * 回调过滤器
 * @author mamr
 * @date 2020/11/18 8:04 下午
 */
public class DaoFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if ("select".equals(method.getName())) {
            // 当方法名称为select时，走Callback列表的第一个拦截器  0代表Callback列表的下标
            return 0;
        }
        if ("update".equals(method.getName())) {
            // 当方法名称为update时，走Callback列表的第二个拦截器
            return 1;
        }
        // 如果你希望不走任何的拦截器，抱歉这是做不到的，必须返回一个有效的下标值
        // 所以我们可以写一个默认的拦截器
        return 2;
    }
}
