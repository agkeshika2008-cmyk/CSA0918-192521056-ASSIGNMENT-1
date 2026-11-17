package parking.model;
import java.time.LocalDate;
public class Visitor extends ParkingUser {
    public Visitor(){this("Guest");}
    public Visitor(String n){super(n);}
    @Override public boolean isFeeExempt(LocalDate day){return false;}
    @Override public String category(){return "Visitor";}
}
