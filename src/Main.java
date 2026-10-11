import model.Person;
import model.Student;
import model.Teacher;

public class Main {
    public static void main(String[] args) {
        Student student = new Student(
            1, "Celia", 16, 1001, "Software engineering"
        );

        Teacher teacher = new Teacher(
            2, "Alex", 35, 2001, "Java programming"
        );

        student.introduce();
        teacher.introduce();

        System.out.println("Student number: " + student.getStudentNumber());        System.out.println("Teacher number: " + teacher.getEmployeeNumber());

        Person person1 = student;
        Person person2 = teacher;

        person1.introduce();
        person2.introduce();
    }
}