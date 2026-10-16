package repository;

import model.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CourseRepository {
    private final List<Course> courses = new ArrayList<>();
    public void add(Course course){
        if (course == null) {
            throw new IllegalArgumentException (
                "Course cannot be null."
            );
        }
        for (Course existing : courses){
            if(existing.getId() == course.getId()){
                throw new IllegalArgumentException(
                    "A course with this ID already exists."
                );
            }
        } 
        courses.add(course);
    }

    public int size(){
        return courses.size();
    }

    public Course findById(int id){
        for (Course course : courses){
            if (course.getId() == id){
                return course;
            }
        }
        return null;
    }

    public List<Course> findAll(){
        return Collections.unmodifiableList(
            new ArrayList<>(courses)
        );
    }
}
