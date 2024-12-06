package cn.anlucky;

import cn.anlucky.system.utils.AddressUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.anlucky.*.mapper")
@SpringBootApplication
public class LuckyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckyAdminApplication.class, args);
    }

}
