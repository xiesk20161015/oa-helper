package cn.dazd.oa.sync;

import cn.dazd.oa.sync.service.CronTaskService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OaSyncSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaSyncSystemApplication.class, args);
    }

    @Bean
    public Boolean doStartCron(CronTaskService cronTaskService) {
        cronTaskService.doStart();
        return true;
    }

}
