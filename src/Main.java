import model.Person;
import model.Student;
import model.Teacher;
import model.Course;
import repository.PersonRepository;
import repository.CourseRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        System.out.println("\n=== PersonRepository Tests ===");

        PersonRepository repository = new PersonRepository();

        Student student1 = new Student(
            101, "Celia", 17, 1001, "Software Development"
        );

        Teacher teacher1 = new Teacher(
            205, "Alex", 35, 2001, "Java programming"
        );

        repository.add(student1);
        System.out.println("Student added successfully!");

        try {
            repository.add(teacher1);
            System.out.println("Teacher added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Could not add teacher: " + e.getMessage()
            );
        }

        System.out.println("\n--- Testing findById() ---");
        Person foundPerson = repository.findById(205);
        if(foundPerson != null){
            System.out.println("Found person: " + foundPerson.getName());
        }
        else {
            System.out.println("Person not found.");
        }

        Person missingPerson = repository.findById(999);
        if(missingPerson != null){
            System.out.println("Found person: " + missingPerson.getName());
        }
        else {
            System.out.println("Person with ID 999 was not found");
        }

        System.out.println("\n --- Testing findAll() ---");
        List<Person> allPeople = repository.findAll();

        for (Person person : allPeople){
            System.out.println(
                person.getId() + " - " + person.getName()
            );
        }
        try{
            allPeople.clear();
        }
        catch(UnsupportedOperationException e){
            System.out.println("Repository list is protected!");
        }
        System.out.println(
            "People in the repository: " + repository.size()
        );






        System.out.println("\n=== CourseRepository Tests ===");

        CourseRepository courseRepository = new CourseRepository();

        courseRepository.add(new Course(101, "Java Programming", 5));
        courseRepository.add(new Course(202, "Database Systems", 4));
        courseRepository.add(new Course(303, "Computer Networks", 3));

        System.out.println("Courses stored: " + courseRepository.size());

        System.out.println("\n--- Testing findById() ---");

        Course foundCourse = courseRepository.findById(202);
        System.out.println("Found Course: " + foundCourse.getName());

        Course missingCourse = courseRepository.findById(999);
        System.out.println("Missing course: " + missingCourse);

        System.out.println("\n --- Testing findAll() ---");
        List<Course> allCourses = courseRepository.findAll();

        for (Course course : allCourses){
            System.out.println(
                course.getId() + " - " +
                course.getName() + " - " +
                course.getCredits() + " credits "
            );
        }

        try{
            allCourses.clear();
        }
        catch(UnsupportedOperationException e){
            System.out.println("Repository list is protected!");
        }

        System.out.println("Final courses count: " + courseRepository.size());

        try{
            courseRepository.add(
                new Course(101, "Duplicate Course", 2)
            );
        }

        catch (IllegalArgumentException e){
            System.out.println("Duplicate rejected: " + e.getMessage());
        }
        System.out.println("Course count after duplicate test: " + courseRepository.size());



        System.out.println("All tests completed successfully!");
    }
}
