package org.migration.tool.snapshots;

import org.junit.Test;
import org.junit.Assert;

public class SnapshotTest {

    @Test
    public void test() {
        String message = "message";
        Long a = new Long(5);
        Long b = new Long(5);
        Assert.assertEquals(message, a, b);
    }
}
