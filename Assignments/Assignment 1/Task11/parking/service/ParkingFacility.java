package parking.service;
public class ParkingFacility {
    private final String[][] slots;
    public ParkingFacility(){this(new int[]{20});}
    public ParkingFacility(int[] sizes){
        if(sizes==null||sizes.length==0)throw new IllegalArgumentException("At least one floor is required");
        slots=new String[sizes.length][];
        for(int f=0;f<sizes.length;f++){
            if(sizes[f]<=0)throw new IllegalArgumentException("Every floor needs at least one slot");
            slots[f]=new String[sizes[f]];
        }
    }
    public int[] allocate(String vehicleNo){
        for(int f=0;f<slots.length;f++)for(int s=0;s<slots[f].length;s++)
            if(slots[f][s]==null){slots[f][s]=vehicleNo;return new int[]{f,s};}
        return null;
    }
    public boolean release(int f,int s){
        if(f<0||f>=slots.length||s<0||s>=slots[f].length||slots[f][s]==null)return false;
        slots[f][s]=null;return true;
    }
    public int getFloorCount(){return slots.length;}
    public int capacity(){int n=0;for(String[] floor:slots)n+=floor.length;return n;}
}
