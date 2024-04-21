import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FireDrillTest{
    @Test
    void testHighestMaxProduct(){
        assertEquals(20, FireDrill.getHighest(new int[]{-1,-2,3,-4,-5}));
       assertEquals(-50, FireDrill.getHighest(new int[]{-5,10}));
       assertEquals(20, FireDrill.getHighest(new int[]{-1,-2,-3,-4,-5}));
       assertEquals(30,FireDrill.getHighest(new int[]{1,-1,-9,30}));
       assertEquals(4, FireDrill.getHighest(new int[]{1,2,2,1,-9}));

    }
}