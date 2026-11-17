class VehicleType {
    private final String name; private final double ratePerHour;
    VehicleType(String name, double ratePerHour) { this.name = name; this.ratePerHour = ratePerHour; }
    String getName() { return name; }
    double getRatePerHour() { return ratePerHour; }
}
class Vehicle {
    private final String regNo; private final VehicleType type;
    Vehicle(String regNo, VehicleType type) { this.regNo = regNo; this.type = type; }
    double calculateFee(int minutes) { int hours = (minutes + 59) / 60; return hours * type.getRatePerHour(); }
    @Override public String toString() { return type.getName() + "[" + regNo + "]"; }
}
public class CompositionDemo {
    public static void main(String[] args) {
        VehicleType twoWheeler = new VehicleType("TwoWheeler", 10);
        VehicleType car = new VehicleType("Car", 30);
        VehicleType eBike = new VehicleType("EBike", 5);
        Vehicle[] parked = {new Vehicle("TN01A1", twoWheeler), new Vehicle("TN02B2", car), new Vehicle("TN05E5", eBike)};
        for (Vehicle v : parked) System.out.println(v + " 130 min -> Rs " + v.calculateFee(130));
    }
}