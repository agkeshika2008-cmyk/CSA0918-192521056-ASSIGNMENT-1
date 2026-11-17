package parking.app;

import java.util.ArrayList;
import java.util.List;
import parking.model.StayAudit;
import parking.model.StayRecord;
import parking.model.Student;

public class EncapsulationDemo {
    public static void main(String[] args) {
        StayRecord record = new StayRecord();
        record.setDurationMinutes(130);
        record.setSlot(0, 1);
        record.setTicketNo(1001);
        System.out.println("valid record: " + StayAudit.finish(record));
        try { record.setDurationMinutes(-5); }
        catch (IllegalArgumentException e) { System.out.println("rejected: " + e.getMessage()); }
        try { record.setSlot(-1, 3); }
        catch (IllegalArgumentException e) { System.out.println("rejected: " + e.getMessage()); }
        try { record.setTicketNo(0); }
        catch (IllegalArgumentException e) { System.out.println("rejected: " + e.getMessage()); }
        System.out.println("state after rejected updates: " + record.getDurationMinutes() + " min, floor "
                + record.getFloor() + " slot " + record.getSlot() + " (unchanged)");
        try { new Student("   "); }
        catch (IllegalArgumentException e) { System.out.println("rejected: " + e.getMessage()); }
        Integer a = 127, b = 127;
        Integer c = 1000, d = 1000;
        System.out.println("Integer 127 == 127: " + (a == b));
        System.out.println("Integer 1000 == 1000: " + (c == d));
        System.out.println("Integer 1000 .equals: " + c.equals(d));
        StayRecord fresh = new StayRecord();
        try { int n = fresh.getTicketNo(); System.out.println(n); }
        catch (NullPointerException e) { System.out.println("unboxing a null Integer -> NullPointerException"); }
        List<Integer> freeSlots = new ArrayList<>();
        freeSlots.add(10); freeSlots.add(20); freeSlots.add(30);
        freeSlots.remove(1);
        System.out.println("after remove(1): " + freeSlots);
        freeSlots.remove(Integer.valueOf(10));
        System.out.println("after remove(Integer.valueOf(10)): " + freeSlots);
        System.out.println("Integer.parseInt(130) = " + Integer.parseInt("130"));
        try { Integer.parseInt("13O"); }
        catch (NumberFormatException e) { System.out.println("Integer.parseInt(13O) -> NumberFormatException: " + e.getMessage()); }
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE + ", Double.parseDouble(12.5) = " + Double.parseDouble("12.5"));
    }
}