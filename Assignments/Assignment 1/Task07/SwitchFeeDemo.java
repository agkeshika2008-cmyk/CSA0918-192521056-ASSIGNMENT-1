public class SwitchFeeDemo {
    enum Kind { TWO_WHEELER, CAR, EV }
    static class TypedVehicle {
        private final String regNo; private final Kind kind;
        TypedVehicle(String regNo, Kind kind) { this.regNo = regNo; this.kind = kind; }
        double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            switch (kind) {
                case TWO_WHEELER: return hours * 10;
                case CAR: case EV: return hours * 30;
                default: throw new IllegalStateException("Unhandled type " + kind);
            }
        }
        double dailyMaximum() {
            switch (kind) {
                case TWO_WHEELER: return 80;
                case CAR: case EV: return 240;
                default: throw new IllegalStateException("Unhandled type " + kind);
            }
        }
        String getRegNo() { return regNo; }
    }
    public static void main(String[] args) {
        TypedVehicle[] vehicles = {
            new TypedVehicle("TN01A1", Kind.TWO_WHEELER),
            new TypedVehicle("TN02B2", Kind.CAR), new TypedVehicle("TN03C3", Kind.EV)
        };
        for (TypedVehicle v : vehicles)
            System.out.println(v.kind + " " + v.getRegNo() + " 130 min -> Rs " + v.calculateFee(130));
    }
}