package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FlightTest {
    @DisplayName("WHEN a Flight object is created with a specified origin, destination, departureHour, "
            + "departureMin, durationMin, and distanceinMiles, THEN its origin will match the specified"
            + "origin AND its destination will match the specified destination...(etc.)"
            + "AND departureTimeMin will be set to departureHour*60 + departureMin.")
    @Test
    void testFlight() {
        Flight flight = new Flight("Ithaca", "Newark", 5, 0, 100, 213);
        assertEquals("Ithaca", flight.origin());
        assertEquals("Newark", flight.destination());
        assertEquals(300, flight.departureTimeMin());
        assertEquals(100, flight.durationMin());
        assertEquals(213, flight.distanceInMiles());
    }

    @DisplayName("GIVEN departureHour 5 and departureMin 0, THEN it should add a leading zero"
            + "to departureMin and return 5:00 AM.")
    @Test
    void testFormatDepartureTimeLeadingZero() {
        Flight flight = new Flight("Ithaca", "Newark", 5, 0, 100, 213);
        assertEquals("5:00 AM", flight.formatDepartureTime());
    }

    @DisplayName("GIVEN departureHour 14 and departureMin 20, THEN it should convert departureHour"
            + "into 2 and return 2:20 PM.")
    @Test
    void testFormatDepartureTimePM() {
        Flight flight = new Flight("Ithaca", "Newark", 14, 20, 100, 213);
        assertEquals("2:20 PM", flight.formatDepartureTime());
    }

    @DisplayName("GIVEN two flights that satisfy the minLayover and departureTime requirements but "
            + "have a different destination/origin and can NOT be a tightConnection, THEN return false.")
    @Test
    void testTightConnectionDiffLocation() {
        Flight flight = new Flight("Ithaca", "Newark", 1, 0, 120, 200);
        Flight nextFlight = new Flight("New York", "Boston", 3, 15, 100, 300);
        assertFalse(flight.tightConnection(nextFlight, 20));
    }

    @DisplayName("GIVEN two flights that satisfy the destination/origin requirement but the connecting flight"
            + "departs BEFORE the first flight arrives, THEN return false.")
    @Test
    void testTightConnectionDepartureTimeBefore() {
        Flight flight = new Flight("Ithaca", "Newark", 1, 0, 120, 200);
        Flight nextFlight = new Flight("New York", "Boston", 3, 15, 100, 300);
        assertFalse(flight.tightConnection(nextFlight, 20));
    }

    @DisplayName("GIVEN two flights that satisfy the destination/origin and departureTime requirements,"
            + "THEN return either true or false depending on if the minLayover > 15.")
    @Test
    void testTightConnectionDepartureTimeAfter() {
        Flight flight = new Flight("Ithaca", "Newark", 1, 0, 120, 200);
        Flight nextFlight = new Flight("Newark", "Boston", 3, 15, 100, 300);
        assertTrue(flight.tightConnection(nextFlight, 20));
        assertFalse(flight.tightConnection(nextFlight, 10));
    }

    @DisplayName("GIVEN Passenger p1 is in the manifest PassengerSet, THEN "
            + "flight.containsPassenger(p1) will return true.")
    @Test
    void testContainsPassengerTrue() {
        Flight flight = new Flight("Ithaca", "Newark", 1, 0, 125, 200);
        Passenger p1 = new Passenger("Harry", "Styles");
        flight.addToManifest(p1);

        assertTrue(flight.containsPassenger(p1));
    }

    @DisplayName("GIVEN Passenger p1 is NOT in the manifest PassengerSet, THEN "
            + "flight.containsPassenger(p1) will return false.")
    @Test
    void testContainsPassengerFalse() {
        Flight flight = new Flight("Ithaca", "Newark", 1, 0, 125, 200);
        Passenger p1 = new Passenger("Harry", "Styles");

        assertFalse(flight.containsPassenger(p1));
    }

    @DisplayName("GIVEN two Passengers added as the first and second Passenger in manifest, where"
            + "p2 is already part of manifest but p1 is not, THEN calling the addToManifest() method"
            + "will result in a false return for p2 but true for p1. The flightCount for p1 should be increased to 1.")
    @Test
    void testAddToManifest() {
        Flight flight = new Flight("Ithaca", "Newark", 1, 0, 125, 200);

        Passenger p1 = new Passenger("Niall", "Horan");
        Passenger p2 = new Passenger("Harry", "Styles");
        flight.addToManifest(p2);

        assertTrue(flight.addToManifest(p1));
        assertEquals(1, p1.flightCount());
        assertFalse(flight.addToManifest(p2));
    }

    @DisplayName("GIVEN two Passengers added as the first and second Passenger in manifest, where"
            + "p2 is already part of manifest but p1 is not, THEN calling the removeFromManifest() method"
            + "will result in a false return for p1 but true for p2. The flightCount for p2 should be decreased to 0.")
    @Test
    void testRemoveFromManifest() {
        Flight flight = new Flight("Ithaca", "Newark", 1, 0, 125, 200);

        Passenger p1 = new Passenger("Niall", "Horan");
        Passenger p2 = new Passenger("Harry", "Styles");
        flight.addToManifest(p2);

        assertFalse(flight.removeFromManifest(p1));
        assertTrue(flight.removeFromManifest(p2));
        assertEquals(0, p2.flightCount());
    }

    @DisplayName("GIVEN a Flight, WHEN queried for its string representation, "
            + "THEN it should return a String that shows the origin, destination, departure "
            + "minute, duration (in minutes), and distance (in miles)")
    @Test
    void testToString() {
        Flight flight = new Flight("Ithaca", "Dallas", 3, 45, 120, 400);
        String expectedOutput = "Flight{Origin City: 'Ithaca', Destination City: 'Dallas', "
                + "Departure Minute: 225, Duration (mins): 120, Distance (miles): 400}";
        assertEquals(expectedOutput, flight.toString());
    }
}
