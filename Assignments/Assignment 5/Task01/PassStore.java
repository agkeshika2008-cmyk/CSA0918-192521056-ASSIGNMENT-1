import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PassStore {
    private final Path file;
    public PassStore(Path file) { this.file=file; }
    public void add(String passId,String regNo,String name,int route,double amount) throws IOException {
        try(BufferedWriter w=Files.newBufferedWriter(file,StandardOpenOption.CREATE,StandardOpenOption.APPEND)) {
            w.write(String.join(",",passId,regNo,name.replace(",","%2C"),String.valueOf(route),String.valueOf(amount)));
            w.newLine();
        }
    }
    public String[] find(String passId) throws IOException {
        try(BufferedReader r=Files.newBufferedReader(file)) {
            String line;
            while((line=r.readLine())!=null) {
                String[] fields=line.split(",",-1);
                if(fields.length!=5) continue;
                try { Integer.parseInt(fields[3]); Double.parseDouble(fields[4]); }
                catch(NumberFormatException e) { continue; }
                if(fields[0].equals(passId)) { fields[2]=fields[2].replace("%2C",","); return fields; }
            }
        }
        return null;
    }
    public static long countLines(Path path) throws IOException {
        try(BufferedReader r=Files.newBufferedReader(path)) { long count=0; while(r.readLine()!=null) count++; return count; }
    }
    public static void main(String[] args) throws Exception {
        Path f=Files.createTempFile("passes",".csv"); PassStore store=new PassStore(f);
        store.add("P1001","192311001","Arun",12,4500);
        Files.writeString(f,"P1002,DAMAGED LINE\n",StandardOpenOption.APPEND);
        store.add("P1003","192311003","Divya",7,3800);
        System.out.println(Arrays.toString(store.find("P1003")));
        System.out.println(Arrays.toString(store.find("P1002")));
        Files.deleteIfExists(f);
    }
}