package io.cdap.wrangler;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.junit.Test;

public class TimeDurationTest {

    @Test
    public void testMilliseconds() {
        TimeDuration td = new TimeDuration("500ms");
        Assert.assertEquals(500L, td.getMilliseconds());
    }

    @Test
    public void testSeconds() {
        TimeDuration td = new TimeDuration("2.5s");
        Assert.assertEquals((long)(2.5 * 1000), td.getMilliseconds());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormat() {
        new TimeDuration("abcms");
    }
}
