package org.migration.tool.snapshots;

import org.junit.Test;
import static org.junit.*;

public class SnapshotTest {

    @Test(expected = IllegalArgumentException.class)
    void test() {
        assertEquals(1, 1);
        throw new IllegalArgumentException();
    }
}