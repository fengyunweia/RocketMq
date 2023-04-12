package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author fengyunwei
 */
@SpringBootApplication(scanBasePackages = "main")
public class Application {
    public static void main(String[] args) {
        //启动
        SpringApplication.run(Application.class,args);
    }
}
