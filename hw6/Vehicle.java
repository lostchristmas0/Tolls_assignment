/*
 * file: Vehicle.java
 * This file represents the class containing info of every single vehicle(trip)
 * both complete and incomplete(refer to the "Toll Record" design)
 */
package hw6;

/**
 * The Vehicle class contains all parameters of a single trip
 * @author Chenghui Zhu
 * @author Dongyu Wu
 */
public class Vehicle implements Comparable<Vehicle> {
    /** The car plate of this vehicle(trip)*/
    private String plate;
    /** The arrival toll(exit number) of this vehicle(trip)*/
    private Integer arrivalToll;
    /** The departure toll(exit number) of this vehicle(trip),
     * incomplete trip is minimal integer as default*/
    private int departureToll = Integer.MIN_VALUE;
    /** The arrive time of this vehicle(trip)*/
    private Integer arrivalTime;
    /** The departure time of this vehicle(trip),
     * incomplete trip is minimal integer as default*/
    private int departureTime = Integer.MIN_VALUE;

    /**
     * The constructor of Vehicle class. Only take car plate,
     * arrive time and toll as an arrival vehicle
     * @param plate the car plate
     * @param arrivalToll the arrive toll number
     * @param arrivalTime the arrive time
     */
    public Vehicle(String plate, int arrivalToll, int arrivalTime) {
        this.plate = plate;
        this.arrivalToll = arrivalToll;
        this.arrivalTime = arrivalTime;
    }

    /**
     * Get the car plate as a string
     * @return the car plate
     */
    public String getPlate() {
        return this.plate;
    }

    /**
     * Get the arrive toll number as an integer
     * @return the arrive toll number
     */
    public int getArrivalToll() {
        return this.arrivalToll;
    }

    /**
     * Get the arrive time as an integer
     * @return the arrive time
     */
    public int getArrivalTime() {
        return this.arrivalTime;
    }

    /**
     * Get the departure toll number as an integer
     * @return the departure toll number
     */
    public int getDepartureToll() {
        return this.departureToll;
    }

    /**
     * Get the departure time as an integer
     * @return the departure time
     */
    public int getDepartureTime() {
        return this.departureTime;
    }

    /**
     * Set the departure toll number when this vehicle is leaving
     * @param departureToll the departure toll number
     */
    public void setDepartureToll(int departureToll) {
        this.departureToll = departureToll;
    }

    /**
     * Set the departure time when this vehicle is leaving
     * @param departureTime the departure time
     */
    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Calculate the fee by{@link hw6.ExitInfo#getToll(int, int)}of a vehicle
     * when it left at a toll. If this vehicle didn't left, the fee will be
     * count as 0.
     * @return the double of calculated fee
     */
    public double payAtExit() {
        if (finished()) {
            return ExitInfo.getToll(arrivalToll, departureToll);
        } else {
            return 0;
        }
    }

    /**
     * Determine if the vehicle is on road or left at any toll
     * @return true if the vehicle left; false if the vehicle is still on road
     */
    public boolean finished() {
        return departureToll != Integer.MIN_VALUE && departureTime !=
                Integer.MIN_VALUE;
    }

    /**
     * Determine if an object o is the same object of this vehicle
     * by their hashcode
     * @param o another object to be determined
     * @return true if o is this vehicle; false if not
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vehicle) {
            return this.hashCode() == ((Vehicle) o).hashCode();
        } else {
            return false;
        }
    }

    /**
     * Get the modified hashcode of this vehicle
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return plate.hashCode() + arrivalToll.hashCode() +
                arrivalTime.hashCode();
    }

    /**
     * Get all info of the vehicle
     * @return a string contains all info of the vehicle(trip)
     */
    @Override
    public String toString() {
        if (!finished()) {
            return "[" + plate + "]{(" + arrivalToll + "," + arrivalTime +")}";
        } else {
            return "[" + plate + "]{(" + arrivalToll + "," + arrivalTime + "),"
                    + "(" + departureToll + "," + departureTime + ")}";
        }
    }

    /**
     * Determine the sort order between each vehicle. First compared by the
     * car plate (alphabetical order), then compared by the arrive time. Notice
     * that this function didn't consider if the vehicle(trip) is finished.
     * @param v another vehicle that needs to be determined
     * @return 1 if this vehicle's order is behind v's; 0 if they have the same
     * order; -1 if this vehicle's order is in front of v's
     */
    @Override
    public int compareTo(Vehicle v) {
        if (this.getPlate().compareTo(v.getPlate()) != 0) {
            return this.getPlate().compareTo(v.getPlate());
        } else if (this.getArrivalTime() < v.getArrivalTime()) {
            return -1;
        } else if (this.getArrivalTime() > v.getArrivalTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * A format version of string that contains all info of the vehicle
     * for output. Format at{@link hw6.TollsRUs#INCOMPLE_TOLL_RECORD_FORMAT},
     * {@link hw6.TollsRUs#COMPLETE_TOLL_RECORD_FORMAT}
     * @return the format string
     */
    public String report() {
        StringBuilder s = new StringBuilder();
        if (!finished()) {
            s.append(String.format(TollsRUs.INCOMPLE_TOLL_RECORD_FORMAT,
                    getPlate(), getArrivalToll(), getArrivalTime()));
        } else {
            s.append(String.format(TollsRUs.COMPLETE_TOLL_RECORD_FORMAT,
                    getPlate(), getArrivalToll(), getArrivalTime(),
                    getDepartureToll(), getDepartureTime()));
        }
        return s.toString();
    }
}
