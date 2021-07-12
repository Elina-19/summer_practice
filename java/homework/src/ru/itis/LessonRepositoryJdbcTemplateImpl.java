package homework.src.ru.itis;

import homework.models.Course;
import homework.models.Lesson;
import homework.models.Teacher;
import homework.src.ru.itis.interfaces.LessonRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.*;

public class LessonRepositoryJdbcTemplateImpl implements LessonRepository{

    //language=SQL
    private static final String SQL_SELECT_ALL = "select l.id as lesson_id, l.name as lesson_name, c.name as course_name, * from lesson l left join course c on l.course_id = c.id left join teacher t on c.teacher_id = t.id";

    //language=SQL
    private static final String SQL_SELECT_BY_NAME = "select l.id as lesson_id, l.name as lesson_name, c.name as course_name, * from lesson l left join course c on l.course_id = c.id left join teacher t on c.teacher_id = t.id where l.name = ?";

    //language=SQL
    private static final String SQL_SAVE = "insert into lesson(name, day_of_week, time, course_id) values (?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPDATE_BY_ID = "update lesson set name = ?, day_of_week = ?, time = ?, course_id = ? where id = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select l.id as lesson_id, l.name as lesson_name, c.name as course_name, * from lesson l left join course c on l.course_id = c.id left join teacher t on c.teacher_id = t.id where l.id = ?";

    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from lesson where id = ?";

    private JdbcTemplate jdbcTemplate;

    public LessonRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Course> courseRowMapper = (row, rowNumber) -> {

        int id = row.getInt("course_id");
        String name = row.getString("course_name");
        String dateStart = row.getString("date_start");
        String dateEnd = row.getString("date_end");

        Course course = new Course(id, name, dateStart, dateEnd, null);
        course.setLessons(new ArrayList<>());
        course.setStudents(new ArrayList<>());

        return course;
    };

    private final RowMapper<Teacher> teacherRowMapper = (row, rowNumber) -> {

        int id = row.getInt("teacher_id");
        String firstName = row.getString("first_name");
        String lastName = row.getString("last_name");
        int experience = row.getInt("experience");

        Teacher teacher = new Teacher(id, firstName, lastName, experience);
        teacher.setCourses(new ArrayList<>());

        return teacher;
    };

    private final RowMapper<Lesson> lessonRowMapper = (row, rowNumber) -> {

        int id = row.getInt("lesson_id");
        String name = row.getString("lesson_name");
        String dayOfWeek = row.getString("day_of_week");
        String time = row.getString("time");

        return (new Lesson(id, name, dayOfWeek, time, null));
    };

    private final ResultSetExtractor<List<Lesson>> lessonResultSetExtractor = resultSet -> {

        List<Lesson> lessons = new ArrayList<>();
        int rowNum = 1;

        while (resultSet.next()) {

            Lesson lesson = lessonRowMapper.mapRow(resultSet, rowNum);

            Course course = courseRowMapper.mapRow(resultSet, rowNum);

            Teacher teacher = teacherRowMapper.mapRow(resultSet, rowNum);

            course.setTeacher(teacher);
            course.getLessons().add(lesson);
            lesson.setCourse(course);
            lessons.add(lesson);

            rowNum++;
        }

        return lessons;
    };

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, lessonResultSetExtractor);
    }

    @Override
    public List<Lesson> findByName(String searchName) {
        return jdbcTemplate.query(SQL_SELECT_BY_NAME, lessonResultSetExtractor, searchName);
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        List<Lesson> lesson = jdbcTemplate.query(SQL_SELECT_BY_ID, lessonResultSetExtractor, id);

        if (lesson.isEmpty()){
            return Optional.empty();
        }
        else {
            return Optional.of(lesson.get(0));
        }
    }

    @Override
    public void save(Lesson lesson) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE, new String[]{"id"});
            statement.setString(1, lesson.getName());
            statement.setString(2, lesson.getDayOfWeek());
            statement.setString(3, lesson.getTime());
            statement.setInt(4, lesson.getCourse().getId());
            return statement;
        }, keyHolder);
    }

    @Override
    public void update(Lesson lesson){

        if (lesson.getId() == null){
            throw new IllegalArgumentException("Exception in <Update>");
        }

        int affectedRows = jdbcTemplate.update(SQL_UPDATE_BY_ID, lesson.getName(),
                lesson.getDayOfWeek(),
                lesson.getTime(),
                lesson.getCourse().getId(),
                lesson.getId());

        if (affectedRows != 1) {
            throw new IllegalArgumentException("Exception in <Update>");
        }
    }

    @Override
    public void delete(Lesson lesson){

        if (lesson.getId() == null){
            throw new IllegalArgumentException("Exception in <Delete>");
        }

        if (jdbcTemplate.update(SQL_DELETE_BY_ID, lesson.getId()) != 1){
            throw new IllegalArgumentException("Exception in <Delete>");
        }
    }
}
