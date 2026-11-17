import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class CampusMigration {
    private static final String URL="jdbc:mysql://localhost:3306/college", USER="root", PASSWORD="";
    static void migrate(Path csv) throws Exception {
        List<String[]> valid=new ArrayList<>();
        List<String> errors=new ArrayList<>();
        try(BufferedReader r=Files.newBufferedReader(csv)) {
            String line; int lineNo=0;
            while((line=r.readLine())!=null) {
                lineNo++; String[] p=line.split(",",-1);
                try {
                    if(p.length!=5) throw new IllegalArgumentException("Expected 5 fields");
                    Integer.parseInt(p[3]); Double.parseDouble(p[4]);
                    valid.add(p);
                } catch(RuntimeException e) { errors.add("Line "+lineNo+": "+e.getMessage()); }
            }
        }
        try(Connection c=DriverManager.getConnection(URL,USER,PASSWORD)) {
            int loaded=PassLoader.loadAll(c,valid);
            System.out.println("Loaded: "+loaded);
            DatabaseMetaData md=c.getMetaData();
            System.out.println("Database: "+md.getDatabaseProductName());
            System.out.println("Driver: "+md.getDriverVersion());
            try(ResultSet tables=md.getTables(null,null,"%",new String[]{"TABLE"})) {
                while(tables.next()) System.out.println("Table: "+tables.getString("TABLE_NAME"));
            }
        }
        for(String error:errors) System.out.println(error);
    }
    public static void main(String[] args) throws Exception {
        if(args.length!=1) { System.out.println("Usage: CampusMigration <pass-file.csv>"); return; }
        migrate(Paths.get(args[0]));
    }
}