package parking.model;

import java.time.LocalDateTime;

public final class ParkingTicket implements Comparable<ParkingTicket>, Reportable {
    private static int lastTicketNo;
    static { lastTicketNo = 1000; }
    private static int nextTicketNo() { return ++lastTicketNo; }
    public static int getLastTicketNo() { return lastTicketNo; }
    private final int ticketNo;
    private final Vehicle vehicle;
    private final ParkingUser user;
    private final int floor, slot;
    private final LocalDateTime entryTime;
    public ParkingTicket(Vehicle vehicle, int floor, int slot, LocalDateTime entryTime) {
        this(vehicle, new Visitor(), floor, slot, entryTime);
    }
    public ParkingTicket(Vehicle vehicle, ParkingUser user, int floor, int slot, LocalDateTime entryTime) {
        this(nextTicketNo(), vehicle, user, floor, slot, entryTime);
    }
    public ParkingTicket(ParkingTicket other) {
        this(other.ticketNo, other.vehicle, other.user, other.floor, other.slot, other.entryTime);
    }
    private ParkingTicket(int ticketNo, Vehicle vehicle, ParkingUser user, int floor, int slot, LocalDateTime entryTime) {
        if (vehicle == null || user == null || entryTime == null)
            throw new IllegalArgumentException("Vehicle, user and entry time are required");
        if (floor < 0 || slot < 0) throw new IllegalArgumentException("Floor and slot must not be negative");
        this.ticketNo = ticketNo; this.vehicle = vehicle; this.user = user;
        this.floor = floor; this.slot = slot; this.entryTime = entryTime;
    }
    public int getTicketNo() { return ticketNo; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingUser getUser() { return user; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    @Override public String reportLine() {
        return "Ticket " + ticketNo + " | " + vehicle + " | " + user + " | floor " + floor + " slot " + slot + " | in " + entryTime;
    }
    @Override public int compareTo(ParkingTicket other) { return Integer.compare(ticketNo, other.ticketNo); }
    @Override public boolean equals(Object o) { return o instanceof ParkingTicket t && t.ticketNo == ticketNo; }
    @Override public int hashCode() { return Integer.hashCode(ticketNo); }
    @Override public String toString() { return "ParkingTicket#" + ticketNo; }
}