package homework.src.ru.itis.interfaces;

import homework.models.Course;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository {

      List<Course> findAll();
      List<Course> findByName(String name);
      Optional<Course> findById(Integer id);
      void save(Course course);
      void update(Course course);
}
