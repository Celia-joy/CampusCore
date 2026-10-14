import model.Person;
import model.Student;
import model.Teacher;
import repository.PersonRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
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
        Person found = repository.findById(205);
        if(found != null){
            System.out.println("Found person: " + found.getName());
        }
        else {
            System.out.println("Person not found.");
        }

        Person missing = repository.findById(999);
        if(missing != null){
            System.out.println("Found person: " + missing.getName());
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


        System.out.println("Test completed.");
    }
}
