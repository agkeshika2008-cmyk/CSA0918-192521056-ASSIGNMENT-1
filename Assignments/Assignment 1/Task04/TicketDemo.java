import parking.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketDemo {
    public static void main(String[] args) {
        LocalDateTime nine = LocalDate.of(2026, 10, 27).atTime(9, 0);
        Visitor guest = new Visitor();
        Student noPass = new Student("Deepa");
        Student withPass = new Student("Deepa", LocalDate.of(2026, 11, 15));
        System.out.println(guest + " | " + noPass + " | " + withPass);
        System.out.println("noPass exempt? " + noPass.isFeeExempt(nine.toLocalDate()));
        System.out.println("withPass exempt? " + withPass.isFeeExempt(nine.toLocalDate()));
        ParkingTicket t1 = new ParkingTicket(new Car("TN02B2"), withPass, 0, 1, nine);
        ParkingTicket t2 = new ParkingTicket(new TwoWheeler("TN01A1"), 0, 2, nine);
        System.out.println(t1.reportLine()); System.out.println(t2.reportLine());
        ParkingTicket duplicate = new ParkingTicket(t1);
        ParkingTicket alias = t1;
        System.out.println("duplicate.getTicketNo() == t1.getTicketNo() : " + (duplicate.getTicketNo() == t1.getTicketNo()));
        System.out.println("duplicate == t1 : " + (duplicate == t1));
        System.out.println("duplicate.equals(t1) : " + duplicate.equals(t1));
        System.out.println("alias == t1 : " + (alias == t1));
        System.out.println("Next ticket number: " + new ParkingTicket(new Car("TN03C3"), 0, 3, nine).getTicketNo());
        try { new ParkingTicket(null, 0, 0, nine); }
        catch (IllegalArgumentException e) { System.out.println("null vehicle rejected: " + e.getMessage()); }
        try { new ParkingTicket(new Car("TN04D4"), -1, 0, nine); }
        catch (IllegalArgumentException e) { System.out.println("negative floor rejected: " + e.getMessage()); }
    }
}