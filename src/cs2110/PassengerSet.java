package cs2110;

/**
 * A mutable set of passengers on a flight.
 */
public class PassengerSet {

    /**
     * Maximum size of any PassengerSet.
     */
    public static final int MAX_CAPACITY = 40;

    /**
     * Array containing the passengers in the set.  Elements `passengers[0..size-1]` contain the
     * distinct passengers in this set, none of which is null.  All elements in `passengers[size..]`
     * are null. The length of `passengers` is the current capacity of the data structure. That
     *      * length is initially 10, can double in size to meet passenger demand, but should never exceed
     * 40. Two passengers `p1` and `p2` are distinct if `p1.equals(p2)` is false. You are not
     * required to write `equals()` methods for this assignment.
     */
    private Passenger[] passengers;

    /**
     * The number of distinct passengers in this set. Must be non-negative and no greater than
     * `passengers.length`. This size is used in other classes, e.g., to determine what airplane to
     * use for a flight.
     */
    private int size;

    /**
     * Assert that a Passenger object satisfies its class invariants.
     */
    private void assertInv() {
        assert passengers != null && passengers.length >= 10 && passengers.length <= MAX_CAPACITY;
        assert size >= 0 && size <= passengers.length;

        for (int i = 0; i < size; i += 1) {
            // Check that elements in use are non-null
            assert passengers[i] != null;

            // Check that passengers are all distinct
            for (int j = i + 1; j < size; j += 1) {
                assert !passengers[i].equals(passengers[j]);
            }
        }

        // Check that unused capacity contains only null
        for (int i = size; i < passengers.length; i += 1) {
            assert passengers[i] == null;
        }
    }

    /**
     * Create and empty set of passengers.
     */
    public PassengerSet() {
        passengers = new Passenger[10];
        size = 0;
        assertInv();
    }

    /**
     * Return the number of passengers in this set.
     */
    public int size() {
        size = 0;
        for (int i = 0; i < passengers.length; i++){
            if (passengers[i] != null) { size++; }
        }
        assertInv();
        return size;
    }

    /**
     * Return a new array containing all passengers in this set.
     */
    public Passenger[] passengers() {
        Passenger[] newPassengers = new Passenger[size];
        for (int i = 0; i < size; i++){
            if (passengers[i] != null) {
                newPassengers[i] = passengers[i];
            }
        }
        assertInv();
        return newPassengers;
    }

    /**
     * Return the capacity of the backing array for this set. This is a helper method (for use by
     * the class implementer, not by clients), but it has default/package visibility to enable
     * testing.
     */
    int capacity() {
        return passengers.length;
    }

    /**
     * Return whether a given passenger is successfully added to this set. If this set was already
     * full, simply return `false` without modifying the set. Effect: Add given passenger to the
     * set.  Requires that the given passenger is not null and is not already in this set.
     */
    public boolean add(Passenger passenger) {
        assert passenger != null;
        assert !contains(passenger);

        if (size() == capacity() && capacity() >= MAX_CAPACITY){ return false; }
        else if (size() == capacity()){
            Passenger[] newPassengers = new Passenger[capacity()*2];
            for (int i = 0; i < size(); i++){
                newPassengers[i] = passengers[i];
            }

            newPassengers[size()] = passenger;

            for (int i = size() + 1; i < newPassengers.length; i++){
                newPassengers[i] = null;
            }

            passengers = newPassengers;
            size++;
        }
        else {
            passengers[size()] = passenger;
            size++;
        }

        assertInv();
        return true;
    }

    /**
     * Return whether this PassengerSet contains a given Passenger.
     */
    public boolean contains(Passenger passenger) {
        for (int i = 0; i < size; i++){
            if (passengers[i].equals(passenger)){
                return true;
            }
        }
        assertInv();
        return false;
    }

    /**
     * Return whether a given passenger is successfully removed from this set. Effect: If the given
     * passenger is in this PassengerSet, remove the passenger from the set and return true.
     * Otherwise, return false. Requires that the given passenger is not null.
     */
    public boolean remove(Passenger passenger) {
        assert passenger != null;
        if (!contains(passenger)) { return false; }

        int index = 0;
        for (int i = 0; i < size; i++){
            if (passengers[i].equals(passenger)) {
                index = i;
                break;
            }
        }
        for (int i = index; i < capacity() - 1; i++){
            passengers[i] = passengers[i+1];
        }

        passengers[capacity()-1] = null;
        size--;
        return true;
    }

    /**
     * Return a String representation of this PassengerSet. The returned string will be enclosed in
     * curly braces, and will contain the String representation of each Passenger in the set,
     * separated by a semicolon and a space ("; "). The order of Passenger String representations in
     * the returned String is not specified.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        for (int i = 0; i < size; i += 1) {
            if (i > 0) {
                builder.append("; ");
            }
            builder.append(passengers[i]);
        }
        builder.append("}");
        return builder.toString();
    }
}
