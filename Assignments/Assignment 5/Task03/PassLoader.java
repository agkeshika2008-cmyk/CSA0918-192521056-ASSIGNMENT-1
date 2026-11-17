import java.sql.*;
import java.util.*;

public class PassLoader {
    public static int loadAll(Connection con,List<String[]> passes) throws SQLException {
        String sql="INSERT INTO bus_pass(pass_id,reg_no,name,route_no,amount) VALUES(?,?,?,?,?)";
        boolean oldAutoCommit=con.getAutoCommit();
        con.setAutoCommit(false);
        try(PreparedStatement ps=con.prepareStatement(sql)) {
            int n=0, batch=0;
            for(String[] p:passes) {
                ps.setString(1,p[0]); ps.setString(2,p[1]); ps.setString(3,p[2]);
                ps.setInt(4,Integer.parseInt(p[3])); ps.setDouble(5,Double.parseDouble(p[4]));
                ps.addBatch(); n++; batch++;
                if(batch==1000) { ps.executeBatch(); batch=0; }
            }
            if(batch>0) ps.executeBatch();
            con.commit();
            return n;
        } catch(SQLException | RuntimeException e) {
            con.rollback();
            throw e;
        } finally { con.setAutoCommit(oldAutoCommit); }
    }
    public static void main(String[] args) {
        System.out.println("PassLoader is ready for a JDBC Connection and generated pass records.");
    }
}