import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ResultProcessor {
    static class Result {
        final String file; final int score; final String error;
        Result(String file, int score, String error) { this.file=file; this.score=score; this.error=error; }
    }
    static Result process(Path file) {
        try {
            String value = Files.readString(file).trim();
            int score = Integer.parseInt(value);
            if (score < 0 || score > 100) throw new IllegalArgumentException("Score out of range");
            return new Result(file.toString(), score, null);
        } catch (Exception e) { return new Result(file.toString(), 0, e.getMessage()); }
    }
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("results");
        List<Path> files = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Path f = dir.resolve("score" + i + ".txt");
            if (i == 4) Files.writeString(f, "corrupt");
            else if (i == 9) f = dir.resolve("missing" + i + ".txt");
            else Files.writeString(f, String.valueOf(50 + i));
            files.add(f);
        }
        for (int threads : new int[]{1,2,4,8}) {
            ExecutorService pool = Executors.newFixedThreadPool(threads);
            try {
                List<Callable<Result>> jobs = new ArrayList<>();
                for (Path f : files) jobs.add(() -> process(f));
                List<Future<Result>> futures = pool.invokeAll(jobs);
                ConcurrentMap<String,Integer> success = new ConcurrentHashMap<>();
                ConcurrentMap<String,String> failed = new ConcurrentHashMap<>();
                for (Future<Result> future : futures) {
                    Result r = future.get();
                    if (r.error == null) success.put(r.file, r.score);
                    else failed.put(r.file, r.error);
                }
                System.out.println(threads + " threads: success=" + success.size() + ", failed=" + failed.size());
            } finally { pool.shutdown(); }
        }
    }
}