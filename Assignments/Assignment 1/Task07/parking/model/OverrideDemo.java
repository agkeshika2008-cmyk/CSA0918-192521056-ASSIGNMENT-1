package parking.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OverrideDemo {
    static class BadVehicle {
        final String regNo;
        BadVehicle(String regNo) { this.regNo = regNo; }
        @Override public boolean equals(Object o) { return o instanceof BadVehicle b && b.regNo.equals(regNo); }
    }
    public static void main(String[] args) {
        Vehicle[] vehicles = {new TwoWheeler("TN01A1"), new Car("TN02B2"), new ElectricCar("TN03C3")};
        for (Vehicle v : vehicles)
            System.out.println(v + " rate/hour = " + v.ratePerHour() + ", 130 min -> Rs " + v.calculateFee(130));
        System.out.println("toString(): " + vehicles[2]);
        Vehicle a = new Car("TN02B2"), b = new Car("  tn02b2 "), c = new ElectricCar("TN02B2");
        System.out.println("a.equals(b): " + a.equals(b));
        System.out.println("a.equals(c): " + a.equals(c));
        System.out.println("a == b: " + (a == b));
        System.out.println("a.hashCode() == c.hashCode(): " + (a.hashCode() == c.hashCode()));
        System.out.println("a.equals(null): " + a.equals(null));
        System.out.println("a.equals(String): " + a.equals("TN02B2"));
        Set<Vehicle> parked = new HashSet<>(); parked.add(a);
        System.out.println("HashSet.add(second same regNo): " + parked.add(c) + ", size = " + parked.size());
        Map<Vehicle, String> slotOf = new HashMap<>(); slotOf.put(a, "floor 0 slot 1");
        System.out.println("HashMap lookup with equal object: " + slotOf.get(b));
        Set<BadVehicle> bad = new HashSet<>();
        bad.add(new BadVehicle("TN09Z9")); bad.add(new BadVehicle("TN09Z9"));
        System.out.println("BadVehicle equals-only set size = " + bad.size());
        final int slotsPerRow = 10;
        System.out.println("final slotsPerRow = " + slotsPerRow + "; DAILY_MAX_HOURS = " + Vehicle.DAILY_MAX_HOURS);
    }
}