import java.util.ArrayList;
import java.util.List;

/** Task 2 - alternative storage for floors with different slot counts. */
public class SlotStorageDemo {
    public static void main(String[] args) {
        int[] slotsPerFloor = {20, 30, 25};
        String[][] jagged = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) jagged[f] = new String[slotsPerFloor[f]];
        jagged[1][7] = "TN01AB1111";
        int[] offset = new int[slotsPerFloor.length + 1];
        for (int f = 0; f < slotsPerFloor.length; f++) offset[f + 1] = offset[f] + slotsPerFloor[f];
        String[] flat = new String[offset[slotsPerFloor.length]];
        int rightIndex = offset[1] + 7;
        flat[rightIndex] = "TN01AB1111";
        System.out.println("flat array length = " + flat.length + ", offset table = " + java.util.Arrays.toString(offset));
        System.out.println("floor 1, slot 7 -> index " + rightIndex);
        int wrongFloor = 2, wrongSlot = 0;
        System.out.println("naive formula = " + (wrongFloor * 20 + wrongSlot) + ", correct index = " + (offset[2] + wrongSlot));
        List<String[]> list = new ArrayList<>();
        for (int size : slotsPerFloor) list.add(new String[size]);
        list.get(1)[7] = "TN01AB1111"; list.add(new String[40]);
        System.out.println("ArrayList floors after adding one = " + list.size());
        System.out.println("jagged[1][7]=" + jagged[1][7] + " flat[" + rightIndex + "]=" + flat[rightIndex]
                + " list.get(1)[7]=" + list.get(1)[7]);
    }
}