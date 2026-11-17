import java.util.*;

public class PharmacyInventory {
    static volatile int sink;
    static long lookupTime(Map<String,Integer> map, List<String> keys, int repeats) {
        long start = System.nanoTime();
        for (int i=0;i<repeats;i++) sink += map.get(keys.get(i % keys.size()));
        return System.nanoTime()-start;
    }
    static void run(int n) {
        Map<String,Integer> hash = new HashMap<>();
        Map<String,Integer> tree = new TreeMap<>();
        Map<String,Integer> linked = new LinkedHashMap<>();
        List<String> keys = new ArrayList<>();
        for(int i=0;i<n;i++) {
            String code=String.format("MED%07d",i);
            keys.add(code); hash.put(code,i); tree.put(code,i); linked.put(code,i);
        }
        int repeats=100_000;
        System.out.println("\nMedicines: "+n);
        System.out.println("HashMap 100000 lookups ns: "+lookupTime(hash,keys,repeats));
        System.out.println("TreeMap 100000 lookups ns: "+lookupTime(tree,keys,repeats));
        System.out.println("LinkedHashMap 100000 lookups ns: "+lookupTime(linked,keys,repeats));
        long t=System.nanoTime(); new TreeMap<>(hash).forEach((k,v)->sink+=v);
        System.out.println("HashMap sorted report (copy+sort) ns: "+(System.nanoTime()-t));
        t=System.nanoTime(); tree.forEach((k,v)->sink+=v);
        System.out.println("TreeMap code-order report ns: "+(System.nanoTime()-t));
        t=System.nanoTime(); linked.forEach((k,v)->sink+=v);
        System.out.println("LinkedHashMap insertion-order report ns: "+(System.nanoTime()-t));
        System.out.println("Order check: TreeMap first="+tree.keySet().iterator().next()
                +", LinkedHashMap first="+linked.keySet().iterator().next());
    }
    public static void main(String[] args) {
        run(100); run(10_000); run(100_000);
        System.out.println("\nHashtable is legacy synchronized API; prefer HashMap or explicit concurrent collections when needed.");
    }
}