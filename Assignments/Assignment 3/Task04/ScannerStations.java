import java.util.concurrent.*;

public class ScannerStations {
    static class Station extends Thread {
        private final int scripts;
        Station(String name, int scripts) { super(name); this.scripts = scripts; }
        public void run() {
            try { Thread.sleep(100L * scripts); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println(getName() + " scanned " + scripts);
        }
    }
    static class RunnableStation implements Runnable {
        private final String name; private final int scripts;
        RunnableStation(String name, int scripts) { this.name = name; this.scripts = scripts; }
        public void run() {
            try { Thread.sleep(100L * scripts); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println(name + " scanned " + scripts);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread[] stations = {
            new Station("Station-1", 4),
            new Thread(new RunnableStation("Station-2", 3)),
            new Thread(new RunnableStation("Station-3", 2)),
            new Thread(new RunnableStation("Station-4", 1))
        };
        int total = 0;
        for (Thread station : stations) station.start();
        for (Thread station : stations) { station.join(); total += 1; }
        System.out.println("Stations completed: " + total);
        ExecutorService pool = Executors.newFixedThreadPool(4);
        try {
            Future<Integer>[] results = new Future[4];
            for (int i = 0; i < 4; i++) {
                final int count = i + 1;
                results[i] = pool.submit(() -> { Thread.sleep(100L * count); return count; });
            }
            int scripts = 0;
            for (Future<Integer> result : results) scripts += result.get();
            System.out.println("Executor total scripts: " + scripts);
        } catch (ExecutionException e) { throw new RuntimeException(e.getCause()); }
        finally { pool.shutdown(); }
    }
}