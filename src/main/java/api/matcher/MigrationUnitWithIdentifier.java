package api.matcher;

import api.entity.MigrationUnit;

public interface MigrationUnitWithIdentifier extends MigrationUnit {
    String getOldIdentifier();
    String getNewIdentifier();
}
