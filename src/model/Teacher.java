package model;

public class Teacher extends Person {
    private int employeeNumber;
    private String subject;

    public Teacher (int id, String name, int age, int employeeNumber, String subject){
        super(id, name, age);
        this.employeeNumber = employeeNumber;
        this.subject = subject;
    }

    public int getEmployeeNumber(){
        return employeeNumber;
    }
    
    public String getSubject(){
        return subject;
    }

    @Override
    public void introduce(){
        System.out.println("Hi, I'm " + getName() + " and I'm a teacher teaching " + getSubject());
    }
}
