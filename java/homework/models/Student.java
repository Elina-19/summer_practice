package homework.models;

import java.util.List;
import java.util.StringJoiner;

public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private String numberOfGroup;

    private List<Course> courses;

    public Student(Integer id, String firstName, String lastName, String numberOfGroup) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfGroup = numberOfGroup;
    }

    public Student(String firstName, String lastName, String numberOfGroup) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfGroup = numberOfGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumberOfGroup() {
        return numberOfGroup;
    }

    public void setNumberOfGroup(String numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return new StringJoiner(",", Student.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("numberOfGroup=" + numberOfGroup)
                .toString();
    }
}
