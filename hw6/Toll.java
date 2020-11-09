/*
 * file: Toll.java
 * This file represents the class holding every single vehicle(trip)
 * and their trip info(refer to the "Toll Road Database" design)
 */
package hw6;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * The Toll class takes every toll record from input file
 * and keeps and sorts them in a TreeSet
 * @author Chenghui Zhu
 * @author Dongyu Wu
 */
public class Toll {
    /** The set keeping every single Vehicle(trip)*/
    private Set<Vehicle> recordSet;
    /** The set keeping every car plate that showed up once to help determine
     * if a car plate exist in the {@link #singleVehicle(String)}*/
    private Set<String> plateSet;

    /**
     * The constructor of Toll class, taking the toll record file
     * as the parameter and keeping each record into the recordSet,
     * each car plate into the plateSet
     * @param fileName the text file of toll record
     */
    public Toll(String fileName) {
        // use a TreeSet to sort records by the overridden compareTo() method
        recordSet = new TreeSet<>();
        plateSet = new HashSet<>();
        // The checkPlate is a HashSet used to check if a vehicle is arriving
        // or departing within that record
        Set<String> checkPlate = new HashSet<>();
        for(String s: FileHandler.open(fileName)) {
            String[] info = s.split(",");
            plateSet.add(info[1]);
            // if the next plate is not in ckeckPlate set, this record is
            // representing an arrival;
            // else this record is a departure (remove the plate from
            // checkPlate set if the trip is completed)
            if (checkPlate.add(info[1])) {
                checkPlate.add(info[1]);
                recordSet.add(new Vehicle(info[1], Integer.parseInt(info[2]),
                        Integer.parseInt(info[0])));
            } else {
                for (Vehicle v: recordSet) {
                    if (!v.finished() && v.getPlate().equals(info[1])) {
                        v.setDepartureTime(Integer.parseInt(info[0]));
                        v.setDepartureToll(Integer.parseInt(info[2]));
                        break;
                    }
                }
                checkPlate.remove(info[1]);
            }
        }
    }

    /*
    public Set<Vehicle> getRecordSet() {
        return this.recordSet;
    }

    public Set<String> getPlateSet() {
        return this.plateSet;
    }
    */

    /**
     * The method that counts the completed trips in all trips
     * @return the total completed trips
     */
    public int completedTrips() {
        int result = 0;
        for (Vehicle v: recordSet) {
            if (v.finished()) {
                result++;
            }
        }
        return result;
    }

    /**
     * Assemble all incomplete records in recordSet
     * @return the string containing all incomplete trips
     */
    public String onRoadReport() {
        StringBuilder s = new StringBuilder();
        for (Vehicle v: recordSet) {
            if (!v.finished()) {
                s.append(v.report() + "\n");
            }
        }
        return s.toString();
    }

    /**
     * Assemble all complete records in recordSet and calculate their fee
     * @return the string containing all complete trips and the total fee
     */
    public String billingReport() {
        StringBuilder s = new StringBuilder();
        double payment = 0;
        for (Vehicle v: recordSet) {
            if (v.finished()) {
                s.append(v.report() + ": " + String.format
                        (TollsRUs.DOLLAR_FORMAT, v.payAtExit()) + "\n");
                payment += v.payAtExit();
            }
        }
        s.append("Total: " + String.format(TollsRUs.DOLLAR_FORMAT, payment));
        return s.toString();
    }

    /**
     * Assemble all complete records which are higher than the speed limit via
     * {@link hw6.TollsRUs#SPEED_LIMIT}
     * @return the string containing all over-speed trips
     */
    public String speedReport() {
        StringBuilder s = new StringBuilder();
        for (Vehicle v: recordSet) {
            if (v.finished()) {
                double speed = Math.abs(ExitInfo.getMileMarker
                        (v.getDepartureToll()) - ExitInfo.getMileMarker
                        (v.getArrivalToll())) / (v.getDepartureTime() -
                        v.getArrivalTime()) * TollsRUs.MINUTES_PER_HOUR;
                if (speed > TollsRUs.SPEED_LIMIT) {
                    s.append("Vehicle " + v.getPlate() + ", starting at time "
                            + v.getArrivalTime() + "\n");
                    s.append("        from " + ExitInfo.getName
                            (v.getArrivalToll()) + "\n");
                    s.append("        to " + ExitInfo.getName
                            (v.getDepartureToll()) + "\n");
                    s.append("        " + String.format
                            (TollsRUs.SPEED_FORMAT, speed) + "\n");
                }
            }
        }
        return s.toString();
    }

    /**
     * Take a specific car plate and assemble all records with this plate
     * @param plate the given string of a car plate
     * @return the string containing all trips with the given plate
     */
    public String singleVehicle(String plate) {
        StringBuilder s = new StringBuilder();
        double payment = 0;
        if (plateSet.contains(plate)) {
            for (Vehicle v: recordSet) {
                if (v.getPlate().equals(plate) && v.finished()) {
                    s.append(v.report() + ": " + String.format
                            (TollsRUs.DOLLAR_FORMAT, v.payAtExit()) + "\n");
                    payment += v.payAtExit();
                }
            }
        }
        s.append("\nVehicle total due: " + String.format
                (TollsRUs.DOLLAR_FORMAT, payment));
        return s.toString();
    }

    /**
     * Take a specific toll number and assemble all records with this toll,
     * first the complete trips then the incomplete trips
     * @param exit the given toll number
     * @return the string containing all trips with the given toll
     */
    public String singleExit(int exit) {
        StringBuilder s = new StringBuilder();
        if (exit <= ExitInfo.LAST_EXIT) {
            for (Vehicle v: recordSet) {
                if (v.finished()) {
                    if (v.getArrivalToll() == exit ||
                            v.getDepartureToll() == exit) {
                        s.append(v.report() + "\n");
                    }
                }
            }
            for (Vehicle v: recordSet) {
                if (!v.finished()) {
                    if (v.getArrivalToll() == exit ||
                            v.getDepartureToll() == exit) {
                        s.append(v.report() + "\n");
                    }
                }
            }
        }
        return s.toString();
    }
}
