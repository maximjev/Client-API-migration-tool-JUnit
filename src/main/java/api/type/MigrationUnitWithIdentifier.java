package api.type;

import api.entity.MigrationUnit;

public interface MigrationUnitWithIdentifier extends MigrationUnit {
    String getOriginalIdentifier();

    String getNewIdentifier();
}
