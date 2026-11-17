import java.util.*;

class Student {
    private final String regNo;
    private final String name;
    Student(String regNo, String name) { this.regNo = regNo; this.name = name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return regNo.equals(((Student)o).regNo);
    }
    // TODO 1 completed: equal students have equal hash codes.
    @Override public int hashCode() { return Objects.hash(regNo); }
    @Override public String toString() { return regNo + " " + name; }
}
public class Attendance {
    static class BrokenStudent {
        String regNo; String name;
        BrokenStudent(String r, String n) { regNo=r; name=n; }
        @Override public boolean equals(Object o) {
            return o instanceof BrokenStudent && regNo.equals(((BrokenStudent)o).regNo);
        }
        // Deliberately missing hashCode for experiment.
    }
    static class MutableStudent {
        String regNo;
        MutableStudent(String r) { regNo=r; }
        @Override public boolean equals(Object o) { return o instanceof MutableStudent m && regNo.equals(m.regNo); }
        @Override public int hashCode() { return Objects.hash(regNo); }
    }
    public static void main(String[] args) {
        Set<BrokenStudent> broken = new HashSet<>();
        broken.add(new BrokenStudent("192311001","Arun"));
        broken.add(new BrokenStudent("192311001","Arun"));
        System.out.println("Broken set size (may retain duplicate): " + broken.size());
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001","Arun"));
        present.add(new Student("192311002","Divya"));
        present.add(new Student("192311001","Arun"));
        System.out.println("Unique students: " + present.size());
        Set<MutableStudent> mutable = new HashSet<>();
        MutableStudent m = new MutableStudent("A1"); mutable.add(m);
        m.regNo = "B2";
        System.out.println("Contains after changing key field: " + mutable.contains(m));
        TreeSet<Student> tree = new TreeSet<>(Comparator.comparing(s -> s.toString().split(" ")[0]));
        tree.add(new Student("192311001","Arun")); tree.add(new Student("192311001","A. Kumar"));
        System.out.println("TreeSet de-duplication size: " + tree.size());
        Map<String,Student> byId = new HashMap<>();
        byId.put("192311001", new Student("192311001","Arun"));
        System.out.println("HashMap key lookup: " + byId.get("192311001"));
    }
}