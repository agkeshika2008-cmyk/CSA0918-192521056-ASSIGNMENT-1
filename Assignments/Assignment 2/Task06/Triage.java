import java.util.*;

class Patient {
    final String name; final int severity; final long arrival;
    Patient(String name,int severity,long arrival){
        if(severity<1||severity>5) throw new IllegalArgumentException("Severity must be 1 to 5");
        this.name=name;this.severity=severity;this.arrival=arrival;
    }
    @Override public String toString(){return name+"(S"+severity+")";}
}
public class Triage {
    private long counter=0;
    // TODO 1 completed: lowest severity number first, then earliest arrival.
    private final PriorityQueue<Patient> queue=new PriorityQueue<>(
        Comparator.comparingInt((Patient p)->p.severity).thenComparingLong(p->p.arrival));
    void admit(String name,int severity){queue.add(new Patient(name,severity,counter++));}
    Patient next(){return queue.poll();}
    List<Patient> waitingOrder(){
        PriorityQueue<Patient> copy=new PriorityQueue<>(queue);
        List<Patient> result=new ArrayList<>();
        while(!copy.isEmpty()) result.add(copy.poll());
        return result;
    }
    public static void main(String[] args){
        Triage er=new Triage();
        er.admit("Ravi",3);er.admit("Meena",1);er.admit("John",3);
        er.admit("Asha",5);er.admit("Kumar",1);er.admit("Lata",2);
        StringBuilder order=new StringBuilder();
        Patient p;
        while((p=er.next())!=null) order.append(p).append(' ');
        System.out.println(order.toString().trim());
        Triage waiting=new Triage();
        waiting.admit("PatientA",5);waiting.admit("PatientB",2);waiting.admit("PatientC",4);
        System.out.println("Waiting order: "+waiting.waitingOrder());
        System.out.println("PriorityQueue add/poll: O(log n); peek: O(1). A five-FIFO-queue design supports O(1) admission and service.");
    }
}