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
public class OADbConfig {
    private DataSource dataSource;

    @Bean
    public Boolean oaEntityManager(
            @Qualifier("oaHikariDataSource") HikariDataSource oaHikariDataSource) {
        this.dataSource = oaHikariDataSource;
        return true;
    }

    @Bean("oaHikariDataSource")
    @ConfigurationProperties("spring.datasource.oa")
    public HikariDataSource oaHikariDataSource() {
        log.info("OA 数据库连接池配置。");
        return new HikariDataSource();
    }

}
