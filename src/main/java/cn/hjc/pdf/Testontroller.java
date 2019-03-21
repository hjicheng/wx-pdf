package cn.hjc.pdf;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testontroller {

    @RequestMapping("/test")
    public String test(){
        return "test success ~";
    }
}
