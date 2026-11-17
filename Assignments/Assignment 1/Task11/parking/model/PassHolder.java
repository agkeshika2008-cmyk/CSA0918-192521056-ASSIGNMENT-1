package parking.model;
import java.time.LocalDate;
public abstract class PassHolder extends ParkingUser {
    private final LocalDate passValidUntil;
    protected PassHolder(String n,LocalDate d){super(n);passValidUntil=d;}
    @Override public boolean isFeeExempt(LocalDate day){return passValidUntil!=null&&!day.isAfter(passValidUntil);}
}
