package model;

public class Student extends Person {
    private int studentNumber;
    private String courseOfStudy;

    public Student(int id, String name, int age, int studentNumber, String courseOfStudy){
        super(id, name, age);
        this.studentNumber = studentNumber;
        this.courseOfStudy = courseOfStudy;
    }

    public int getStudentNumber(){
        return studentNumber;
    }

    public String getCourseOfStudy(){
        return courseOfStudy;
    }
    
    @Override
    public void introduce(){
        System.out.println("Hi, I'm " + getName() + " and I'm a student studying " + getCourseOfStudy() + " . ");
    }
}