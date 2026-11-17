import java.util.Scanner;

public class DataTypesDemo {

    static final double CAR_RATE = 30;

    public static void main(String[] args) {

        String regNo;
        int minutes;

        if (args.length >= 2) {
            regNo = args[0];
            minutes = Integer.parseInt(args[1]);
        } else {
            Scanner in = new Scanner(System.in);

            System.out.print("Registration number: ");
            regNo = in.hasNext() ? in.next() : "TN02CD2222";

            System.out.print("Minutes parked: ");
            minutes = in.hasNextInt() ? in.nextInt() : 130;
        }

        int wrongHours = minutes / 60;
        double wrongHours2 = minutes / 60;
        int wrongHours3 = (int) (minutes / 60.0);
        long wrongHours4 = Math.round(minutes / 60.0);

        int hours = (minutes + 59) / 60;
        double fee = hours * CAR_RATE;

        double rupees = 90.99;
        int narrowed = (int) rupees;

        long bigMinutes = 3000000000L;

        System.out.println("Vehicle: " + regNo);
        System.out.println("Minutes: " + minutes);
        System.out.println("Wrong calculation 1: " + wrongHours);
        System.out.println("Wrong calculation 2: " + wrongHours2);
        System.out.println("Wrong calculation 3: " + wrongHours3);
        System.out.println("Wrong calculation 4: " + wrongHours4);
        System.out.println("Correct hours: " + hours);
        System.out.println("Parking fee: Rs " + fee);
        System.out.println("Narrowed value: " + narrowed);
        System.out.println("Large minutes: " + bigMinutes);
        System.out.println("Double addition: " + (0.1 + 0.2));
    }
}
