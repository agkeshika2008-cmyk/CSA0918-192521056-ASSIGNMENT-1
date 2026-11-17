import java.util.concurrent.atomic.AtomicBoolean;

public class SeatBooking {
    private final AtomicBoolean[] booked;
    private int bookingsMade;
    SeatBooking(int seats) {
        booked = new AtomicBoolean[seats];
        for (int i = 0; i < seats; i++) booked[i] = new AtomicBoolean(false);
    }
    public void book(int seat) {
        if (seat < 0 || seat >= booked.length) return;
        if (booked[seat].compareAndSet(false, true)) {
            synchronized (this) { bookingsMade++; }
        }
    }
    public synchronized void bookWithMethodLock(int seat) {
        if (!booked[seat].get()) { booked[seat].set(true); bookingsMade++; }
    }
    public static void main(String[] args) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];
        long start = System.nanoTime();
        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> screen.book(seat));
            users[i].start();
        }
        for (Thread t : users) t.join();
        System.out.println("Seats sold: " + screen.bookingsMade);
        System.out.println("Elapsed ms: " + (System.nanoTime() - start) / 1_000_000.0);
    }
}