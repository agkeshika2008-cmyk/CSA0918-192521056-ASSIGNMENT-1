package parking.model;

public class StayRecord {
    private Integer ticketNo;
    private int durationMinutes;
    private int floor;
    private int slot;
    boolean billed;
    public Integer getTicketNo() { return ticketNo; }
    public int getDurationMinutes() { return durationMinutes; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public boolean isBilled() { return billed; }
    public void setTicketNo(Integer ticketNo) {
        if (ticketNo != null && ticketNo <= 0) throw new IllegalArgumentException("Ticket number must be positive");
        this.ticketNo = ticketNo;
    }
    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes < 0) throw new IllegalArgumentException("Duration must not be negative: " + durationMinutes);
        this.durationMinutes = durationMinutes;
    }
    public void setSlot(int floor, int slot) {
        if (floor < 0 || slot < 0) throw new IllegalArgumentException("Invalid slot position: floor " + floor + ", slot " + slot);
        this.floor = floor; this.slot = slot;
    }
    void markBilled() { billed = true; }
    protected String summary() { return "ticket " + ticketNo + ", " + durationMinutes + " min, floor " + floor + " slot " + slot; }
}