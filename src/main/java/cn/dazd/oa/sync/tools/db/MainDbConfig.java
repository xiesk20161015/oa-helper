package cn.dazd.oa.sync.tools.db;


import cn.dazd.oa.sync.entity.BaseEntity;
import cn.dazd.oa.sync.repo.IBaseRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author xiesh
 * @version 1.0
 * @date 2022/3/21 10:25
 */
@Getter
@Slf4j
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = {IBaseRepo.class},
        transactionManagerRef = "mainTransactionManager",
        entityManagerFactoryRef = "mainEntityManagerFactory")
@Configuration
public class MainDbConfig {
    private DataSource dataSource;

    @Bean
    @Primary
    public EntityManager mainEntityManager(
            LocalContainerEntityManagerFactoryBean mainEntityManagerFactory,
            HikariDataSource mainHikariDataSource) {
        this.dataSource = mainHikariDataSource;
        return Objects.requireNonNull(mainEntityManagerFactory.getObject()).createEntityManager();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
            DataSource mainHikariDataSource, EntityManagerFactoryBuilder builder,
            JpaProperties mainJpaProperties) {
        return builder.dataSource(mainHikariDataSource).properties(mainJpaProperties.getProperties()).packages(BaseEntity.class)
                .persistenceUnit("mainPersistenceUnit").build();
    }

    @Bean
    @Primary
    PlatformTransactionManager mainTransactionManager(LocalContainerEntityManagerFactoryBean mainEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(mainEntityManagerFactory.getObject()));
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public HikariDataSource mainHikariDataSource() {
        log.info("本机主数据库连接池配置。");
        return new HikariDataSource();
    }

    @ConfigurationProperties("spring.jpa")
    @Bean
    @Primary
    public JpaProperties mainJpaProperties() {
        return new JpaProperties();
    }

    /**
     * 配置第三方查询插件
     */
    @Component
    public static class QueryDslConfig {
        @PersistenceContext(name = "mainEntityManager")
        private EntityManager mainEntityManager;

        @Bean
        public JPAQueryFactory initFactory() {
            return new JPAQueryFactory(mainEntityManager);
        }
    }
}
