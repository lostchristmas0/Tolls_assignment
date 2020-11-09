package hw6.test;

import hw6.Vehicle;
import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleTest {

    @Test
    public void test1() {
        Vehicle v1 = new Vehicle("GIU920", 0, 0);
        Vehicle v2 = new Vehicle("FEA517", 32, 70);
        assertFalse(v1.finished());
        assertEquals(0, v1.payAtExit(), 0);
        assertFalse(v1.equals(v2));
        v2.setDepartureToll(39);
        v2.setDepartureTime(90);
        assertTrue(v2.finished());
        assertEquals(1.8464, v2.payAtExit(), 0.01);
        assertEquals("[GIU920]{(0,0)}", v1.toString());
        assertEquals("[FEA517]{(32,70), (39,90)}", v2.toString());
        assertEquals("[     GIU920] on # 0, time     0", v1.report());
        assertEquals("[     FEA517] on #32, time    70; off #39, time    90",
                v2.report());
        assertEquals(1, v1.compareTo(v2));
        assertEquals(-1, v2.compareTo(v1));
        v1.setDepartureToll(15);
        v1.setDepartureTime(59);
        assertEquals("[     GIU920] on # 0, time     0; off #15, time    59",
                v1.report());
    }
}
