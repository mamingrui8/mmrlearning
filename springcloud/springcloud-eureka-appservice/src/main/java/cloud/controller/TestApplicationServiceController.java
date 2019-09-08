package cloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开发Eureka Client
 * 这里开发的是Application Service, 也即服务提供者Provider 简单案例
 * 在Spring Cloud中开发一个服务提供者，只需要按照SpringMVC的控制器进行代码开发即可。
 * 一个对外提供的方法，就是一个服务。
 */
@RestController("/test")
public class TestApplicationServiceController {
    /**
     * 定义一个测试服务
     * 测试服务监听get请求，对URI没有任何要求
     * @return 由于使用了@RestController注解，因此都是通过Response Body返回，且格式为JSON。
     */
    @GetMapping
    public List<Map<String, Object>> test(){
        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i< 3; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", i+1);
            data.put("name", "test name " + i);

            result.add(data);
        }

        return result;
    }
}
