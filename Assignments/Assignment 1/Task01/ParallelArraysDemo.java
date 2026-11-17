import java.util.Arrays;

/**
 * Task 1 - Alternative concept: parallel arrays of Strings versus one object per vehicle.
 * Compile: javac ParallelArraysDemo.java      Run: java ParallelArraysDemo
 */
public class ParallelArraysDemo {
    static class VehicleRecord {
        String regNo, type, entryTime, userCategory;
        VehicleRecord(String regNo, String type, String entryTime, String userCategory) {
            this.regNo = regNo; this.type = type; this.entryTime = entryTime; this.userCategory = userCategory;
        }
    }
    static VehicleRecord[] vehicles = new VehicleRecord[10];
    static int vehicleCount = 0;
    static void addA(String regNo, String type, String entryTime, String category) {
        vehicles[vehicleCount++] = new VehicleRecord(regNo, type, entryTime, category);
    }
    static VehicleRecord findA(String regNo) {
        for (int i = 0; i < vehicleCount; i++) if (vehicles[i].regNo.equals(regNo)) return vehicles[i];
        return null;
    }
    static String[] regNo = new String[10], type = new String[10], entryTime = new String[10], userCategory = new String[10];
    static int count = 0;
    static void addB(String r, String t, String e, String c) {
        regNo[count] = r; type[count] = t; entryTime[count] = e; userCategory[count] = c; count++;
    }
    static int findB(String r) {
        for (int i = 0; i < count; i++) if (regNo[i].equals(r)) return i;
        return -1;
    }
    public static void main(String[] args) {
        addA("TN01AB1111", "CAR", "09:15", "VISITOR");
        addA("TN02CD2222", "TWO_WHEELER", "09:40", "STUDENT");
        addB("TN01AB1111", "CAR", "09:15", "VISITOR");
        addB("TN02CD2222", "TWO_WHEELER", "09:40", "STUDENT");
        VehicleRecord v = findA("TN02CD2222");
        System.out.println("Design A found: " + v.regNo + " " + v.type + " " + v.entryTime + " " + v.userCategory);
        int i = findB("TN02CD2222");
        System.out.println("Design B found: " + regNo[i] + " " + type[i] + " " + entryTime[i] + " " + userCategory[i]);
        for (int k = 0; k < count - 1; k++) {
            regNo[k] = regNo[k + 1]; type[k] = type[k + 1]; entryTime[k] = entryTime[k + 1]; userCategory[k] = userCategory[k + 1];
        }
        count--;
        System.out.println("After removing the first vehicle (Design B): " + Arrays.toString(Arrays.copyOf(regNo, count)));
    }
}