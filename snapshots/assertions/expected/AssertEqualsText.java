package org.migration.tool.snapshots;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SnapshotTest {

    @Test
    public void test() {
        String message = "message";
        Long a = new Long(5);
        Long b = new Long(5);
        Assertions.assertEquals(a, b, message);
    }
}
