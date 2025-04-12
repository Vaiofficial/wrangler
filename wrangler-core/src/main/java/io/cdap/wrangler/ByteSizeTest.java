package io.cdap.wrangler;

import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.Assert;
import org.junit.Test;

public class ByteSizeTest {

    @Test
    public void testParsingBytes() {
        ByteSize bs = new ByteSize("1024B");
        Assert.assertEquals(1024L, bs.getBytes());
    }

    @Test
    public void testParsingKilobytes() {
        ByteSize bs = new ByteSize("2KB");
        Assert.assertEquals(2L * 1024, bs.getBytes());
    }

    @Test
    public void testParsingMegabytes() {
        ByteSize bs = new ByteSize("1.5MB");
        Assert.assertEquals((long)(1.5 * 1024 * 1024), bs.getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFormat() {
        new ByteSize("123XY");
    }
}
