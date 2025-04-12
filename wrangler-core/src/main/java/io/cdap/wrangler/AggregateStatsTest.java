package io.cdap.wrangler;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.executor.testing.TestingRig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AggregateStatsTest {

    @Test
    public void testAggregateStatsTotalSizeAndTime() throws Exception {
        List<Row> rows = Arrays.asList(
                new Row("data_transfer_size", "1MB").add("response_time", "100ms"),
                new Row("data_transfer_size", "2MB").add("response_time", "200ms")
        );

        String[] recipe = new String[] {
                "aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec"
        };

        List<Row> results = TestingRig.execute(recipe, rows);

        Assert.assertEquals(1, results.size());
        Row resultRow = results.get(0);

        double expectedTotalSizeMB = 3.0;
        double expectedTotalTimeSec = 0.3;

        Assert.assertEquals(expectedTotalSizeMB,
                (double) resultRow.getValue("total_size_mb"), 0.001);

        Assert.assertEquals(expectedTotalTimeSec,
                (double) resultRow.getValue("total_time_sec"), 0.001);
    }
}
