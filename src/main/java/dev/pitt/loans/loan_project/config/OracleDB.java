package dev.pitt.loans.loan_project.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracleTransactionManager",
        basePackages = {"dev.pitt.loans.loan_project.repository.jpa.core.oracle"})
public class OracleDB {
    @Primary
    @Bean(name = "oracleProperties")
    @ConfigurationProperties("spring.datasource")
    DataSourceProperties dataSourceProperties() {

        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "oracleDatasource")
    //@ConfigurationProperties(prefix = "spring.datasource")
    DataSource datasource(@Qualifier("oracleProperties") DataSourceProperties properties) {

        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "oracleEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                    @Qualifier("oracleDatasource") DataSource dataSource) {

        return builder.dataSource(dataSource)
                .packages("dev.pitt.loans.loan_project.entity.core.oracle")
                .persistenceUnit("oracle").build();
    }

    @Primary
    @Bean(name = "oracleTransactionManager")
    PlatformTransactionManager transactionManager(
            @Qualifier("oracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
