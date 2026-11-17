package parking.app;
import java.time.LocalDate;
import java.time.LocalDateTime;
import parking.model.*;
import parking.service.*;
public class Main {
    static final LocalDate DAY=LocalDate.of(2026,10,27);
    static LocalDateTime at(int h,int m){return DAY.atTime(h,m);}
    public static void main(String[] args){
        ParkingService s=new ParkingService(new ParkingFacility(new int[]{3,3}));
        s.checkIn(VehicleFactory.create("TWO_WHEELER","TN01A1"),new Visitor("Arun"),at(9,0));
        s.checkIn(VehicleFactory.create("CAR","TN02B2"),new Visitor("Bala"),at(9,15));
        s.checkIn(VehicleFactory.create("EV","TN03C3"),new Visitor("Chitra"),at(10,0));
        s.checkIn(VehicleFactory.create("CAR","TN04D4"),new Student("Deepa",DAY.plusDays(10)),at(8,0));
        s.checkIn(VehicleFactory.create("CAR","TN05E5"),new Visitor("Esha"),at(8,0));
        s.checkIn(VehicleFactory.create("TWO_WHEELER","TN06F6"),new Staff("Farid",DAY.minusDays(1)),at(12,0));
        System.out.println("TN01A1 -> Rs "+s.checkOut("TN01A1",at(10,30)));
        System.out.println("TN02B2 -> Rs "+s.checkOut("TN02B2",at(12,15)));
        System.out.println("TN03C3 -> Rs "+s.checkOut("TN03C3",at(13,30),15));
        System.out.println("TN04D4 -> Rs "+s.checkOut("TN04D4",at(17,0)));
        System.out.println("TN05E5 -> Rs "+s.checkOut("TN05E5",at(18,0)));
        System.out.println("TN06F6 -> Rs "+s.checkOut("TN06F6",at(12,45)));
        System.out.println("Total collection: Rs "+s.getTotalCollection());
        System.out.println("Vehicles still parked: "+s.getActiveCount());
    }
}
