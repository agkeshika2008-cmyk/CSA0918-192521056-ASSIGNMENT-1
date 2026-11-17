public class FeeSlabDemo {
    static double rateFor(String type) {
        switch (type) {
            case "TWO_WHEELER": return 10;
            case "CAR": case "EV": return 30;
            default: throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
    static double parkingFee(String type, int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Minutes must not be negative");
        double rate = rateFor(type);
        if (minutes == 0) return 0;
        int hours = (minutes + 59) / 60;
        return Math.min(hours, 8) * rate;
    }
    public static void main(String[] args) {
        String[] types = {"TWO_WHEELER", "CAR", "EV", "CAR", "CAR", "CAR", "CAR"};
        int[] minutes = {130, 130, 130, 0, 60, 61, 481};
        for (int i = 0; i < types.length; i++)
            System.out.println(types[i] + " " + minutes[i] + " min -> Rs " + parkingFee(types[i], minutes[i]));
        try { parkingFee("BUS", 30); }
        catch (IllegalArgumentException e) { System.out.println("BUS -> " + e.getMessage()); }
    }
}