package com.example.catalogservice.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * When the server is running, the H2 database is also running.
 */
@Configuration
public class H2Config {

    @Bean
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer().start();
    }
}
