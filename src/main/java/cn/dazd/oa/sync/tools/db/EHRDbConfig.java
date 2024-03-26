package cn.dazd.oa.sync.tools.db;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xiesh
 * @version 1.0
 * @date 2022/3/21 10:25
 */
@Getter
@Slf4j
@Configuration
public class EHRDbConfig {
    private DataSource dataSource;

    @Bean
    public Boolean ehrEntityManager(
            @Qualifier("ehrHikariDataSource") HikariDataSource ehrHikariDataSource) {
        this.dataSource = ehrHikariDataSource;
        return true;
    }

    @Bean("ehrHikariDataSource")
    @ConfigurationProperties("spring.datasource.ehr")
    public HikariDataSource ehrHikariDataSource() {
        log.info("EHR 数据库连接池配置。");
        return new HikariDataSource();
    }

}
