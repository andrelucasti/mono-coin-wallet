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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Primary
    @Bean
    DataSource dataSource() throws IOException {
        var properties = new Properties();
        var inputStream = Resources.getResource("application-wallet-manager.properties").openStream();
        properties.load(inputStream);

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

    @Bean
    public Flyway flyway(@Qualifier("dataSource") final DataSource dataSource){
        final var configuration = new ClassicConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setLocations(new Location("classpath:flyway"));

        final var flyway = new Flyway(configuration);
        flyway.migrate();

        return flyway;
    }
}
