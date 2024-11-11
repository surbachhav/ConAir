package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PassengerSetTest {

    @DisplayName("WHEN a new PassengerSet is created, THEN its size should be zero.")
    @Test
    void testSizeAfterCreation() {
        // Invoking the constructor should yield an empty set.
        PassengerSet passengers = new PassengerSet();
        assertEquals(0, passengers.size());
    }

    @DisplayName("WHEN a new PassengerSet is created with two non-null passengers, "
            + "THEN the Passenger[] array returned should only have those two elements.")
    @Test
    void testPassengers() {
        PassengerSet actual = new PassengerSet();
        Passenger p1 = new Passenger("Louie", "Tomlinson");
        Passenger p2 = new Passenger("Harry", "Styles");
        actual.add(p1);
        actual.add(p2);

        assertEquals(p1, actual.passengers()[0]);
        assertEquals(p2, actual.passengers()[1]);
        assertEquals(2, actual.passengers().length);
    }

    @DisplayName("WHEN a new PassengerSet is created, THEN its capacity should be ten.")
    @Test
    void testCapacity() {
        PassengerSet passengers = new PassengerSet();
        assertEquals(10, passengers.capacity());
    }

    @DisplayName("WHEN one Passenger is added to an empty passengers PassengerSet, "
            + "THEN passengers.size() should be one.")
    @Test
    void testAddNoResize() {
        PassengerSet passengers = new PassengerSet();
        Passenger p1 = new Passenger("Niall", "Horan");
        passengers.add(p1);

        assertEquals(1,passengers.size());
    }

    @DisplayName("WHEN eleven Passengers are added to an empty passengers PassengerSet, "
            + "THEN its capacity should be doubled to twenty and its size should be eleven.")
    @Test
    void testAddResize() {
        PassengerSet passengers = new PassengerSet();
        Passenger p1 = new Passenger("Niall", "Horan");
        passengers.add(p1);

        String fName = "John";
        String lName = "Doe";
        for (int i = 0; i < 10; i++){
            assertTrue(passengers.add(new Passenger(fName, lName)));
            fName += "1";
            lName += "1";
        }
        assertEquals(20,passengers.capacity());
        assertEquals(11,passengers.size());
    }

    @DisplayName("WHEN p1 and p2 (two Passengers) are created, BUT only p1 is added to the passengers PassengerSet"
            + "THEN passengers.contains(p1) should be true and passengers.contains(p2) should be false.")
    @Test
    void testContainsTrue() {
        PassengerSet passengers = new PassengerSet();
        Passenger p1 = new Passenger("Liam", "Payne");
        Passenger p2 = new Passenger("Zayn", "Malik");
        passengers.add(p1);

        assertTrue(passengers.contains(p1));
        assertFalse(passengers.contains(p2));
    }

    @DisplayName("GIVEN a PassengerSet of five Passengers and p2 (a Passenger in PassengerSet) is removed,"
            + "THEN p2 should be deleted from the PassengerSet AND every Passenger thereafter should be moved up by one index"
            + "AND the PassengerSet size should decrease by one.")
    @Test
    void testRemoveValid() {
        PassengerSet passengers = new PassengerSet();
        Passenger p1 = new Passenger("Liam", "Payne");
        Passenger p2 = new Passenger("Zayn", "Malik");
        Passenger p3 = new Passenger("Harry", "Styles");
        Passenger p4 = new Passenger("Louie", "Tomlinson");
        Passenger p5 = new Passenger("Niall", "Horan");

        passengers.add(p1);
        passengers.add(p2);
        passengers.add(p3);
        passengers.add(p4);
        passengers.add(p5);

        assertTrue(passengers.remove(p2));
        assertEquals(4, passengers.size());
    }

    @DisplayName("GIVEN a PassengerSet of four Passengers and a Passenger NOT in PassengerSet is removed,"
            + "THEN the PassengerSet should remain the same AND the size should not change"
            + "AND passengers.remove() should return false.")
    @Test
    void testRemoveInvalid() {
        PassengerSet passengers = new PassengerSet();
        Passenger p1 = new Passenger("Liam", "Payne");
        Passenger p2 = new Passenger("Zayn", "Malik");
        Passenger p3 = new Passenger("Harry", "Styles");
        Passenger p4 = new Passenger("Louie", "Tomlinson");
        Passenger p5 = new Passenger("Niall", "Horan");

        passengers.add(p1);
        passengers.add(p2);
        passengers.add(p3);
        passengers.add(p4);

        assertFalse(passengers.remove(p5));
        assertEquals(4, passengers.size());
    }

    // We provide a test for toString() to help achieve full line coverage
    @DisplayName("GIVEN a PassengerSet `ps` containing Passenger `p1` and Passenger `p2`, "
            + "WHEN `ps` is queried for its String representation,"
            + "THEN it returns '{p1, p2}' or '{p2, p1}.")
    @Test
    void testToString() {
        PassengerSet passengers = new PassengerSet();
        Passenger shiny = new Passenger("Shiny", "Pteranodon");
        Passenger tiny = new Passenger("Tiny", "Pteranodon");
        passengers.add(shiny);
        passengers.add(tiny);
        assertTrue(passengers.toString().equals("{Pteranodon,Shiny; Pteranodon,Tiny}")
                || passengers.toString().equals("{Pteranodon,Tiny; Pteranodon,Shiny}"));
    }
}
