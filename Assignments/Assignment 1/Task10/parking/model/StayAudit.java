package parking.model;

public final class StayAudit {
    private StayAudit() { }
    public static String finish(StayRecord record) {
        record.markBilled();
        return record.summary() + ", billed = " + record.isBilled();
    }
}