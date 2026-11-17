package parking.model;
import java.time.LocalDate;
public class Student extends PassHolder {
    public Student(String n){this(n,null);}
    public Student(String n,LocalDate d){super(n,d);}
    @Override public String category(){return "Student";}
}
