package cs2110;

/**
 * A flight managed by ConAir. The category of airplane should be sufficiently large to accommodate
 * all passengers on the manifest. The airplane category is determined according to this table:
 * <pre>
 * Manifest size (x)                     | Airplane category
 * ----------------------------------------------
 * x <= 10                               |     1
 * 10 < x <= 20                          |     2
 * 20 < x <= PassengerSet.MAX_CAPACITY   |     3
 * </pre>
 */
public class Flight {

    /**
     * The origin city for the flight. E.g., "SYR". Must be non-empty and have no leading or
     * trailing whitespace.
     */
    private final String origin;

    /**
     * The destination city for the flight. E.g., "PHL". Must be non-empty, not equal to `origin`,
     * and have no leading or trailing whitespace.
     */
    private final String destination;

    /**
     * The set of passengers on this flight.
     */
    private final PassengerSet manifest;

    /**
     * The category of airplane to use for this flight. Only three values are allowed: 1, 2, or 3.
     * The value used should be as specified in the table in the comment at the top of this class.
     */
    private int airplaneCategory;

    /**
     * The start time of this flight, expressed as the number of minutes after midnight.  Must be
     * between 0 and 1439, inclusive.
     */
    private final int departureTimeMin;

    /**
     * The duration of this flight, in minutes. Must be positive.
     */
    private final int durationMin;

    /**
     * The distance in miles to fly from `origin` to `destination`. Must be between 100 and 1000,
     * inclusive.
     */
    private final int distanceInMiles;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert !origin.isEmpty();
        assert !destination.isEmpty();
        assert airplaneCategory > 0 && airplaneCategory < 4;
        assert departureTimeMin >= 0 && departureTimeMin <= 1439;
        assert durationMin >= 0;
        assert distanceInMiles >= 100 && distanceInMiles <= 1000;
    }

    /**
     * Create a flight from `origin` that departs at local time `departureHour`:`departureMin` and
     * takes `durationMin` to fly `distanceInMiles` to `destination`. The flight initially starts
     * with no passengers and with the smallest `airplaneCategory` (i.e., 1). Requires the given
     * `origin` and `destination` to not be empty and to not be equal, `departureHour` to be between
     * 0 and 23, `departureMin` to be between 0 and 59, `durationMin` to be positive, and
     * `distanceInMiles` to be between 100 and 1000, inclusive. Ensures that the given `origin` and
     * `destination` have no leading or trailing whitespaces before using them to create a new
     * Flight.
     */
    public Flight(String origin, String destination, int departureHour, int departureMin,
            int durationMin, int distanceInMiles) {
        assert !origin.isEmpty();
        assert !destination.isEmpty();
        assert departureHour >= 1 && departureHour <= 23;
        assert departureMin >= 0 && departureMin <= 59;
        assert durationMin >= 0;
        assert distanceInMiles >= 100 && distanceInMiles <= 1000;

        airplaneCategory = 1;
        this.origin = origin.trim();
        this.destination = destination.trim();
        this.durationMin = durationMin;
        this.distanceInMiles = distanceInMiles;
        departureTimeMin = departureHour*60 + departureMin;
        manifest = new PassengerSet();

        assertInv();
    }

    /**
     * Return the origin of this flight.
     */
    public String origin() {
        return origin;
    }

    /**
     * Return the destination of this flight.
     */
    public String destination() {
        return destination;
    }

    /**
     * Return the number of minutes since midnight when this flight departs.
     */
    public int departureTimeMin() {
        return departureTimeMin;
    }

    /**
     * Return the time of day when this flight departs.
     */
    public String departureTime() {
        return formatDepartureTime();
    }

    /**
     * Return the duration, in minutes, for this flight.
     */
    public int durationMin() {
        return durationMin;
    }

    /**
     * Return the flight distance in miles from the flight's origin to its destination.
     */
    public int distanceInMiles() {
        return distanceInMiles;
    }

    /**
     * Return the set of passengers on this flight.
     */
    public PassengerSet manifest() {
        PassengerSet passengerSet = new PassengerSet();
        for (int i = 0; i < manifest.size(); i++) {
            passengerSet.add(manifest.passengers()[i]);
        }
        return passengerSet;
    }

    /**
     * Return the departure time for this flight in the `hour:min AM/PM` format, using 12-hour time.
     * For example, if `departureTimeMin` is 675, then this method returns "11:15 AM" and if
     * `departureTimeMin is 815, then this method returns "1:35 PM". Add leading zero(s) to the
     * minutes portion if the number of minutes past the hour is less than 10.
     */
    public String formatDepartureTime() {
        String amOrPm = "AM";
        String zero = "";
        int hour = departureTimeMin/60;
        if (hour > 12){
            hour -= 12;
            amOrPm = "PM";
        }
        int min = departureTimeMin % 60;
        if (min < 10){
            zero = "0";
        }
        assertInv();
        return hour + ":" + zero + min + " " + amOrPm;
    }

    /**
     * Return whether `nextFlight` departs from this flight's destination within `minLayover`
     * minutes (exclusive) after our arrival time.  For example, if this flight lands at ITH at 9:00
     * and `nextFlight` departs from ITH at 9:15, then this would qualify as a "short layover" for
     * any value of `minLayover > 15`.  Returns false if `nextFlight` does not depart from our
     * destination, or if it departs before we arrive (such flights don't count as "connections").
     * (Also returns false if the layover time is at least `minLayover`).  Requires `minLayover` is
     * non-negative.
     */
    public boolean tightConnection(Flight nextFlight, int minLayover) {
        assert minLayover >= 0;
        if (!nextFlight.origin().equals(destination()) || nextFlight.departureTimeMin() < (departureTimeMin() + durationMin())){
            return false;
        }
        else {
            return nextFlight.departureTimeMin() - (departureTimeMin() + durationMin())
                    < minLayover;
        }
    }

    /**
     * Returns whether a given passenger is on this flight.
     */
    public boolean containsPassenger(Passenger passenger) {
        return manifest.contains(passenger);
    }

    /**
     * Match the airplane category to the number of passengers, based on the number of passengers on
     * the manifest as specified in the table in the comments at the top of this class. Recall that
     * the PassengerSet _type_ is constrained to have no more than PassengerSet.MAX_CAPACITY
     * Passengers.
     */
    private void updatePlaneCategory() {
        if (manifest.size() <= 10){
            airplaneCategory = 1;
        }
        else if (manifest.size() > 10 && manifest.size() <= 20){
            airplaneCategory = 2;
        }
        else {
            airplaneCategory = 3;
        }
        assertInv();
    }

    /**
     * Return `true` and increment by 1 the number of flights that a given passenger is on if that
     * passenger is not already on this flight and is successfully added to the manifest for this
     * flight. Otherwise, return `false`. If necessary, the airplane category should be updated to
     * ensure sufficient capacity for the manifest, as specified in the table in the comments at the
     * top of this class.
     */
    public boolean addToManifest(Passenger passenger) {
        if (manifest.contains(passenger)){
            return false;
        }
        passenger.increaseReservations(1);
        manifest.add(passenger);
        updatePlaneCategory();
        assertInv();
        return true;
    }

    /**
     * Return `true` and decrement by 1 the number of flights that a given passenger is on, if that
     * passenger is already on this flight. Otherwise, return `false`.  If necessary, the airplane
     * category should be updated to ensure sufficient capacity for the manifest, as specified in
     * table in the comments at the top of this class.
     */
    public boolean removeFromManifest(Passenger passenger) {
        if (!manifest.contains((passenger))){
            return false;
        }
        passenger.reduceReservations(1);
        manifest.remove(passenger);
        updatePlaneCategory();
        assertInv();
        return true;
    }

    /**
     * Return a String representation of this Flight.
     */
    @Override
    public String toString() {
        return "Flight{" +
                "Origin City: '" + origin() + '\'' +
                ", Destination City: '" + destination() + '\'' +
                ", Departure Minute: " + departureTimeMin() +
                ", Duration (mins): " + durationMin() +
                ", Distance (miles): " + distanceInMiles() +
                '}';
    }
}
