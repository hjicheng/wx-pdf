package cn.hjc.pdf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
@MapperScan("cn.hjc.pdf.mapper")
public class PdfApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfApplication.class, args);
    }

}
