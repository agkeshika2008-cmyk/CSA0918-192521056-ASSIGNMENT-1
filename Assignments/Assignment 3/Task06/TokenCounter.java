public class TokenCounter {
    private final int[] buffer = new int[5];
    private int count = 0, in = 0, out = 0;
    public synchronized void put(int token) throws InterruptedException {
        while (count == buffer.length) wait();
        buffer[in] = token;
        in = (in + 1) % buffer.length;
        count++;
        notifyAll();
    }
    public synchronized int take() throws InterruptedException {
        while (count == 0) wait();
        int token = buffer[out];
        out = (out + 1) % buffer.length;
        count--;
        notifyAll();
        return token;
    }
    static class Wallet {
        private int balance;
        Wallet(int balance) { this.balance = balance; }
        int getBalance() { return balance; }
    }
    static void transfer(Wallet from, Wallet to, int amount) {
        Wallet first = System.identityHashCode(from) < System.identityHashCode(to) ? from : to;
        Wallet second = first == from ? to : from;
        synchronized (first) {
            synchronized (second) {
                if (from.balance >= amount) { from.balance -= amount; to.balance += amount; }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        TokenCounter counter = new TokenCounter();
        int[] sums = new int[2];
        Thread cook = new Thread(() -> {
            try { for (int t = 1; t <= 20; t++) counter.put(t); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread[] students = new Thread[2];
        for (int s = 0; s < 2; s++) {
            final int id = s;
            students[s] = new Thread(() -> {
                try { for (int k = 0; k < 10; k++) sums[id] += counter.take(); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }
        cook.start(); for (Thread t : students) t.start();
        cook.join(); for (Thread t : students) t.join();
        System.out.println("Sum of tokens collected: " + (sums[0] + sums[1]) + " (expected 210)");
    }
}