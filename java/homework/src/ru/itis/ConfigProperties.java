package homework.src.ru.itis;

import com.zaxxer.hikari.HikariConfig;
import java.util.Properties;

public class ConfigProperties {

    private HikariConfig config;
    private Properties properties;

    public ConfigProperties(HikariConfig config, Properties properties) {
        this.config = config;
        this.properties = properties;
        setProperties();
    }

    private void setProperties() {

        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));
    }

    public HikariConfig getConfig() {
        return config;
    }
}