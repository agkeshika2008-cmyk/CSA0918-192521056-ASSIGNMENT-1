import parking.model.*;
import java.time.LocalDateTime;

public class TicketAlternatives {
    static class TicketBean {
        private int ticketNo; private Vehicle vehicle; private int floor, slot; private LocalDateTime entryTime;
        public void setTicketNo(int ticketNo) { this.ticketNo = ticketNo; }
        public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
        public void setFloor(int floor) { this.floor = floor; }
        public void setSlot(int slot) { this.slot = slot; }
        public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }
        public Vehicle getVehicle() { return vehicle; }
    }
    static final class TicketFactory {
        private TicketFactory() { }
        static ParkingTicket forVisitor(Vehicle v, int floor, int slot, LocalDateTime in) {
            return new ParkingTicket(v, new Visitor(), floor, slot, in);
        }
        static ParkingTicket forStudent(Vehicle v, String name, int floor, int slot, LocalDateTime in) {
            return new ParkingTicket(v, new Student(name), floor, slot, in);
        }
    }
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.of(2026, 10, 27, 9, 0);
        TicketBean half = new TicketBean();
        System.out.println("Setter approach: vehicle is " + half.getVehicle() + " -> invalid ticket already exists");
        try { half.getVehicle().getRegNo(); }
        catch (NullPointerException e) { System.out.println("Using it fails later with: NullPointerException"); }
        ParkingTicket t = TicketFactory.forStudent(new Car("TN02B2"), "Deepa", 0, 1, now);
        System.out.println("Factory approach: " + t.reportLine());
    }
}