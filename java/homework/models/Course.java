package homework.models;

import java.util.List;
import java.util.StringJoiner;

public class Course {

    private Integer id;
    private String name;
    private String dateStart;
    private String dateEnd;
    private Teacher teacher;

    private List<Lesson> lessons;
    private List<Student> students;

    public Course(Integer id, String name, String dateStart, String dateEnd, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.teacher = teacher;
    }

    public Course( String name, String dateStart, String dateEnd, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.teacher = teacher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Lesson> getLessons(){
        return lessons;
    }

    public void setLessons(List<Lesson> lessons){
        this.lessons = lessons;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return new StringJoiner(",", Course.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("dateStart=" + dateStart + "'")
                .add("dateEnd=" + dateEnd)
                .add("teacher=" + teacher.getFirstName() + " " + teacher.getLastName())
                .add("lessons=" + lessons)
                .add("students=" + students)
                .toString();
    }
}
