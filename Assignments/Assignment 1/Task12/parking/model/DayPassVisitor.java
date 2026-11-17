package parking.model;
import java.time.LocalDate;
public class DayPassVisitor extends Visitor {
    public static final double PASS_PRICE=100;
    private final LocalDate passDate;
    public DayPassVisitor(String name,LocalDate passDate){
        super(name);
        if(passDate==null)throw new IllegalArgumentException("Pass date is required");
        this.passDate=passDate;
    }
    @Override public boolean isFeeExempt(LocalDate day){return passDate.equals(day);}
    @Override public String category(){return "Visitor(day pass)";}
}
