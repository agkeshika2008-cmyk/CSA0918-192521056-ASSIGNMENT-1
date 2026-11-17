public class PublicFieldsDemo {
    static class OpenRecord {
        public int durationMinutes;
        public int floor;
        public int slot;
    }
    static void entryScreen(OpenRecord r, int minutes) {
        if (minutes < 0) return;
        r.durationMinutes = minutes;
    }
    static void importFromFile(OpenRecord r, int minutes) { r.durationMinutes = minutes; }
    public static void main(String[] args) {
        OpenRecord r = new OpenRecord();
        entryScreen(r, 130);
        System.out.println("after entryScreen(130): " + r.durationMinutes);
        entryScreen(r, -5);
        System.out.println("after entryScreen(-5): " + r.durationMinutes);
        importFromFile(r, -5);
        System.out.println("after importFromFile(-5): " + r.durationMinutes);
        r.floor = 99;
        System.out.println("floor set directly to: " + r.floor);
    }
}