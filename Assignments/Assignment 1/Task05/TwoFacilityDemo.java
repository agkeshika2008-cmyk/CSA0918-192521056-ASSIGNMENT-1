public class TwoFacilityDemo {
    static class FacilityWithOwnCounter {
        private int nextTicket = 1001;
        int issue() { return nextTicket++; }
    }
    static class GlobalCounter {
        private static int next = 1001;
        static int issue() { return next++; }
    }
    static class PrefixedCounter {
        private final String code; private int next = 1001;
        PrefixedCounter(String code) { this.code = code; }
        String issue() { return code + "-" + next++; }
    }
    public static void main(String[] args) {
        FacilityWithOwnCounter north = new FacilityWithOwnCounter(), south = new FacilityWithOwnCounter();
        System.out.println("Instance counters: north " + north.issue() + ", south " + south.issue());
        System.out.println("Fix 1 (static): " + GlobalCounter.issue() + ", " + GlobalCounter.issue() + ", " + GlobalCounter.issue());
        PrefixedCounter n = new PrefixedCounter("N"), s = new PrefixedCounter("S");
        System.out.println("Fix 2 (prefixed): " + n.issue() + ", " + s.issue() + ", " + n.issue());
    }
}