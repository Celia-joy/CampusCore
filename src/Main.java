
import model.Person;
import model.Student;
import model.Teacher;
import model.Course;
import model.Enrollment;

import repository.PersonRepository;
import repository.CourseRepository;
import repository.EnrollmentRepository;

import service.PersonService;
import service.CourseService;
import service.EnrollmentService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // =========================================
        // PERSON REPOSITORY TESTS
        // =========================================

        System.out.println("\n=== PersonRepository Tests ===");

        PersonRepository personRepository = new PersonRepository();
        PersonService personService = new PersonService(personRepository);

        Student student1 = new Student(
            101, "Celia", 17, 1001, "Software Development"
        );

        Teacher teacher1 = new Teacher(
            205, "Alex", 35, 2001, "Java Programming"
        );

        personService.registerPerson(student1);
        System.out.println("Student added successfully!");

        try {
            personService.registerPerson(teacher1);
            System.out.println("Teacher added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Could not add teacher: " + e.getMessage()
            );
        }

        System.out.println("\n--- Testing findById() ---");

        Person foundPerson = personRepository.findById(205);

        if (foundPerson != null) {
            System.out.println("Found person: " + foundPerson.getName());
        } else {
            System.out.println("Person not found.");
        }

        Person missingPerson = personRepository.findById(999);

        if (missingPerson != null) {
            System.out.println("Found person: " + missingPerson.getName());
        } else {
            System.out.println("Person with ID 999 was not found.");
        }

        System.out.println("\n--- Testing findAll() ---");

        List<Person> allPeople = personService.getAllPeople();

        for (Person person : allPeople) {
            System.out.println(
                person.getId() + " - " + person.getName()
            );
        }

        try {
            allPeople.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("Repository list is protected!");
        }

        System.out.println(
            "People in the repository: " + personRepository.size()
        );


        // =========================================
        // COURSE REPOSITORY TESTS
        // =========================================

        System.out.println("\n=== CourseRepository Tests ===");

        CourseRepository courseRepository = new CourseRepository();
        CourseService courseService = new CourseService(courseRepository);

        courseRepository.add(
            new Course(101, "Java Programming", 5)
        );
        courseRepository.add(
            new Course(202, "Database Systems", 4)
        );
        courseRepository.add(
            new Course(303, "Computer Networks", 3)
        );

        System.out.println(
            "Courses stored: " + courseRepository.size()
        );

        System.out.println("\n--- Testing findById() ---");

        Course foundCourse = courseService.findCourseById(202);
        System.out.println("Found Course: " + foundCourse.getName());

        Course missingCourse = courseService.findCourseById(999);
        System.out.println("Missing course: " + missingCourse);

        System.out.println("\n--- Testing findAll() ---");

        List<Course> allCourses = courseService.getAllCourses();

        for (Course course : allCourses) {
            System.out.println(
                course.getId() + " - " +
                course.getName() + " - " +
                course.getCredits() + " credits"
            );
        }

        try {
            allCourses.clear();
        } catch (UnsupportedOperationException e) {
            System.out.println("Repository list is protected!");
        }

        System.out.println(
            "Final courses count: " + courseRepository.size()
        );

        try {
            courseRepository.add(
                new Course(101, "Duplicate Course", 2)
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Duplicate rejected: " + e.getMessage()
            );
        }

        System.out.println(
            "Course count after duplicate test: " +
            courseRepository.size()
        );


        // =========================================
        // ENROLLMENT REPOSITORY TESTS
        // =========================================

        System.out.println("\n=== EnrollmentRepository Tests ===");

        EnrollmentRepository enrollmentRepository =
            new EnrollmentRepository();

        Enrollment first = new Enrollment(101, 202);
        Enrollment second = new Enrollment(101, 303);

        enrollmentRepository.add(first);
        enrollmentRepository.add(second);

        System.out.println(
            "Total enrollments: " + enrollmentRepository.size()
        );

        System.out.println(
            "Found course ID: " +
            enrollmentRepository
                .findByStudentAndCourse(101, 303)
                .getCourseId()
        );

        System.out.println(
            "Missing enrollment: " +
            enrollmentRepository.findByStudentAndCourse(999, 202)
        );

        try {
            enrollmentRepository.add(new Enrollment(101, 202));
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Duplicate enrollment rejected: " + e.getMessage()
            );
        }

        System.out.println(
            "Enrollments after duplicate test: " +
            enrollmentRepository.size()
        );


        // =========================================
        // ENROLLMENT SERVICE TESTS
        // =========================================

        System.out.println("\n=== EnrollmentService Tests ===");

        // Use the same repositories, so the service
        // can find the student and course already registered.
        EnrollmentService enrollmentService = new EnrollmentService(
            personRepository,
            courseRepository,
            enrollmentRepository
        );

        // Student 101 enrolls in course 101.
        // This pair has not been enrolled yet.
        enrollmentService.enrollStudent(101, 101);

        System.out.println(
            "Enrollments after service call: " +
            enrollmentRepository.size()
        );

        Enrollment savedEnrollment =
            enrollmentRepository.findByStudentAndCourse(101, 101);

        if (savedEnrollment != null) {
            System.out.println(
                "Enrollment saved successfully: Student " +
                savedEnrollment.getStudentId() +
                " -> Course " + savedEnrollment.getCourseId()
            );
        }

        System.out.println("\nAll tests completed!");
    }
}
