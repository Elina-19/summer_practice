package homework.src.ru.itis.interfaces;

import homework.models.Course;
import homework.models.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonRepository {

    List<Lesson> findAll();
    List<Lesson> findByName(String name);
    Optional<Lesson> findById(Integer id);
    void save(Lesson course);
    void update(Lesson course);
    void delete(Lesson lesson);
}
