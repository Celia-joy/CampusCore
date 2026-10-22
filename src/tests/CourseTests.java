
package tests;

import model.Course;

public class CourseTests {

    public static void testCourseValidation() {
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
            System.out.println("ERROR: Invalid ID was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Invalid ID rejected: " + e.getMessage()
            );
        }

        // Test 3: Blank name
        try {
            new Course(405, "   ", 5);
            System.out.println("ERROR: Blank name was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Blank name rejected: " + e.getMessage()
            );
        }

        // Test 4: Invalid credits
        try {
            new Course(406, "Database Systems", 0);
            System.out.println("ERROR: Invalid credits were accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println(
                "Invalid credits rejected: " + e.getMessage()
            );
        }
    }
}
