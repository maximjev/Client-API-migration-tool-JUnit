package org.migration.tool.snapshots;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SnapshotTest {

    @Test
    void test() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException();
        });
    }
}
