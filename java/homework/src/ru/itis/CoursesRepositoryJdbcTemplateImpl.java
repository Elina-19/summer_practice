package homework.src.ru.itis;

import homework.models.Course;
import homework.models.Lesson;
import homework.models.Student;
import homework.models.Teacher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class CoursesRepositoryJdbcTemplateImpl implements CoursesRepository{

    //language=SQL
    private static final String SQL_SELECT_ALL = "select c.id as course_id, l.id as lesson_id, l.name as lesson_name, * from course c left join teacher t on c.teacher_id = t.id left join lesson l on c.id = l.course_id order by c.id";

    //language=SQL
    private static final String SQL_SELECT_BY_NAME = "select c.id as course_id, l.id as lesson_id, l.name as lesson_name, * from course c left join teacher t on c.teacher_id = t.id left join lesson l on c.id = l.course_id where c.name = ?";

    //language=SQL
    private static final String SQL_SAVE = "insert into course(name, date_start, date_end, teacher_id) values (?, ?, ?, ?)";

    //language=SQL
    private static final String SQL_UPDATE_BY_ID = "update course set name = ?, date_start = ?, date_end = ?, teacher_id = ? where id = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select c.id as course_id, l.id as lesson_id, l.name as lesson_name, * from course c left join teacher t on c.teacher_id = t.id left join lesson l on c.id = l.course_id where c.id = ?";

    private JdbcTemplate jdbcTemplate;

    public CoursesRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<Course> courseRowMapper = (row, rowNumber) -> {

        int id = row.getInt("course_id");
        String name = row.getString("name");
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

    private final RowMapper<Student> studentRowMapper = (row, rowNumber) -> {

        int id = row.getInt("student_id");
        String firstName = row.getString("student_name");
        String lastName = row.getString("student_lastName");
        String group = row.getString("number_of_group");

        Student student = new Student(id, firstName, lastName, group);
        student.setCourses(new ArrayList<>());

        return student;
    };

    private final ResultSetExtractor<List<Course>> courseResultSetExtractor = resultSet -> {

        List<Course> courses = new ArrayList<>();
        Set<Integer> processedCourses = new HashSet<>();
        Course currentCourse = null;
        int rowNum = 1;

        while (resultSet.next()) {

            if (!processedCourses.contains(resultSet.getInt("course_id"))) {
                currentCourse = courseRowMapper.mapRow(resultSet, rowNum);
                courses.add(currentCourse);

                Teacher teacher = teacherRowMapper.mapRow(resultSet, rowNum);
                teacher.getCourses().add(currentCourse);
                currentCourse.setTeacher(teacher);
            }

            Integer lessonId = resultSet.getObject("lesson_id", Integer.class);

            if (lessonId != null) {
                Lesson lesson = lessonRowMapper.mapRow(resultSet, rowNum);
                lesson.setCourse(currentCourse);
                currentCourse.getLessons().add(lesson);
            }

            processedCourses.add(currentCourse.getId());
            rowNum++;
        }

        return courses;
    };

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, courseResultSetExtractor);
    }

    @Override
    public List<Course> findByName(String searchName) {
        return jdbcTemplate.query(SQL_SELECT_BY_NAME, courseResultSetExtractor, searchName);
    }

    @Override
    public Optional<Course> findById(Integer id) {
        List<Course> course = jdbcTemplate.query(SQL_SELECT_BY_ID, courseResultSetExtractor, id);

        if (course.isEmpty()){
            return Optional.empty();
        }
        else {
            return Optional.of(course.get(0));
        }
    }

    @Override
    public void save(Course course) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE, new String[]{"id"});
            statement.setString(1, course.getName());
            statement.setString(2, course.getDateStart());
            statement.setString(3, course.getDateEnd());
            statement.setInt(4, course.getTeacher().getId());
            return statement;
        }, keyHolder);
    }

    @Override
    public void update(Course course){

        if (course.getId() == null){
            throw new IllegalArgumentException("Exception in <Update>");
        }

        int affectedRows = jdbcTemplate.update(SQL_UPDATE_BY_ID, course.getName(),
                course.getDateStart(),
                course.getDateEnd(),
                course.getTeacher().getId(),
                course.getId());

        if (affectedRows != 1) {
            throw new IllegalArgumentException("Exception in <Update>");
        }
    }
}
