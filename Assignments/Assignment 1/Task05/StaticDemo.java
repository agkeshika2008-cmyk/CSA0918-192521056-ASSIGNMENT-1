import parking.model.*;
import parking.service.*;
import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StaticDemo {
    static void describe(ParkingTicket t) {
        System.out.println("  inside describe(): " + t.reportLine());
        t = null;
    }
    static void tryToChange(int floor) { floor = 99; }
    static ParkingTicket issueTicket(ParkingService service, String regNo, LocalDateTime in) {
        return service.checkIn(new Car(regNo), new Visitor(), in);
    }
    public static void main(String[] args) throws InterruptedException {
        LocalDateTime nine = LocalDate.of(2026, 10, 27).atTime(9, 0);
        System.out.println("Counter before any ticket: " + ParkingTicket.getLastTicketNo());
        ParkingService north = new ParkingService(new ParkingFacility(new int[]{2, 2}));
        ParkingService south = new ParkingService(new ParkingFacility(new int[]{3}));
        ParkingTicket a = issueTicket(north, "TN01A1", nine);
        ParkingTicket b = issueTicket(south, "TN02B2", nine);
        ParkingTicket c = issueTicket(north, "TN03C3", nine);
        System.out.println("North ticket " + a.getTicketNo() + ", south ticket " + b.getTicketNo()
                + ", north ticket " + c.getTicketNo());
        System.out.println("Counter after three tickets: " + ParkingTicket.getLastTicketNo());
        System.out.println("Vehicle.normalise: " + Vehicle.normalise("  tn09xy9999 "));
        System.out.println("Passing a ticket to a method:");
        describe(a);
        System.out.println("after describe(), a is still: " + a);
        int floor = 1; tryToChange(floor);
        System.out.println("primitive floor after tryToChange: " + floor);
        ParkingTicket tracked = issueTicket(north, "TN04D4", nine);
        WeakReference<ParkingTicket> watcher = new WeakReference<>(tracked);
        System.out.println("Ticket reachable while parked: " + (watcher.get() != null));
        north.checkOut("TN04D4", nine.plusHours(1));
        tracked = null;
        for (int i = 0; i < 5 && watcher.get() != null; i++) { System.gc(); Thread.sleep(50); }
        System.out.println("Ticket collected after exit (GC not guaranteed): " + (watcher.get() == null));
    }
}