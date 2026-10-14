package repository;

import model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class PersonRepository {
    private final List<Person> people = new ArrayList<>();

    public void add(Person person){
        if (person == null) {
            throw new IllegalArgumentException (
                "Person cannot be null"
            );
        }
        for (Person existing : people){
            if (existing.getId() == person.getId()){
                throw new IllegalArgumentException (
                    "A person with this ID already exits."
                );
            }
        }

        people.add(person);
    }

    public List<Person> findAll() {
        return Collections.unmodifiableList(
            new ArrayList<>(people)
        );
    }

    public int size() {
        return people.size();
    }

    public Person findById(int id){
        for (Person person : people){
            if(person.getId() == id){
                return person;
            }
        }
        return null;
    }
}