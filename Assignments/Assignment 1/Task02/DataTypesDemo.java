import java.util.Scanner;

/** Task 2 - data types, casting risks and input. */
public class DataTypesDemo {
    static final double CAR_RATE = 30;
    public static void main(String[] args) {
        String regNo; int minutes;
        if (args.length >= 2) { regNo = args[0]; minutes = Integer.parseInt(args[1]); }
        else {
            Scanner in = new Scanner(System.in);
            System.out.print("Registration number: "); regNo = in.hasNext() ? in.next() : "TN02CD2222";
            System.out.print("Minutes parked: "); minutes = in.hasNextInt() ? in.nextInt() : 130;
            System.out.println();
        }
        System.out.println("Vehicle " + regNo + " parked for " + minutes + " minutes at Rs " + CAR_RATE + "/hour\n");
        int wrongHours = minutes / 60;
        System.out.println("WRONG 1  int hours = minutes / 60 -> " + wrongHours + " h -> Rs " + wrongHours * CAR_RATE);
        double wrongHours2 = minutes / 60;
        System.out.println("WRONG 2  double hours = minutes / 60 -> " + wrongHours2 + " h -> Rs " + wrongHours2 * CAR_RATE);
        int wrongHours3 = (int) (minutes / 60.0);
        System.out.println("WRONG 3  (int) (minutes / 60.0) -> " + wrongHours3 + " h -> Rs " + wrongHours3 * CAR_RATE);
        long wrongHours4 = Math.round(minutes / 60.0);
        System.out.println("WRONG 4  Math.round(minutes / 60.0) -> " + wrongHours4 + " h -> Rs " + wrongHours4 * CAR_RATE);
        int hours = (minutes + 59) / 60;
        double fee = hours * CAR_RATE;
        System.out.println("CORRECT  (minutes + 59) / 60 -> " + hours + " h -> Rs " + fee);
        double rupees = 90.99; int narrowed = (int) rupees;
        System.out.println("\n(int) 90.99 = " + narrowed + " -> paise lost");
        long bigMinutes = 3_000_000_000L;
        System.out.println("(int) 3000000000L = " + (int) bigMinutes + " -> overflow; use long");
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
    }
}