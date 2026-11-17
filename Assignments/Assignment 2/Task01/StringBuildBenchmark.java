import java.util.*;

public class StringBuildBenchmark {
    static volatile int sink;
    interface Builder { String build(String[] fields); }
    static String plus(String[] fields) {
        String result = "";
        for (String field : fields) result = result + field;
        return result;
    }
    static StringBuilder builder(String[] fields) {
        StringBuilder result = new StringBuilder();
        for (String field : fields) result.append(field);
        return result;
    }
    static StringBuffer buffer(String[] fields) {
        StringBuffer result = new StringBuffer();
        for (String field : fields) result.append(field);
        return result;
    }
    static long measure(Builder b, String[] fields, int repeats) {
        for (int i = 0; i < 3; i++) sink += b.build(fields).length();
        long start = System.nanoTime();
        for (int i = 0; i < repeats; i++) sink += b.build(fields).length();
        return (System.nanoTime() - start) / repeats;
    }
    static String[] fields(int count) {
        String[] a = new String[count];
        for (int i = 0; i < count; i++) a[i] = "FIELD" + i + "|";
        return a;
    }
    static void run(String label, int fieldCount, int tickets, int repeats) {
        String[] f = fields(fieldCount);
        System.out.println("\n" + label + " (" + tickets + " tickets, " + fieldCount + " fields)");
        System.out.println("String + avg ns/ticket: " + measure(StringBuildBenchmark::plus, f, repeats));
        System.out.println("StringBuilder avg ns/ticket: " + measure(x -> builder(x).toString(), f, repeats));
        System.out.println("StringBuffer avg ns/ticket: " + measure(x -> buffer(x).toString(), f, repeats));
        System.out.println("Approx intermediate concatenation strings per ticket: " + Math.max(0, fieldCount - 1));
        System.out.println("Final ticket characters: " + builder(f).length());
    }
    static long usedMemory() {
        Runtime r = Runtime.getRuntime(); return r.totalMemory() - r.freeMemory();
    }
    public static void main(String[] args) {
        System.out.println("Used memory before: " + usedMemory());
        run("Short ticket", 6, 1, 100_000);
        run("Many tickets", 50, 10_000, 2_000);
        run("Large ticket", 500, 100, 1_000);
        System.gc();
        System.out.println("Used memory after GC: " + usedMemory());
        System.out.println("Shared multithreaded log: synchronize access to one StringBuffer or use a lock; use StringBuilder for each thread-confined ticket.");
    }
}