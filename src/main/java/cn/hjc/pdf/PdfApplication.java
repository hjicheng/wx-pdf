package cn.hjc.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class PdfApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfApplication.class, args);
    }

}
