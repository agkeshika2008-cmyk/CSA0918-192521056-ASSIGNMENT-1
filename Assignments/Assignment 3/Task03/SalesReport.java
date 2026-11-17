import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SalesReport {
    static long readTotal(Path file) throws IOException {
        long total = 0;
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                try { total += Long.parseLong(line.trim()); }
                catch (NumberFormatException e) { System.err.println("Invalid record in " + file + ": " + line); }
            }
        }
        return total;
    }
    static void generate(List<Path> stores, Path output) throws IOException {
        long grandTotal = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(output)) {
            for (Path store : stores) {
                try {
                    long amount = readTotal(store);
                    writer.write(store.getFileName() + "," + amount);
                    writer.newLine();
                    grandTotal += amount;
                } catch (IOException e) {
                    System.err.println("Failed file " + store + ": " + e.getMessage());
                    writer.write(store.getFileName() + ",ERROR");
                    writer.newLine();
                }
            }
            writer.write("TOTAL," + grandTotal);
            writer.newLine();
        }
    }
    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("sales");
        Path a = dir.resolve("storeA.txt"), b = dir.resolve("storeB.txt");
        Files.write(a, Arrays.asList("100", "250"));
        Files.write(b, Arrays.asList("300", "bad", "150"));
        Path report = dir.resolve("daily-report.csv");
        generate(Arrays.asList(a, b, dir.resolve("missing.txt")), report);
        Files.lines(report).forEach(System.out::println);
    }
}