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
        if(personRepository == null){
            throw new IllegalArgumentException(
                "PersonRepository cannot be null."
            );
        }
        if(courseRepository == null){
            throw new IllegalArgumentException(
                "CourseRepository cannot be null."
            );
        }

        if( enrollmentRepository == null){
            throw new IllegalArgumentException(
                "EnrollmentRepository cannot be null."
            );
        }
        this.personRepository = personRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void enrollStudent(int studentId, int courseId){
        if (studentId <= 0){
            throw new IllegalArgumentException(
                "Student ID must be positive."
            );
        }

        if (courseId <= 0){
            throw new IllegalArgumentException(
                "Course ID must be positive."
            );
        }

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

