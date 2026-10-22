
package tests;

import model.Enrollment;

public class EnrollmentTests {

    public static void testConstructorValidation() {
        System.out.println("\n--- Testing Enrollment Constructor Validation ---");

        try {
            new Enrollment(0, 101);
            System.out.println("ERROR: Invalid student ID was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid student ID rejected: " + e.getMessage());
        }

        try {
            new Enrollment(101, -1);
            System.out.println("ERROR: Invalid course ID was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid course ID rejected: " + e.getMessage());
        }

        Enrollment validEnrollment = new Enrollment(101, 202);

        System.out.println(
            "Valid enrollment created: Student "
            + validEnrollment.getStudentId()
            + " -> Course "
            + validEnrollment.getCourseId()
        );
    }
}
