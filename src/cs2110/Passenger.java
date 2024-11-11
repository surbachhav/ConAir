package cs2110;

/**
 * A passenger on one of ConAir's flights.
 */
public class Passenger {

    /**
     * Flag that is set to true if Passenger has only one name.
     */
    private final boolean singleNamed;
    /**
     * First name of Passenger; may not be null and may not have leading or trailing
        whitespaces, but it should be empty if Passenger only has a single name
     */
    private String firstName;
    /**
     * Last name of Passenger; may not be empty or null and may not have leading or trailing
        whitespaces; if a Passenger has a single name, then that name should be treated as the
        Passenger's last name
     */
    private String lastName;
    /**
     * Number of flights Passenger currently has reserved (integer; may not be negative)
     */
    private int flightNum;

    /**
     * Assert that this class satisfies its class invariants.
     */
    private void assertInv() {
        assert firstName != null;
        if (singleNamed) { assert firstName.isEmpty(); }
        assert lastName != null && !lastName.isEmpty();
        assert flightNum >= 0;
    }

    /**
     * Create a new Passenger with a first name and a last name. Requires that the first name and
     * last name are not empty. Ensures that input first name and last name have no leading or
     * trailing whitespaces before using them to create a new Passenger.
     */
    public Passenger(String firstName, String lastName) {
        assert firstName != null;
        assert lastName != null && !lastName.isEmpty();

        singleNamed = false;
        this.firstName = firstName.strip();
        this.lastName = lastName.strip();
        assertInv();
    }

    /**
     * Create a new Passenger with a single name. Requires that the single name is not empty.
     * Ensures that the input single name has no leading or trailing spaces before using it to
     * create a new Passenger and that the created single-named Passenger's first name is empty.
     */
    public Passenger(String singleName) {
        assert singleName != null && !singleName.isEmpty();

        singleNamed = true;
        firstName = "";
        lastName = singleName.strip();
        assertInv();
    }

    /**
     * Return the first name of this Passenger if `singleNamed == false`. Otherwise, return an empty
     * String.
     */
    public String firstName() {
        if (!singleNamed) { return firstName; }
        return "";
    }

    /**
     * Return the last name of this Passenger or their single name if `singleNamed == true`. Will
     * not be empty.
     */
    public String lastName() {
        if (singleNamed) { return lastName; }
        return lastName;
    }

    /**
     * Return the number of flights that this customer has currently reserved.
     */
    public int flightCount() {
        return flightNum;
    }

    /**
     * Return an identifier for this Passenger, obtained from the last name and first name (if the
     * Passenger has one). If the passenger has a first name, return the last name and the first
     * name separated by a comma. Otherwise, return only the last name or single name.
     */
    public String fullID() {
        // Observe that, by invoking methods instead of referencing this fields, this method was
        // implemented without knowing how you will name your fields.
        if (singleNamed) {
            return lastName();
        } else {
            return lastName() + "," + firstName();
        }
    }

    /**
     * Increase the number of flights that this customer has reserved by `reservationIncrement`.
     * This method will be invoked, for example, if a Passenger reserves flights. Requires that
     * `reservationIncrement` is positive.
     */
    public void increaseReservations(int reservationIncrement) {
        assert reservationIncrement >= 0;
        flightNum += reservationIncrement;
        assertInv();
    }

    /**
     * Reduce the number of flights that this customer has reserved by `reservationDecrement`. This
     * method can be invoked, for example, if a Passenger completes or cancels flights. Requires
     * `reservationDecrement` to be less or equal to the number of flights that the Passenger
     * currently has reserved. Requires `reservationDecrement` to be positive.
     */
    public void reduceReservations(int reservationDecrement) {
        assert reservationDecrement >= 0 && reservationDecrement <= flightNum;
        flightNum -= reservationDecrement;
        assertInv();
    }

    /**
     * Return the full ID of this Passenger as its String representation.
     */
    @Override
    public String toString() {
        return fullID();
    }
}
