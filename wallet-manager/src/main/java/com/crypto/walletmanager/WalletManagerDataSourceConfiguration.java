package com.crypto.walletmanager;

import com.google.common.io.Resources;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
            basePackages = {"com.crypto.walletmanager.dataprovider"},
            entityManagerFactoryRef = "walletManagerEntity",
            transactionManagerRef = "walletManagerTransaction"
)
public class WalletManagerDataSourceConfiguration {

    @Primary
    @Bean(name = "walletManagerDataSource")
    DataSource dataSource() throws IOException {
        var properties = fetchProperties();

        var hikariDataSource = DataSourceBuilder.create()
                .url(properties.getProperty("spring.datasource.jdbc-url"))
                .driverClassName(properties.getProperty("spring.datasource.driver-class-name"))
                .username(properties.getProperty("spring.datasource.username"))
                .password(properties.getProperty("spring.datasource.password"))
                .type(HikariDataSource.class)
                .build();

        hikariDataSource.setPoolName(properties.getProperty("spring.datasource.pool-name"));

        return hikariDataSource;
    }

    @Primary
    @Bean(name = "walletManagerEntity")
    public LocalContainerEntityManagerFactoryBean walletManagerEntity(@Qualifier("walletManagerDataSource") DataSource dataSource) throws IOException {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.crypto.walletmanager.dataprovider");
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean(name = "managerFlyway")
    public Flyway flyway(@Qualifier("walletManagerDataSource") final DataSource dataSource){
        final var configuration = new ClassicConfiguration();
        configuration.setDataSource(dataSource);
            configuration.setLocations(new Location("classpath:flyway_wallet_manager"));


        final var flyway = new Flyway(configuration);
        flyway.migrate();

        return flyway;
    }

    @Primary
    @Bean(name = "walletManagerTransaction")
    public TransactionManager walletManagerTransaction(@Qualifier("walletManagerEntity") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());

        return jpaTransactionManager;
    }

    private static Properties fetchProperties() throws IOException {
        var properties = new Properties();
        var inputStream = Resources.getResource("application-wallet-manager.properties").openStream();
        properties.load(inputStream);
        return properties;
    }
}
