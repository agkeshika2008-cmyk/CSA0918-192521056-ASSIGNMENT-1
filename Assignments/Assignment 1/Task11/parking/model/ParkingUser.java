package parking.model;
public abstract class ParkingUser implements Payable {
    private final String name;
    protected ParkingUser(String n){
        if(n==null||n.trim().isEmpty()) throw new IllegalArgumentException("Name must not be blank");
        name=n.trim();
    }
    public String getName(){return name;}
    public abstract String category();
    @Override public String toString(){return category()+"["+name+"]";}
}
