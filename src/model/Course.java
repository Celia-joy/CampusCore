package model;

public class Course {
    private final int id;
    private String name;
    private int credits;

    public Course(int id, String name, int credits){
        if(id <= 0){
            throw new IllegalArgumentException("Course ID must be positive.");
        }

        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Course name cannot be empty.");
        }

        if(credits <= 0){
            throw new IllegalArgumentException("Course credits must be positive.");
        }
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getCredits(){
        return credits;
    }
}