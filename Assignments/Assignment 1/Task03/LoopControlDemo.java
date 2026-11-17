public class LoopControlDemo {
    static int[] firstFree(String[][] slots) {
        for (int f = 0; f < slots.length; f++)
            for (int s = 0; s < slots[f].length; s++)
                if (slots[f][s] == null) return new int[]{f, s};
        return null;
    }
    static int countFree(String[][] slots) {
        int free = 0;
        for (String[] floor : slots) for (String vehicle : floor) {
            if (vehicle != null) continue;
            free++;
        }
        return free;
    }
    static int[] firstFreeWithBreak(String[][] slots) {
        int foundFloor = -1, foundSlot = -1;
        search:
        for (int f = 0; f < slots.length; f++)
            for (int s = 0; s < slots[f].length; s++)
                if (slots[f][s] == null) { foundFloor = f; foundSlot = s; break search; }
        return foundFloor < 0 ? null : new int[]{foundFloor, foundSlot};
    }
    public static void main(String[] args) {
        String[][] slots = {{"A", "B"}, {"C", null, "E"}, {null}};
        System.out.println("firstFree = " + java.util.Arrays.toString(firstFree(slots)));
        System.out.println("firstFreeWithBreak = " + java.util.Arrays.toString(firstFreeWithBreak(slots)));
        System.out.println("countFree = " + countFree(slots));
    }
}