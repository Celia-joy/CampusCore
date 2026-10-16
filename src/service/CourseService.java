package service;

import model.Course;
import repository.CourseRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository repository;

    public CourseService(CourseRepository repository){
        if(repository == null){
            throw new IllegalArgumentException("CourseRepository cannot be null.");
        }
        this.repository = repository;
    }

    public void addCourse(Course course){
        repository.add(course);
    }

    public Course findCourseById(int id){
        return repository.findById(id);
    }

    public List<Course> getAllCourses(){
        return repository.findAll();
    }
}