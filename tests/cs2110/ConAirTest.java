package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConAirTest {
    @DisplayName("GIVEN Flights f1-f7 where Passenger p1 is a passenger of 6 flights but no other Passengers,"
            + "THEN return 1 for frequentFliers(). IF p1 is removed off 2 of these flights,"
            + "THEN return 0 for frequentFliers().")
    @Test
    void testFrequentFliers() {
        ConAir cA = new ConAir();

        Flight f1 = new Flight("Ithaca", "Newark", 1, 0, 200, 200);
        Flight f2 = new Flight("New York", "Chicago", 2, 15, 300, 250);
        Flight f3 = new Flight("Boston", "Los Angeles", 12, 5, 400, 800);
        Flight f4 = new Flight("Trenton", "Phoenix", 18, 30, 250, 600);
        Flight f5 = new Flight("San Diego", "Philadelphia", 20, 50, 450, 900);
        Flight f6 = new Flight("Chicago", "Ithaca", 9, 45, 120, 500);
        Flight f7 = new Flight("Washington", "Edison", 13, 15, 400, 800);

        cA.addFlight(f1);
        cA.addFlight(f2);
        cA.addFlight(f3);
        cA.addFlight(f4);
        cA.addFlight(f5);
        cA.addFlight(f6);
        cA.addFlight(f7);

        Passenger p1 = new Passenger("Liam", "Payne");
        Passenger p2 = new Passenger("Zayn", "Malik");
        Passenger p3 = new Passenger("Harry", "Styles");
        Passenger p4 = new Passenger("Louie", "Tomlinson");
        Passenger p5 = new Passenger("Niall", "Horan");

        f1.addToManifest(p1);
        f2.addToManifest(p1);
        f3.addToManifest(p1);
        f4.addToManifest(p1);
        f5.addToManifest(p1);
        f6.addToManifest(p1);

        f1.addToManifest(p2);
        f6.addToManifest(p2);

        f1.addToManifest(p3);
        f2.addToManifest(p3);
        f4.addToManifest(p3);
        f5.addToManifest(p3);
        f7.addToManifest(p3);

        f2.addToManifest(p4);
        f3.addToManifest(p4);
        f6.addToManifest(p4);

        f7.addToManifest(p5);
        assertEquals(0, cA.frequentFliers());

        f5.removeFromManifest(p1);
        f6.removeFromManifest(p1);
        assertEquals(0, cA.frequentFliers());
    }

    @DisplayName("GIVEN a Passenger p1 with connecting flights f1 --> f2 and f3 --> f4 that do not"
            + "have a gap larger than minLayover, THEN hasBadLayover() will return true. GIVEN a"
            + "Passenger p2 with connecting flights f1 --> f2 and f3 --> f5 that do have a gap larger"
            + "than minLayover, THEN hasBadLayover() will return false.")
    @Test
    void testHasBadLayover() {
        ConAir cA = new ConAir();
        Flight f1 = new Flight("Ithaca", "Newark", 1, 0, 120, 200);
        Flight f2 = new Flight("Newark", "Chicago", 3, 15, 300, 250);
        Flight f3 = new Flight("Boston", "Los Angeles", 12, 0, 120, 800);
        Flight f4 = new Flight("Los Angeles", "Phoenix", 2, 0, 250, 600);
        Flight f5 = new Flight("Los Angeles", "Phoenix", 2, 20, 250, 600);

        cA.addFlight(f1);
        cA.addFlight(f2);
        cA.addFlight(f3);
        cA.addFlight(f4);
        cA.addFlight(f5);

        Passenger p1 = new Passenger("Liam", "Payne");
        Passenger p2 = new Passenger("Harry", "Styles");

        f1.addToManifest(p1);
        f2.addToManifest(p1);
        f3.addToManifest(p1);
        f4.addToManifest(p1);

        f1.addToManifest(p1);
        f2.addToManifest(p1);
        f3.addToManifest(p1);
        f5.addToManifest(p1);

        assertTrue(cA.hasBadLayover(p1,20));
        assertFalse(cA.hasBadLayover(p2,10));
    }
}
