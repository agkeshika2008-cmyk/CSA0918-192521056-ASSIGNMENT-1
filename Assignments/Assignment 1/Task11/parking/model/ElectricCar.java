package parking.model;
public class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String r){super(r);}
    @Override public double chargingFee(int kWh){
        if(!Chargeable.isValidUnits(kWh)) throw new IllegalArgumentException("kWh must not be negative");
        return kWh*RATE_PER_KWH;
    }
}
