interface VehicleI {
    String getRegNo();
    double ratePerHour();
    default double calculateFee(int minutes) {
        int hours = (minutes + 59) / 60;
        return hours * ratePerHour();
    }
}
abstract class ChargeableBase { abstract double chargingFee(int kWh); }
class CarI implements VehicleI {
    private final String regNo;
    CarI(String regNo) { this.regNo = regNo; }
    public String getRegNo() { return regNo; }
    public double ratePerHour() { return 30; }
    @Override public boolean equals(Object o) { return o instanceof VehicleI v && v.getRegNo().equals(regNo); }
    @Override public int hashCode() { return regNo.hashCode(); }
}
class ElectricCarI extends ChargeableBase implements VehicleI {
    private final String regNo;
    ElectricCarI(String regNo) { this.regNo = regNo; }
    public String getRegNo() { return regNo; }
    public double ratePerHour() { return 30; }
    @Override double chargingFee(int kWh) { return kWh * 12; }
    @Override public boolean equals(Object o) { return o instanceof VehicleI v && v.getRegNo().equals(regNo); }
    @Override public int hashCode() { return regNo.hashCode(); }
}
public class VehicleAsInterface {
    public static void main(String[] args) {
        VehicleI car = new CarI("TN02B2");
        ElectricCarI ev = new ElectricCarI("TN03C3");
        System.out.println("CarI 130 min -> Rs " + car.calculateFee(130));
        System.out.println("ElectricCarI 130 min -> Rs " + (ev.calculateFee(130) + ev.chargingFee(10)));
        System.out.println("CarI equals ElectricCarI with same regNo? " + new CarI("X1").equals(new ElectricCarI("X1")));
    }
}