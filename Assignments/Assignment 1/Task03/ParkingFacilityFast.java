public class ParkingFacilityFast {
    private final String[][] slots;
    private final int[] freeCount;
    private final int[][] freeStack;
    private final int[] top;
    public ParkingFacilityFast(int[] slotsPerFloor) {
        int floors = slotsPerFloor.length;
        slots = new String[floors][]; freeCount = new int[floors];
        freeStack = new int[floors][]; top = new int[floors];
        for (int f = 0; f < floors; f++) {
            int n = slotsPerFloor[f]; slots[f] = new String[n]; freeStack[f] = new int[n];
            for (int i = 0; i < n; i++) freeStack[f][i] = n - 1 - i;
            top[f] = n; freeCount[f] = n;
        }
    }
    public int[] allocate(String vehicleNo) {
        for (int f = 0; f < slots.length; f++) {
            if (freeCount[f] == 0) continue;
            int s = freeStack[f][--top[f]];
            slots[f][s] = vehicleNo; freeCount[f]--;
            return new int[]{f, s};
        }
        return null;
    }
    public boolean release(int floor, int slot) {
        if (floor < 0 || floor >= slots.length) return false;
        if (slot < 0 || slot >= slots[floor].length) return false;
        if (slots[floor][slot] == null) return false;
        slots[floor][slot] = null; freeStack[floor][top[floor]++] = slot; freeCount[floor]++;
        return true;
    }
    public static void main(String[] args) {
        ParkingFacilityFast p = new ParkingFacilityFast(new int[]{2, 1});
        System.out.println(java.util.Arrays.toString(p.allocate("TN01AB1111")));
        System.out.println(java.util.Arrays.toString(p.allocate("TN02CD2222")));
        System.out.println(java.util.Arrays.toString(p.allocate("TN03EF3333")));
        System.out.println(java.util.Arrays.toString(p.allocate("TN04GH4444")));
        System.out.println(p.release(0, 1) + " " + p.release(0, 1) + " " + p.release(5, 0));
        System.out.println(java.util.Arrays.toString(p.allocate("TN04GH4444")));
    }
}