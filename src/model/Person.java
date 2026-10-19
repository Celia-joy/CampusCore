package model;

public abstract class Person {
    private final int id;
    private String name;
    private int age;

    public Person(int id, String name, int age){
        if ( id <= 0){
            throw new IllegalArgumentException (
                "Person ID must be positive."
            );
        }
        this.id = id;
        setName(name);
        setAge(age);
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException(
                "Name cannot be empty."
            );
        }

        this.name = name;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age){
        if(age < 0 || age > 120){
            throw new IllegalArgumentException(
                "Age must be between 0 and 120."
            );
        }
        this.age = age;
    }
    public abstract void introduce();
}