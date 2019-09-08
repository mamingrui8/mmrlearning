package cloud;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Eureka Client启动类
 * 是使用SpringBoot启动类实现应用启动
 * @EnableEurekaClient 注解是用于通知Spring Boot应用: 当前应用是一个Eureka客户端，
 * 需要做服务的注册。
 *
 * 使用全局配置文件Application.properties来对Eureka Server的相关信息进行配置
 * 比如 Eureka Server的IP
 */
@EnableEurekaClient //在Edgware版本以后可以省略
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
