interface Chargeable { double chargingFee(int kWh); }
class Vehicle {
    private final String regNo;
    private String note = trace("Vehicle field initialiser");
    { trace("Vehicle instance-initialiser block"); }
    Vehicle(String regNo) { trace("1) Vehicle constructor body, regNo = " + regNo); this.regNo = regNo; }
    static String trace(String message) { System.out.println(message); return message; }
    String describe() { return "Vehicle " + regNo; }
    double ratePerHour() { return 0; }
    String getRegNo() { return regNo; }
}
class Car extends Vehicle {
    Car(String regNo) { super(regNo); trace("2) Car constructor body"); }
    @Override String describe() { return super.describe() + " -> Car"; }
    @Override double ratePerHour() { return 30; }
}
class ElectricCar extends Car implements Chargeable {
    private final int batteryKWh;
    ElectricCar(String regNo, int batteryKWh) {
        super(regNo); trace("3) ElectricCar constructor body, batteryKWh = " + batteryKWh); this.batteryKWh = batteryKWh;
    }
    ElectricCar(String regNo) { this(regNo, 40); }
    @Override String describe() { return super.describe() + " -> ElectricCar (" + batteryKWh + " kWh battery)"; }
    @Override public double chargingFee(int kWh) { return kWh * 12.0; }
}
public class TraceDemo {
    public static void main(String[] args) {
        System.out.println("new ElectricCar(\"TN03C3\", 50):");
        Vehicle v = new ElectricCar("TN03C3", 50);
        System.out.println(); System.out.println(v.describe());
        System.out.println("rate = " + v.ratePerHour());
        System.out.println(); System.out.println("new ElectricCar(\"TN04D4\"):");
        new ElectricCar("TN04D4");
    }
}