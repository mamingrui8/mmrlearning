package cloud;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 新版本的Spring Security 默认启用了csrf检验，要在eureka服务端那边配置security的csrf检验为false
 */
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        //继续执行原有流程，使认证生效
//        super.configure(http);
        //关闭csrf
        http.csrf().disable();
        //开启认证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}