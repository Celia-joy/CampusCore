package service;

import model.Enrollment;
import model.Student;
import model.Course;
import model.Person;

import repository.EnrollmentRepository;
import repository.CourseRepository;
import repository.PersonRepository;

public class EnrollmentService {
    private final PersonRepository personRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService (PersonRepository personRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository){
        this.personRepository = personRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void enrollStudent(int studentId, int courseId){

        Person person = personRepository.findById(studentId);
        if(person == null){
            throw new IllegalArgumentException("Person not found.");
        }
        if(!(person instanceof Student)){
            throw new IllegalArgumentException("Only students can enroll");
        }

        Course course = courseRepository.findById(courseId);
        if(course == null){
            throw new IllegalArgumentException("Course not found.");
        }

        Enrollment enrollment = new Enrollment(studentId, courseId);
        enrollmentRepository.add(enrollment);
    }
}

