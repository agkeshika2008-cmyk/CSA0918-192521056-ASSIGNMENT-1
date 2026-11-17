package parking.model;
public interface Chargeable {
    double RATE_PER_KWH = 12.0;
    double chargingFee(int kWh);
    default String chargingSummary(int kWh) { return kWh + " kWh -> Rs " + chargingFee(kWh); }
    static boolean isValidUnits(int kWh) { return kWh >= 0; }
}
