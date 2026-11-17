package parking.app;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parking.model.Car;
import parking.model.ParkingTicket;
import parking.model.Visitor;

public class LookupBenchmark {
    static int sink;
    public static void main(String[] args) {
        int[] sizes = {500, 5_000, 50_000};
        LocalDateTime now = LocalDateTime.of(2026, 10, 27, 9, 0);
        System.out.printf("%-8s %-22s %-22s %-22s%n", "n", "array (ns/find)", "ArrayList (ns/find)", "HashMap (ns/find)");
        for (int n : sizes) {
            ParkingTicket[] array = new ParkingTicket[n];
            List<ParkingTicket> list = new ArrayList<>();
            Map<String, ParkingTicket> map = new HashMap<>();
            for (int i = 0; i < n; i++) {
                ParkingTicket t = new ParkingTicket(new Car("TN" + (100000 + i)), new Visitor(), 0, i, now);
                array[i] = t; list.add(t); map.put(t.getVehicle().getRegNo(), t);
            }
            String target = "TN" + (100000 + n - 1);
            int repeats = 10_000_000 / n;
            for (int w = 0; w < 3; w++) { arr(array, target, repeats); lst(list, target, repeats); mp(map, target, repeats * 50); }
            long t0 = System.nanoTime(); arr(array, target, repeats);
            double a = (double) (System.nanoTime() - t0) / repeats;
            t0 = System.nanoTime(); lst(list, target, repeats);
            double b = (double) (System.nanoTime() - t0) / repeats;
            t0 = System.nanoTime(); mp(map, target, repeats * 50);
            double c = (double) (System.nanoTime() - t0) / (repeats * 50.0);
            System.out.printf("%-8d %-22.1f %-22.1f %-22.1f%n", n, a, b, c);
        }
    }
    static void arr(ParkingTicket[] a, String target, int repeats) {
        for (int r = 0; r < repeats; r++)
            for (int i = 0; i < a.length; i++)
                if (a[i].getVehicle().getRegNo().equals(target)) { sink += i; break; }
    }
    static void lst(List<ParkingTicket> l, String target, int repeats) {
        for (int r = 0; r < repeats; r++)
            for (int i = 0; i < l.size(); i++)
                if (l.get(i).getVehicle().getRegNo().equals(target)) { sink += i; break; }
    }
    static void mp(Map<String, ParkingTicket> m, String target, int repeats) {
        for (int r = 0; r < repeats; r++) sink += m.get(target).getSlot();
    }
}