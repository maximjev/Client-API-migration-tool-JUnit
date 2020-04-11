package org.migration.tool.snapshots;

import org.junit.Test;
import org.junit.Assert;

public class SnapshotTest {

    @Test(expected = IllegalArgumentException.class)
    void test() {
        Assert.assertEquals(1, 1);
        throw new IllegalArgumentException();
    }
}
