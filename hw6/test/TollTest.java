package hw6.test;

import hw6.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TollTest {

    @Test
    public void test1() {
        Toll test = new Toll("TollTest.txt");
        assertEquals(5, test.completedTrips());
        assertEquals("[     FEA517] on #32, time    12\n" +
                "[        RNA] on #27, time   103\n", test.onRoadReport());
        assertEquals("[        DNA] on #25, time    92; off #19, time   110: "+
                "$ 2.50\n" +
                "[     GHQ124] on #61, time    10; off #55, time    30: " +
                "$ 2.62\n" +
                "[     GIU920] on # 0, time     0; off #12, time    48: " +
                "$ 0.75\n" +
                "[     GIU920] on #12, time    64; off # 7, time    90: " +
                "$ 0.45\n" +
                "[        RNA] on #17, time    28; off #23, time    62: " +
                "$ 3.27\n" +
                "Total: $ 9.59", test.billingReport());
        assertEquals("Vehicle DNA, starting at time 92\n" +
                "        from Schenectady - I-890 - NY Routes 7 & 146\n" +
                "        to Kingston - NY Route 28 - " +
                "Kingston-Rhinecliff Bridge\n" +
                "        208.2 MpH\n" +
                "Vehicle GHQ124, starting at time 10\n" +
                "        from Ripley - Shortman Road\n" +
                "        to Springville - Orchard Park - Lackawanna - " +
                "West Seneca - US Route 219 - Ridge Road\n" +
                "        196.3 MpH\n" +
                "Vehicle RNA, starting at time 28\n" +
                "        from Newburgh - Scranton - I-84 - " +
                "NY Routes 17K & 300\n" +
                "        to Albany (Downtown) - Troy - Rensselaer - I-787 - " +
                "US Route 9W\n" +
                "        144.4 MpH\n", test.speedReport());
        assertEquals("[     GIU920] on # 0, time     0; off #12, time    48: "+
                "$ 0.75\n" +
                "[     GIU920] on #12, time    64; off # 7, time    90: " +
                "$ 0.45\n" +
                "\nVehicle total due: $ 1.20", test.singleVehicle("GIU920"));
        assertEquals("[        RNA] on #17, time    28; off #23, time    62: "+
                "$ 3.27\n" +
                "\nVehicle total due: $ 3.27", test.singleVehicle("RNA"));
        assertEquals("[     FEA517] on #32, time    12\n",test.singleExit(32));
        assertEquals("[     GIU920] on # 0, time     0; off #12, time    48\n"+
                "[     GIU920] on #12, time    64; off # 7, time    90\n",
                test.singleExit(12));
    }
}
