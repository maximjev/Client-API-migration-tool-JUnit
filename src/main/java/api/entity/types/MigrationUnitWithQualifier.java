package api.entity.types;


import api.entity.MigrationUnit;

import java.util.Optional;

public interface MigrationUnitWithQualifier extends MigrationUnit {
    Optional<MigrationUnitName> getOriginalQualifier();

    Optional<MigrationUnitName> getNewQualifier();
}
