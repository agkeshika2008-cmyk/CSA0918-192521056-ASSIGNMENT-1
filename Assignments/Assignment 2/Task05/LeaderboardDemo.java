import java.util.*;

class Team {
    final String name; final int points; final int goalDiff;
    Team(String name,int points,int goalDiff){this.name=name;this.points=points;this.goalDiff=goalDiff;}
    @Override public String toString(){return name+"("+points+","+goalDiff+")";}
}
class Leaderboard<T> {
    private final List<T> items=new ArrayList<>();
    private final Comparator<? super T> order;
    Leaderboard(Comparator<? super T> order){this.order=order;}
    void add(T item){items.add(Objects.requireNonNull(item));}
    List<T> ranking(){
        List<T> copy=new ArrayList<>(items);
        copy.sort(order);
        return copy;
    }
    void addAll(List<? extends T> values){items.addAll(values);}
}
public class LeaderboardDemo {
    public static void main(String[] args){
        Comparator<Team> byRank=Comparator.comparingInt((Team t)->t.points).reversed()
            .thenComparing(Comparator.comparingInt((Team t)->t.goalDiff).reversed())
            .thenComparing(t->t.name);
        Leaderboard<Team> board=new Leaderboard<>(byRank);
        board.add(new Team("Panthers",9,4));
        board.add(new Team("Eagles",9,6));
        board.add(new Team("Cobras",6,1));
        board.add(new Team("Bisons",9,4));
        System.out.println(board.ranking());
        List<Team> faultyData=Arrays.asList(new Team("Huge",Integer.MAX_VALUE,0),
            new Team("Small",Integer.MIN_VALUE,0),new Team("Middle",0,0));
        Comparator<Team> faulty=(a,b)->b.points-a.points;
        List<Team> faultySorted=new ArrayList<>(faultyData); faultySorted.sort(faulty);
        System.out.println("Faulty comparator test: "+faultySorted);
        List<Team> safeSorted=new ArrayList<>(faultyData); safeSorted.sort(byRank);
        System.out.println("Safe comparator test: "+safeSorted);
        // Expected: [Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]
    }
}