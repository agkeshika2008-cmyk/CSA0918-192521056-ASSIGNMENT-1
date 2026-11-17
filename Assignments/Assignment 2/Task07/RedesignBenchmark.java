import java.util.*;

public class RedesignBenchmark {
    static volatile int sink;
    static class Song {
        final String id; Song(String id){this.id=id;}
    }
    static long linearLookup(List<Song> songs,String id,int reps){
        long start=System.nanoTime();
        for(int r=0;r<reps;r++) for(Song s:songs) if(s.id.equals(id)){sink++;break;}
        return System.nanoTime()-start;
    }
    static long indexedLookup(Map<String,Song> index,String id,int reps){
        long start=System.nanoTime();
        for(int r=0;r<reps;r++) if(index.containsKey(id)) sink++;
        return System.nanoTime()-start;
    }
    static void run(int n){
        List<Song> list=new ArrayList<>();
        Map<String,Song> index=new HashMap<>();
        for(int i=0;i<n;i++){Song s=new Song("SONG"+i);list.add(s);index.put(s.id,s);}
        String target="SONG"+(n-1);
        int reps=Math.max(100,1_000_000/n);
        for(int i=0;i<2;i++){linearLookup(list,target,100);indexedLookup(index,target,100);}
        long linear=linearLookup(list,target,reps);
        long hashed=indexedLookup(index,target,reps);
        System.out.println("n="+n+" linear ns/op="+linear/(double)reps
            +" indexed ns/op="+hashed/(double)reps);
    }
    public static void main(String[] args){
        System.out.println("Original: ArrayList linear scan O(n) per lookup.");
        System.out.println("Redesign: HashMap index by immutable song ID, average O(1) lookup; extra O(n) index space.");
        run(1_000);run(10_000);run(100_000);
        System.out.println("Trade-off: index must be updated on every insert/delete; ArrayList remains useful for ordered display and sequential playback.");
    }
}