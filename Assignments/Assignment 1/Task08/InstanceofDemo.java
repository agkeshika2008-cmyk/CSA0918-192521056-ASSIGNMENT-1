public class InstanceofDemo {
    static double rateOf(Vehicle v) {
        if (v instanceof ElectricCar) return 30;
        else if (v instanceof Car) return 30;
        else if (v instanceof TwoWheeler) return 10;
        else throw new IllegalArgumentException("Unknown vehicle class " + v.getClass().getSimpleName());
    }
    static double fee(Vehicle v, int minutes) {
        int hours = (minutes + 59) / 60;
        double fee = hours * rateOf(v);
        if (v instanceof ElectricCar) fee += ((ElectricCar) v).chargingFee(10);
        return fee;
    }
    public static void main(String[] args) {
        Vehicle[] parked = {new TwoWheeler("TN01A1"), new Car("TN02B2"), new ElectricCar("TN03C3")};
        for (Vehicle v : parked)
            System.out.println(v.getClass().getSimpleName() + " " + v.getRegNo() + " -> Rs " + fee(v, 130));
    }
}