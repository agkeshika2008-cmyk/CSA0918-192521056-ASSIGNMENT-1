import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class FeeService {
    private static final String URL = "jdbc:mysql://localhost:3306/college";
    private static final String USER = "root", PASSWORD = "";
    String pay(String regNo, double amount) throws SQLException {
        if (regNo == null || regNo.isBlank() || amount <= 0) throw new IllegalArgumentException("Enter valid details");
        String sql = "INSERT INTO fees(reg_no, amount) VALUES (?, ?)";
        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, regNo.trim()); ps.setDouble(2, amount);
            ps.executeUpdate();
        }
        return "Payment recorded successfully";
    }
}
public class FeeWindow extends JFrame {
    private final JTextField regNo = new JTextField(12), amount = new JTextField(10);
    private final JLabel status = new JLabel(" ");
    private final FeeService service = new FeeService();
    public FeeWindow() {
        super("Student Fee Payment");
        setLayout(new GridLayout(4, 2, 8, 8));
        add(new JLabel("Register number")); add(regNo);
        add(new JLabel("Amount")); add(amount);
        JButton pay = new JButton("Pay"), clear = new JButton("Clear"), receipt = new JButton("Receipt");
        add(pay); add(clear); add(receipt); add(status);
        pay.addActionListener(e -> {
            try { status.setText(service.pay(regNo.getText(), Double.parseDouble(amount.getText()))); }
            catch (Exception ex) { status.setText("Payment failed: " + ex.getMessage()); }
        });
        clear.addActionListener(e -> { regNo.setText(""); amount.setText(""); status.setText("Cleared"); });
        receipt.addActionListener(e -> JOptionPane.showMessageDialog(this, status.getText(), "Receipt", JOptionPane.INFORMATION_MESSAGE));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(FeeWindow.this, "Close window?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) dispose();
            }
        });
        pack(); setLocationRelativeTo(null);
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new FeeWindow().setVisible(true)); }
}