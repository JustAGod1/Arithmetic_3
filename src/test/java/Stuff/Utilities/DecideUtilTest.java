package Stuff.Utilities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Yuri on 01.03.17.
 */
public class DecideUtilTest {
    @Test
    public void toFactors() throws Exception {
        final float[] expected = {0.1f, 2, 3};
        final float f = 0.6f;
        Assert.assertArrayEquals("toFactors(float)", expected, DecideUtil.toFactors(f), 0.01f);
    }

    @Test
    public void toFactors1() throws Exception {

    }

    @Test
    public void toFloats() throws Exception {

    }

}