package parking.app;

import parking.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class AbstractionDemo {
    public static void main(String[] args) {
        Vehicle ev = new ElectricCar("TN03C3");
        System.out.println(ev + " fee for 130 min = Rs " + ev.calculateFee(130));
        if (ev instanceof Chargeable c) System.out.println("default method: " + c.chargingSummary(10));
        System.out.println("static method: Chargeable.isValidUnits(-5) = " + Chargeable.isValidUnits(-5));
        System.out.println("constant: Chargeable.RATE_PER_KWH = " + Chargeable.RATE_PER_KWH);
        LocalDate today = LocalDate.of(2026, 10, 27);
        Payable[] users = {new Student("Deepa", today.plusDays(3)), new Staff("Farid"), new Visitor("Arun")};
        for (Payable p : users) System.out.println(p + " exempt today? " + p.isFeeExempt(today));
        LocalDateTime nine = today.atTime(9, 0);
        ParkingTicket t3 = new ParkingTicket(new Car("C3"), 0, 2, nine);
        ParkingTicket t1 = new ParkingTicket(new Car("C1"), 0, 0, nine);
        ParkingTicket t2 = new ParkingTicket(new Car("C2"), 0, 1, nine);
        ParkingTicket[] tickets = {t2, t3, t1};
        Arrays.sort(tickets);
        for (Reportable r : tickets) System.out.println(r.reportLine());
    }
}