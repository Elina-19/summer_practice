package homework.models;

import java.util.StringJoiner;

public class Lesson {

    private Integer id;
    private String name;
    private String dayOfWeek;
    private String time;
    private Course course;

    public Lesson(Integer id, String name, String dayOfWeek, String time, Course course) {
        this.id = id;
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.course = course;
    }

    public Lesson(String name, String dayOfWeek, String time, Course course) {
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.course = course;
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return new StringJoiner(",", Lesson.class.getSimpleName() + "[", "]")
                .add("name='" +name + "'")
                .add("dayOfWeek='" + dayOfWeek + "'")
                .add("time=" + time)
                .toString();
    }
}
