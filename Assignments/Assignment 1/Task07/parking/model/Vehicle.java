package parking.model;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Vehicle {
    public static final int MINUTES_PER_DAY = 1440;
    public static final int DAILY_MAX_HOURS = 8;
    private final String regNo;
    protected Vehicle(String regNo) { this.regNo = normalise(regNo); }
    public static String normalise(String regNo) {
        if (regNo == null || regNo.trim().isEmpty())
            throw new IllegalArgumentException("Registration number must not be blank");
        return regNo.trim().toUpperCase();
    }
    public final String getRegNo() { return regNo; }
    protected abstract double ratePerHour();
    public double dailyMaximum() { return DAILY_MAX_HOURS * ratePerHour(); }
    public double calculateFee(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Minutes must not be negative");
        int hours = (minutes + 59) / 60;
        return hours * ratePerHour();
    }
    public double calculateFee(LocalDateTime in, LocalDateTime out) {
        if (in == null || out == null) throw new IllegalArgumentException("Entry and exit time are required");
        long minutes = Duration.between(in, out).toMinutes();
        if (minutes < 0) throw new IllegalArgumentException("Exit time is before entry time");
        long fullDays = minutes / MINUTES_PER_DAY;
        int rest = (int) (minutes % MINUTES_PER_DAY);
        return fullDays * dailyMaximum() + Math.min(calculateFee(rest), dailyMaximum());
    }
    @Override public final boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Vehicle other && regNo.equals(other.regNo);
    }
    @Override public final int hashCode() { return regNo.hashCode(); }
    @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
}