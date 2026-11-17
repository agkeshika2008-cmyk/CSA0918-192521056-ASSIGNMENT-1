package parking.service;
import java.time.LocalDateTime;
import parking.model.*;
public class ParkingService {
    private final ParkingFacility facility;
    private final ParkingTicket[] active;
    private int activeCount;
    private double totalCollection;
    public ParkingService(ParkingFacility f){
        if(f==null)throw new IllegalArgumentException("Facility is required");
        facility=f;active=new ParkingTicket[f.capacity()];
    }
    public ParkingTicket findTicket(String r){int i=indexOf(Vehicle.normalise(r));return i<0?null:active[i];}
    private int indexOf(String r){
        for(int i=0;i<activeCount;i++)if(active[i].getVehicle().getRegNo().equals(r))return i;
        return -1;
    }
    public ParkingTicket checkIn(Vehicle v,ParkingUser u,LocalDateTime in){
        if(v==null||u==null||in==null)throw new IllegalArgumentException("Vehicle, user and entry time are required");
        if(indexOf(v.getRegNo())>=0)throw new ParkingException("Duplicate registration number: "+v.getRegNo());
        int[] p=facility.allocate(v.getRegNo());
        if(p==null)throw new ParkingException("Parking facility is full");
        ParkingTicket t=new ParkingTicket(v,u,p[0],p[1],in);active[activeCount++]=t;return t;
    }
    public double checkOut(String r,LocalDateTime out){return checkOut(r,out,0);}
    public double checkOut(String r,LocalDateTime out,int kWh){
        if(out==null)throw new IllegalArgumentException("Exit time is required");
        if(kWh<0)throw new IllegalArgumentException("kWh must not be negative");
        int i=indexOf(Vehicle.normalise(r));
        if(i<0)throw new ParkingException("No active ticket for "+r);
        ParkingTicket t=active[i];Vehicle v=t.getVehicle();
        if(out.isBefore(t.getEntryTime()))throw new IllegalArgumentException("Exit time is before entry time");
        if(kWh>0&&!(v instanceof Chargeable))throw new IllegalArgumentException(v+" cannot be charged");
        double parking=0;
        if(!t.getUser().isFeeExempt(t.getEntryTime().toLocalDate()))parking=v.calculateFee(t.getEntryTime(),out);
        double charge=(v instanceof Chargeable c)?c.chargingFee(kWh):0;
        double bill=parking+charge;
        facility.release(t.getFloor(),t.getSlot());active[i]=active[--activeCount];active[activeCount]=null;
        totalCollection+=bill;return bill;
    }
    public int getActiveCount(){return activeCount;}
    public double getTotalCollection(){return totalCollection;}
}
