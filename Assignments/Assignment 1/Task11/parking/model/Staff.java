package parking.model;
import java.time.LocalDate;
public class Staff extends PassHolder {
    public Staff(String n){this(n,null);}
    public Staff(String n,LocalDate d){super(n,d);}
    @Override public String category(){return "Staff";}
}
