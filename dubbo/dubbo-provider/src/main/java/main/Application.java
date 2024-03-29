package main;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class Application {
    public static void main(String[] args) {
        //启动
        SpringApplication.run(Application.class,args);
    }
}
