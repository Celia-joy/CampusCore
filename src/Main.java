
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

        
        System.out.println("\n--- Testing Missing Course ---");

        try {
            enrollmentService.enrollStudent(101, 999);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Enrollment rejected: " + e.getMessage());
        }

        System.out.println(
            "Current enrollments: " + enrollmentRepository.size()
        );

        
        System.out.println("\n--- Testing Duplicate Enrollment ---");

        try {
            enrollmentService.enrollStudent(101, 202);
            System.out.println("Enrollment successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Enrollment rejected: " + e.getMessage());
        }

        System.out.println(
            "Current enrollments: " + enrollmentRepository.size()
        );

        
        // =========================================
        // COURSE VALIDATION TESTS
        // =========================================

        System.out.println("\n=== Course Validation Tests ===");

        // Test 1: Valid course
        Course validCourse = new Course(
            404, "Object-Oriented Programming", 4
        );

        System.out.println(
            "Valid course created: " + validCourse.getName()
        );

        // Test 2: Invalid ID
        try {
            new Course(-1, "Java Programming", 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID rejected: " + e.getMessage());
        }

        // Test 3: Blank name
        try {
            new Course(405, "   ", 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Blank name rejected: " + e.getMessage());
        }

        // Test 4: Invalid credits
        try {
            new Course(406, "Database Systems", 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid credits rejected: " + e.getMessage());
        }

        
        // =========================================
        // STUDENT VALIDATION TESTS
        // =========================================

        System.out.println("\n=== Student Validation Tests ===");

        Student validStudent = new Student(
            404, "Jordan", 18, 4001, "Software Engineering"
        );

        System.out.println(
            "Valid student created: " + validStudent.getName()
        );

        try {
            new Student(405, "Jordan", 18, 0, "Software Engineering");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Invalid student number rejected: " + e.getMessage()
            );
        }

        try {
            new Student(406, "Jordan", 18, 4002, "   ");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Blank course of study rejected: " + e.getMessage()
            );
        }

        
        // =========================================
        // TEACHER VALIDATION TESTS
        // =========================================

        System.out.println("\n=== Teacher Validation Tests ===");

        Teacher validTeacher = new Teacher(
            407, "Morgan", 35, 5001, "Java Programming"
        );

        System.out.println(
            "Valid teacher created: " + validTeacher.getName()
        );

        try {
            new Teacher(408, "Morgan", 35, 0, "Java Programming");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Invalid employee number rejected: " + e.getMessage()
            );
        }

        try {
            new Teacher(409, "Morgan", 35, 5002, "   ");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Blank subject rejected: " + e.getMessage()
            );
        }

        
        // =========================================
        // SERVICE DEPENDENCY VALIDATION TESTS
        // =========================================

        System.out.println("\n=== Service Dependency Tests ===");

        try {
            new PersonService(null);
        } catch (IllegalArgumentException e) {
            System.out.println(
                "PersonService rejected null: " + e.getMessage()
            );
        }

        try {
            new CourseService(null);
        } catch (IllegalArgumentException e) {
            System.out.println(
                "CourseService rejected null: " + e.getMessage()
            );
        }

        try {
            new EnrollmentService(
                personRepository,
                null,
                enrollmentRepository
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                "EnrollmentService rejected null: " + e.getMessage()
            );
        }
        
        try {
            new EnrollmentService(
                null, courseRepository, enrollmentRepository
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Null person repository rejected: " + e.getMessage());
        }

        try {
            new EnrollmentService(
                personRepository, courseRepository, null
            );
        } catch (IllegalArgumentException e) {
            System.out.println("Null enrollment repository rejected: " + e.getMessage());
        }


        // =========================================
        // PERSON ID VALIDATION TESTS
        // =========================================


        
        System.out.println("\n=== Person ID Validation Tests ===");

        // A valid ID should work.
        Student validIdStudent = new Student(
            410, "Jordan", 18, 4010, "Software Engineering" 
        );
        System.out.println("Valid person ID accepted: " + validStudent.getId());

        // Zero should be rejected.
        try {
            new Student(0, "Jordan", 18, 4011, "Software Engineering");
        } catch (IllegalArgumentException e) {
            System.out.println("Zero ID rejected: " + e.getMessage());
        }

        // A negative ID should also be rejected.
        try {
            new Teacher(-5, "Alex", 35, 2001, "Java");
        } catch (IllegalArgumentException e) {
            System.out.println("Negative ID rejected: " + e.getMessage());
        }

        // =========================================
        // AGE BOUNDARY TESTS
        // =========================================


        
        System.out.println("\n=== Age Boundary Tests ===");

        // Lower boundary: should succeed
        try {
            Student ageZero = new Student(
                501, "Jordan", 0, 4501, "Software Engineering"
            );
            System.out.println("Age 0 accepted: " + ageZero.getAge());
        } catch (IllegalArgumentException e) {
            System.out.println("Age 0 rejected: " + e.getMessage());
        }

        // Upper boundary: should succeed
        try {
            Student age120 = new Student(
                502, "Morgan", 120, 4502, "Computer Science"
            );
            System.out.println("Age 120 accepted: " + age120.getAge());
        } catch (IllegalArgumentException e) {
            System.out.println("Age 120 rejected: " + e.getMessage());
        }

        // Below the lower boundary: should fail
        try {
            Student ageNegative = new Student(
            503, "Alex", -1, 4503, "Computer Science"
        );
        System.out.println("Age -1 accepted unexpectedly!");
        } catch (IllegalArgumentException e) {
            System.out.println("Age -1 rejected: " + e.getMessage());
        }

        // Above the upper boundary: should fail
        try {
            Student age121 = new Student(
                504, "Taylor", 121, 4504, "Computer Science"
            );
        System.out.println("Age 121 accepted unexpectedly!");
        } catch (IllegalArgumentException e) {
            System.out.println("Age 121 rejected: " + e.getMessage());
        }

        // =========================================
        // NULL INPUT VALIDATION TESTS
        // =========================================

        
        System.out.println("\n=== Null Input Validation Tests ===");

        // Test 1: Null student name
        try {
            new Student(601, null, 18, 4601, "Software Engineering");
            System.out.println("ERROR: Null student name was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Null student name rejected: " + e.getMessage());
        }

        // Test 2: Null course name
        try {
            new Course(602, null, 3);
            System.out.println("ERROR: Null course name was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Null course name rejected: " + e.getMessage());
        }

        // Test 3: Null student course of study
        try {
            new Student(603, "Jordan", 18, 4603, null);
            System.out.println("ERROR: Null course of study was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Null course of study rejected: " + e.getMessage());
        }

        // Test 4: Null teacher subject
        try {
            new Teacher(604, "Morgan", 35, 2604, null);
            System.out.println("ERROR: Null teacher subject was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Null teacher subject rejected: " + e.getMessage());
        }

        // =========================================
        // REPOSITORY NULL INPUT TESTS
        // =========================================

        
        System.out.println("\n=== Repository Null Input Tests ===");

         // PersonRepository
        try {
            personRepository.add(null);
            System.out.println("ERROR: Null person was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Null person rejected: " + e.getMessage());
        }

        // CourseRepository
        try {
            courseRepository.add(null);
            System.out.println("ERROR: Null course was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Null course rejected: " + e.getMessage());
        }

        // EnrollmentRepository
        try {
            enrollmentRepository.add(null);
            System.out.println("ERROR: Null enrollment was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Null enrollment rejected: " + e.getMessage());
        }

        // =========================================
        // AGE MUTATION VALIDATION TESTS
        // =========================================


        
        System.out.println("\n=== Age Mutation Validation Test ===");

        Student ageTestStudent = new Student(
            505, "Celia", 18, 4505, "Software Engineering"
        );

        System.out.println("Age before invalid update: " + ageTestStudent.getAge());

        try {
            ageTestStudent.setAge(150);
            System.out.println("ERROR: Invalid age was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid age rejected: " + e.getMessage());
        }

        System.out.println("Age after invalid update: " + ageTestStudent.getAge());

        
        // =========================================
        // TEACHER ENROLLMENT TESTS
        // =========================================

        System.out.println("\n--- Testing Teacher Enrollment ---");

        int countBefore = enrollmentRepository.size();

        try {
            enrollmentService.enrollStudent(205, 101);
            System.out.println("ERROR: Teacher enrollment was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Teacher enrollment rejected: " + e.getMessage());
        }

        System.out.println("Enrollments before: " + countBefore);
        System.out.println("Enrollments after: " + enrollmentRepository.size());

        // =========================================
        // MISSING PERSON TESTS
        // =========================================

        
        System.out.println("\n--- Testing Missing Person ---");

        int countBeforeMissingPerson = enrollmentRepository.size();

        try {
            enrollmentService.enrollStudent(999, 101);
            System.out.println("ERROR: Enrollment with missing person was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Missing person rejected: " + e.getMessage());
        }

        System.out.println("Enrollments before: " + countBeforeMissingPerson);
        System.out.println("Enrollments after: " + enrollmentRepository.size());

        // =========================================
        // VALIDATION ERROR TESTS
        // =========================================

        
        System.out.println("\n--- Testing Validation Order ---");

        int countBeforeOrderTest = enrollmentRepository.size();

        try {
            enrollmentService.enrollStudent(205, 999);
            System.out.println("ERROR: Invalid enrollment was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("First validation error: " + e.getMessage());
        }

        System.out.println("Enrollments before: " + countBeforeOrderTest);
        System.out.println("Enrollments after: " + enrollmentRepository.size());

        System.out.println("\nAll tests completed!");
    }
}
