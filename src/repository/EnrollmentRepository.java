package repository;

import model.Enrollment;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    public void add(Enrollment enrollment){
        if (enrollment == null){
            throw new IllegalArgumentException("Enrollment cannot be null");
        }

        for (Enrollment existing : enrollments){
            if(existing.getStudentId() == enrollment.getStudentId() && existing.getCourseId() == enrollment.getCourseId()){
                throw new IllegalArgumentException("Duplicate enrollment");
            }
        }
        enrollments.add(enrollment);
    } 

    public int size(){
        return enrollments.size();
    }

    public Enrollment findByStudentAndCourse(int studentId, int courseId){
        if(studentId <= 0){
            throw new IllegalArgumentException(
                "Student ID must be positive."
            );
        }
        if (courseId <= 0){
            throw new IllegalArgumentException(
                "Course ID must be positive."
            );
        }
        for (Enrollment enrollment : enrollments){
            if(enrollment.getStudentId() == studentId && enrollment.getCourseId() == courseId){
                return enrollment;
            }
        }
        return null;
    }

    public List<Enrollment> findAll(){
        return Collections.unmodifiableList(
            new ArrayList<>(enrollments)
        );
    }

}