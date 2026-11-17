package parking.model;
public class EBike extends Vehicle {
    public static final double FLAT_FEE=20;
    public EBike(String r){super(r);}
    @Override protected double ratePerHour(){return FLAT_FEE;}
    @Override public double dailyMaximum(){return FLAT_FEE;}
    @Override public double calculateFee(int minutes){
        if(minutes<0)throw new IllegalArgumentException("Minutes must not be negative");
        return minutes==0?0:FLAT_FEE;
    }
}
