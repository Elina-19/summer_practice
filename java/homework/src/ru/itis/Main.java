package homework.src.ru.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import homework.models.Course;
import homework.models.Teacher;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args){

        Properties properties = new Properties();

        try{
            properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));
        }catch(IOException e){
            throw new IllegalArgumentException(e);
        }

        ConfigProperties configProperties = new ConfigProperties(new HikariConfig(), properties);
        DataSource dataSource = new HikariDataSource(configProperties.getConfig());

        CoursesRepository coursesRepository = new CoursesRepositoryJdbcTemplateImpl(dataSource);

        coursesRepository.update(new Course(9, "kf", "eml", "ffkm", new Teacher(3, "f", "fe", 3)));
    }
}
