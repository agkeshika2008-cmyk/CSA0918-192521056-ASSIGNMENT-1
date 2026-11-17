import java.sql.*;

public class SecureLoginDemo {
    static boolean unsafeLogin(Connection c,String id,String password) throws SQLException {
        String sql="SELECT COUNT(*) FROM users WHERE id='"+id+"' AND pwd='"+password+"'";
        try(Statement s=c.createStatement(); ResultSet rs=s.executeQuery(sql)) { rs.next(); return rs.getInt(1)>0; }
    }
    static boolean safeLogin(Connection c,String id,String password) throws SQLException {
        String sql="SELECT COUNT(*) FROM users WHERE id=? AND pwd=?";
        try(PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,id); ps.setString(2,password);
            try(ResultSet rs=ps.executeQuery()) { rs.next(); return rs.getInt(1)>0; }
        }
    }
    public static void main(String[] args) {
        System.out.println("Use a local test database and call unsafeLogin/safeLogin with test credentials.");
    }
}