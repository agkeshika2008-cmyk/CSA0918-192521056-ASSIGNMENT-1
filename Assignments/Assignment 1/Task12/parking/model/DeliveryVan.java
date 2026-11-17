package parking.model;
import java.time.Duration;
import java.time.LocalDateTime;
public class DeliveryVan extends Vehicle {
    private static final double CAR_RATE=30;
    private static final int PEAK_START_HOUR=9,PEAK_END_HOUR=17;
    public DeliveryVan(String r){super(r);}
    @Override protected double ratePerHour(){return CAR_RATE;}
    @Override public double dailyMaximum(){return DAILY_MAX_HOURS*CAR_RATE*2;}
    private double rateAt(LocalDateTime t){int h=t.getHour();return h>=PEAK_START_HOUR&&h<PEAK_END_HOUR?2*CAR_RATE:CAR_RATE;}
    @Override public double calculateFee(LocalDateTime in,LocalDateTime out){
        if(in==null||out==null)throw new IllegalArgumentException("Entry and exit time are required");
        long remaining=Duration.between(in,out).toMinutes();
        if(remaining<0)throw new IllegalArgumentException("Exit time is before entry time");
        double total=0;LocalDateTime start=in;
        while(remaining>0){
            long chunk=Math.min(remaining,MINUTES_PER_DAY),blocks=(chunk+59)/60;double fee=0;
            for(long i=0;i<blocks;i++)fee+=rateAt(start.plusHours(i));
            total+=Math.min(fee,dailyMaximum());start=start.plusMinutes(chunk);remaining-=chunk;
        }
        return total;
    }
}
