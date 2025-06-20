package dev.pitt.loans.loan_project.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager",
        basePackages = {"dev.pitt.loans.loan_project.repository.jpa.core.postgres"})
public class PostgresDB {

    @Bean(name = "postgresProperties")
    @ConfigurationProperties("spring.datasource.posgres")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "postgresDatasource")
    DataSource datasource(@Qualifier("postgresProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgresEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                                                    @Qualifier("postgresDatasource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("dev.pitt.loans.loan_project.entity.core.postgres")
                .persistenceUnit("postgres").build();
    }

    @Bean(name = "postgresTransactionManager")
    PlatformTransactionManager transactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
