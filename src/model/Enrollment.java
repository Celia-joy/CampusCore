package model;

public class Enrollment {
    private final int studentId;
    private final int courseId;

    public Enrollment(int studentId, int courseId){
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
        this.studentId = studentId;
        this.courseId = courseId;
    }
    public int getStudentId(){
        return studentId;
    }

    public int getCourseId(){
        return courseId;
    }
}