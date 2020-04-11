package org.migration.tool.snapshots;

import org.junit.Test;

public class SnapshotTest {

    @Test(expected = IllegalArgumentException.class)
    void test() {
        throw new IllegalArgumentException();
    }
}
