import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EventRegistrationApp extends JFrame {
    private static final String URL="jdbc:mysql://localhost:3306/college", USER="root", PASSWORD="";
    private final JTextField studentId=new JTextField(10), studentName=new JTextField(12);
    private final JComboBox<String> event=new JComboBox<>(new String[]{"Cultural Fest","Tech Symposium","Sports Day"});
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Student ID","Name","Event"},0);
    private final JTable table=new JTable(model);
    public EventRegistrationApp() {
        super("Campus Event Registration");
        JPanel form=new JPanel(new FlowLayout());
        form.add(new JLabel("Student ID")); form.add(studentId); form.add(new JLabel("Name")); form.add(studentName); form.add(event);
        JButton register=new JButton("Register"), refresh=new JButton("Refresh");
        form.add(register); form.add(refresh);
        add(form,BorderLayout.NORTH); add(new JScrollPane(table),BorderLayout.CENTER);
        JMenuBar bar=new JMenuBar(); JMenu menu=new JMenu("File"); JMenuItem exit=new JMenuItem("Exit"); exit.addActionListener(e->dispose()); menu.add(exit); bar.add(menu); setJMenuBar(bar);
        register.addActionListener(e -> registerAsync());
        refresh.addActionListener(e -> loadAsync());
        setSize(700,400); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadAsync();
    }
    private Connection connect() throws SQLException { return DriverManager.getConnection(URL,USER,PASSWORD); }
    private void registerAsync() {
        String id=studentId.getText().trim(), name=studentName.getText().trim(), eventName=(String)event.getSelectedItem();
        if(id.isEmpty() || name.isEmpty()) { JOptionPane.showMessageDialog(this,"Enter all details"); return; }
        new SwingWorker<Void,Void>() {
            protected Void doInBackground() throws Exception {
                try(Connection c=connect(); PreparedStatement ps=c.prepareStatement("INSERT INTO event_registration(student_id,student_name,event_name) VALUES(?,?,?)")) {
                    ps.setString(1,id); ps.setString(2,name); ps.setString(3,eventName); ps.executeUpdate();
                } return null;
            }
            protected void done() { try { get(); JOptionPane.showMessageDialog(EventRegistrationApp.this,"Registered"); loadAsync(); } catch(Exception ex) { JOptionPane.showMessageDialog(EventRegistrationApp.this,"Registration failed or duplicate: "+ex.getMessage()); } }
        }.execute();
    }
    private void loadAsync() {
        new SwingWorker<java.util.List<String[]>,Void>() {
            protected java.util.List<String[]> doInBackground() throws Exception {
                java.util.List<String[]> rows=new java.util.ArrayList<>();
                try(Connection c=connect(); Statement s=c.createStatement(); ResultSet rs=s.executeQuery("SELECT student_id,student_name,event_name FROM event_registration ORDER BY student_id")) {
                    while(rs.next()) rows.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3)});
                } return rows;
            }
            protected void done() { try { model.setRowCount(0); for(String[] r:get()) model.addRow(r); } catch(Exception ex) { JOptionPane.showMessageDialog(EventRegistrationApp.this,ex.getMessage()); } }
        }.execute();
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new EventRegistrationApp().setVisible(true)); }
}