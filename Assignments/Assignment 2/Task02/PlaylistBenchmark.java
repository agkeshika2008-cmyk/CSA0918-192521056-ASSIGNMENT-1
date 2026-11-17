import java.util.*;

public class PlaylistBenchmark {
    static volatile int sink;
    static long time(Runnable action) {
        long start = System.nanoTime(); action.run(); return System.nanoTime() - start;
    }
    static void test(int n) {
        System.out.println("\nPlaylist size: " + n);
        ArrayList<Integer> a = new ArrayList<>();
        LinkedList<Integer> l = new LinkedList<>();
        for (int i = 0; i < n; i++) { a.add(i); l.add(i); }
        int reps = Math.max(100, 1_000_000 / n), middle = n / 2;
        System.out.println("ArrayList add end ns/op: " + time(() -> { for (int i=0;i<reps;i++) { a.add(i); a.remove(a.size()-1); } }) / (double)reps);
        System.out.println("LinkedList add end ns/op: " + time(() -> { for (int i=0;i<reps;i++) { l.add(i); l.remove(l.size()-1); } }) / (double)reps);
        System.out.println("ArrayList front insert/remove ns/op: " + time(() -> { for(int i=0;i<reps;i++){a.add(0,-1);a.remove(0);} }) / (double)reps);
        System.out.println("LinkedList front insert/remove ns/op: " + time(() -> { for(int i=0;i<reps;i++){l.addFirst(-1);l.removeFirst();} }) / (double)reps);
        System.out.println("ArrayList middle insert/remove ns/op: " + time(() -> { for(int i=0;i<reps;i++){a.add(middle,-1);a.remove(middle);} }) / (double)reps);
        System.out.println("LinkedList middle insert/remove ns/op: " + time(() -> { for(int i=0;i<reps;i++){l.add(middle,-1);l.remove(middle);} }) / (double)reps);
        System.out.println("ArrayList get k ns/op: " + time(() -> { for(int i=0;i<reps;i++) sink += a.get(middle); }) / (double)reps);
        System.out.println("LinkedList get k ns/op: " + time(() -> { for(int i=0;i<reps;i++) sink += l.get(middle); }) / (double)reps);
        System.out.println("ArrayList iteration ns: " + time(() -> { for(int x:a) sink += x; }));
        System.out.println("LinkedList iteration ns: " + time(() -> { for(int x:l) sink += x; }));
        ArrayDeque<Integer> q = new ArrayDeque<>();
        System.out.println("ArrayDeque addFirst/addLast/removeFirst ns/op: " + time(() -> { for(int i=0;i<reps;i++){q.addFirst(i);q.addLast(i);q.removeFirst();q.removeLast();} }) / (double)(reps*4));
    }
    public static void main(String[] args) { test(1_000); test(10_000); test(50_000); }
}