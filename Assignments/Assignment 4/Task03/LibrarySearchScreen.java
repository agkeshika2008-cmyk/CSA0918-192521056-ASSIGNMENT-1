import java.awt.*;
import javax.swing.*;

public class LibrarySearchScreen extends JFrame {
    private final JTextField search = new JTextField(22);
    private final JComboBox<String> category = new JComboBox<>(new String[]{"All","Books","Journals","Theses"});
    private final DefaultListModel<String> results = new DefaultListModel<>();
    public LibrarySearchScreen() {
        super("Campus Library Search");
        setLayout(new BorderLayout(8,8));
        JLabel title = new JLabel("Library Catalogue", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(20f));
        add(title, BorderLayout.NORTH);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton find = new JButton("Search");
        searchPanel.add(search); searchPanel.add(category); searchPanel.add(find);
        add(searchPanel, BorderLayout.PAGE_START);
        JList<String> list = new JList<>(results);
        add(new JScrollPane(list), BorderLayout.CENTER);
        JPanel actions = new JPanel(new FlowLayout());
        for (String text : new String[]{"Prev","Next","Reserve"}) actions.add(new JButton(text));
        add(actions, BorderLayout.SOUTH);
        find.addActionListener(e -> {
            results.clear();
            String q = search.getText().trim();
            if (q.isEmpty()) results.addElement("Enter a search term");
            else { results.addElement("Result for: " + q); results.addElement("Category: " + category.getSelectedItem()); }
        });
        setSize(650,420); setLocationRelativeTo(null); setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new LibrarySearchScreen().setVisible(true)); }
}