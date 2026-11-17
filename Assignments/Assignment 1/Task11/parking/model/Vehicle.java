package parking.model;
import java.time.Duration;
import java.time.LocalDateTime;
public abstract class Vehicle {
    public static final int MINUTES_PER_DAY=1440, DAILY_MAX_HOURS=8;
    private final String regNo;
    protected Vehicle(String regNo) { this.regNo=normalise(regNo); }
    public static String normalise(String r) {
        if(r==null||r.trim().isEmpty()) throw new IllegalArgumentException("Registration number must not be blank");
        return r.trim().toUpperCase();
    }
    public final String getRegNo(){return regNo;}
    protected abstract double ratePerHour();
    public double dailyMaximum(){return DAILY_MAX_HOURS*ratePerHour();}
    public double calculateFee(int minutes) {
        if(minutes<0) throw new IllegalArgumentException("Minutes must not be negative");
        return ((minutes+59)/60)*ratePerHour();
    }
    public double calculateFee(LocalDateTime in,LocalDateTime out) {
        if(in==null||out==null) throw new IllegalArgumentException("Entry and exit time are required");
        long m=Duration.between(in,out).toMinutes();
        if(m<0) throw new IllegalArgumentException("Exit time is before entry time");
        long days=m/MINUTES_PER_DAY; int rest=(int)(m%MINUTES_PER_DAY);
        return days*dailyMaximum()+Math.min(calculateFee(rest),dailyMaximum());
    }
    @Override public final boolean equals(Object o){return this==o||(o instanceof Vehicle v&&regNo.equals(v.regNo));}
    @Override public final int hashCode(){return regNo.hashCode();}
    @Override public String toString(){return getClass().getSimpleName()+"["+regNo+"]";}
}
