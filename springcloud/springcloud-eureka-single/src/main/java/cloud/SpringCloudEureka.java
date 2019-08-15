package cloud;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 需要增加一个新的注解
 * @EnableEurekaServer - 用于开启EurekaServer服务  表明当前服务时一个EurekaServer服务
 *
 * 可选的启动注解: @SpringCloudApplication。这个注解类似@SpringBootApplication，相当于把当前服务标识成一个SpringCloud应用
 * 在本项目中不能使用@SpringCloudApplication注解，因为缺少Hystrix熔断器，从而抛出异常: ClassNotFoundException
 */

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEureka {
    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(SpringCloudEureka.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);
        SpringApplication.run(SpringCloudEureka.class, args);
    }
}
