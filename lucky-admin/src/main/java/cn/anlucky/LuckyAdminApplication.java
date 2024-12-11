package cn.anlucky;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;



@EnableAsync
@MapperScan("cn.anlucky.*.mapper")
@SpringBootApplication
public class LuckyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckyAdminApplication.class, args);
        System.out.println("Lucky-Admin-Vue 启动成功~~喵喵~" +"\n" +
                "  _                _          \n" +
                " | |              | |         \n" +
                " | |    _   _  ___| | ___   _ \n" +
                " | |   | | | |/ __| |/ / | | |\n" +
                " | |___| |_| | (__|   <| |_| |\n" +
                " |_________,_|____|_|\\_\\\\__, |\n" +
                "                         __/ |\n" +
                "                        |___/ ");
    }

}
