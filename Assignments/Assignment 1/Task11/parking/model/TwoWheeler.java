package parking.model;
public class TwoWheeler extends Vehicle {
    public TwoWheeler(String r){super(r);}
    @Override protected double ratePerHour(){return 10;}
}
