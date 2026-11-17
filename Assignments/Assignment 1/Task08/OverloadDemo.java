import java.time.LocalTime;

public class OverloadDemo {
    static double calculateFee(Vehicle v, int minutes) { return v.calculateFee(minutes); }
    static double calculateFee(Vehicle v, LocalTime in, LocalTime out) {
        if (out.isBefore(in)) throw new IllegalArgumentException("Exit is before entry");
        int minutes = (int) java.time.Duration.between(in, out).toMinutes();
        return calculateFee(v, minutes);
    }
    static String describe(Vehicle v) { return "describe(Vehicle)"; }
    static String describe(Car c) { return "describe(Car)"; }
    static String describe(ElectricCar e) { return "describe(ElectricCar)"; }
    static String charge(int kWh) { return "charge(int)"; }
    static String charge(double kWh) { return "charge(double)"; }
    public static void main(String[] args) {
        Vehicle car = new Car("TN02B2");
        System.out.println("calculateFee(v, 130) = " + calculateFee(car, 130));
        System.out.println("calculateFee(v, 09:00, 11:10) = " + calculateFee(car, LocalTime.of(9, 0), LocalTime.of(11, 10)));
        System.out.println("charge(10) -> " + charge(10));
        System.out.println("charge(10.5) -> " + charge(10.5));
        System.out.println("charge('A') -> " + charge('A'));
        Vehicle v = new ElectricCar("TN03C3"); Car c = new ElectricCar("TN03C3"); ElectricCar e = new ElectricCar("TN03C3");
        System.out.println("describe(Vehicle ref) -> " + describe(v));
        System.out.println("describe(Car ref) -> " + describe(c));
        System.out.println("describe(EV ref) -> " + describe(e));
        System.out.println("v.ratePerHour() = " + v.ratePerHour());
        Vehicle[] parked = {new TwoWheeler("TN01A1"), new Car("TN02B2"), new ElectricCar("TN03C3")};
        for (Vehicle p : parked) System.out.println("actual " + p.getClass().getSimpleName() + " -> rate " + p.ratePerHour());
    }
}