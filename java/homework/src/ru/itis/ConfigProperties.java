package homework.src.ru.itis;

import com.zaxxer.hikari.HikariConfig;
import java.util.Properties;

public class ConfigProperties {

    public static HikariConfig setProperties(HikariConfig config, Properties properties) {

        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        return config;
    }
}