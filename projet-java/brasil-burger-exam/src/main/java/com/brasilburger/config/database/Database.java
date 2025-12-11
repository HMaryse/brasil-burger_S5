package com.brasilburger.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class Database {

    private static HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            InputStream is = Database.class.getClassLoader().getResourceAsStream("application.properties");

            if (is != null) {
                props.load(is);
            } else {
                throw new RuntimeException("Impossible de charger application.properties");
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(user);
            config.setPassword(password);

            config.setMaximumPoolSize(10);
            config.setMinimumIdle(1);

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur initialisation BD : " + e.getMessage());
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
