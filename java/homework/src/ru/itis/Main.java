package homework.src.ru.itis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import homework.src.ru.itis.interfaces.CoursesRepository;
import homework.src.ru.itis.interfaces.LessonRepository;
import homework.models.Course;
import homework.models.Teacher;

import javax.sql.DataSource;
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

        DataSource dataSource = new HikariDataSource(ConfigProperties.setProperties(new HikariConfig(), properties));

        CoursesRepository coursesRepository = new CoursesRepositoryJdbcTemplateImpl(dataSource);

        System.out.println(coursesRepository.findById(2));
    }
}
