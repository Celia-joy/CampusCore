package service;

import repository.PersonRepository;
import model.Person;

import java.util.List;

public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository){
        if(repository == null){
            throw new IllegalArgumentException("PersonRepository cannot be null.");
        }
        this.repository = repository;
    }

    public void registerPerson(Person person){
        repository.add(person);

    }
    public Person findPersonById(int id){
        return repository.findById(id);
    }

    public List<Person> getAllPeople(){
        return repository.findAll();
    }
}
