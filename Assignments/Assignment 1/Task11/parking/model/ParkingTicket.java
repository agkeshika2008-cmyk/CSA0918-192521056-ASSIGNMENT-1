package parking.model;
import java.time.LocalDateTime;
public final class ParkingTicket implements Comparable<ParkingTicket>,Reportable {
    private static int lastTicketNo;
    static {lastTicketNo=1000;}
    private static int nextTicketNo(){return ++lastTicketNo;}
    public static int getLastTicketNo(){return lastTicketNo;}
    private final int ticketNo;
    private final Vehicle vehicle;
    private final ParkingUser user;
    private final int floor,slot;
    private final LocalDateTime entryTime;
    public ParkingTicket(Vehicle v,int f,int s,LocalDateTime t){this(v,new Visitor(),f,s,t);}
    public ParkingTicket(Vehicle v,ParkingUser u,int f,int s,LocalDateTime t){this(nextTicketNo(),v,u,f,s,t);}
    public ParkingTicket(ParkingTicket other){this(other.ticketNo,other.vehicle,other.user,other.floor,other.slot,other.entryTime);}
    private ParkingTicket(int n,Vehicle v,ParkingUser u,int f,int s,LocalDateTime t){
        if(v==null||u==null||t==null)throw new IllegalArgumentException("Vehicle, user and entry time are required");
        if(f<0||s<0)throw new IllegalArgumentException("Floor and slot must not be negative");
        ticketNo=n;vehicle=v;user=u;floor=f;slot=s;entryTime=t;
    }
    public int getTicketNo(){return ticketNo;} public Vehicle getVehicle(){return vehicle;}
    public ParkingUser getUser(){return user;} public int getFloor(){return floor;}
    public int getSlot(){return slot;} public LocalDateTime getEntryTime(){return entryTime;}
    @Override public String reportLine(){return "Ticket "+ticketNo+" | "+vehicle+" | "+user+" | floor "+floor+" slot "+slot+" | in "+entryTime;}
    @Override public int compareTo(ParkingTicket o){return Integer.compare(ticketNo,o.ticketNo);}
    @Override public boolean equals(Object o){return o instanceof ParkingTicket t&&t.ticketNo==ticketNo;}
    @Override public int hashCode(){return Integer.hashCode(ticketNo);}
    @Override public String toString(){return "ParkingTicket#"+ticketNo;}
}
