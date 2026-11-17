package parking.service;
import parking.model.*;
public final class VehicleFactory {
    private VehicleFactory(){}
    public static Vehicle create(String type,String regNo){
        if(type==null)throw new IllegalArgumentException("Vehicle type is required");
        switch(type.trim().toUpperCase()){
            case "TWO_WHEELER":return new TwoWheeler(regNo);
            case "CAR":return new Car(regNo);
            case "EV":return new ElectricCar(regNo);
            default:throw new IllegalArgumentException("Unknown vehicle type: "+type);
        }
    }
}
