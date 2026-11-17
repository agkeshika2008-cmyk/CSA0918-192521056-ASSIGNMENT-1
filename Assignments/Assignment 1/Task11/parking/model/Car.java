package parking.model;
public class Car extends Vehicle {
    public Car(String r){super(r);}
    @Override protected double ratePerHour(){return 30;}
}
