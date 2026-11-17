import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class AppointmentDesk extends JFrame {
    private final JTextField patientId = new JTextField(12);
    private final JComboBox<String> department = new JComboBox<>(new String[]{"Cardiology","Dental","Neurology"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> slot = new JComboBox<>(new String[]{"09:00","10:00","11:00","14:00","15:00"});
    private final Map<String,List<String>> doctors = Map.of(
        "Cardiology", List.of("Dr. Kumar","Dr. Meena"),
        "Dental", List.of("Dr. Ravi","Dr. Anu"),
        "Neurology", List.of("Dr. Shah","Dr. Iyer"));
    private boolean bookingInProgress;
    public AppointmentDesk() {
        super("Hospital Appointment Desk");
        setLayout(new GridLayout(5,2,8,8));
        add(new JLabel("Patient ID")); add(patientId);
        add(new JLabel("Department")); add(department);
        add(new JLabel("Doctor")); add(doctor);
        add(new JLabel("Time slot")); add(slot);
        JButton book = new JButton("Book"); add(book);
        JLabel status = new JLabel(" "); add(status);
        department.addActionListener(e -> refreshDoctors());
        book.addActionListener(e -> {
            bookingInProgress = true;
            try {
                if (patientId.getText().isBlank()) throw new IllegalArgumentException("Patient ID is required");
                if (doctor.getSelectedItem() == null) throw new IllegalArgumentException("Select a doctor");
                status.setText("Booked: " + patientId.getText() + " / " + doctor.getSelectedItem() + " / " + slot.getSelectedItem());
            } catch (IllegalArgumentException ex) { status.setText(ex.getMessage()); }
            finally { bookingInProgress = false; }
        });
        refreshDoctors();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (bookingInProgress && JOptionPane.showConfirmDialog(AppointmentDesk.this,"Booking in progress. Close?") != JOptionPane.YES_OPTION) return;
                dispose();
            }
        });
        setSize(420,250); setLocationRelativeTo(null);
    }
    private void refreshDoctors() {
        doctor.removeAllItems();
        for (String name : doctors.getOrDefault((String)department.getSelectedItem(), List.of())) doctor.addItem(name);
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new AppointmentDesk().setVisible(true)); }
}