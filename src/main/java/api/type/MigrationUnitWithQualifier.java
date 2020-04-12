package api.type;


import api.entity.MigrationUnit;
import api.entity.MigrationUnitName;

import java.util.Optional;

public interface MigrationUnitWithQualifier extends MigrationUnit {
    Optional<MigrationUnitName> getOriginalQualifier();

    Optional<MigrationUnitName> getNewQualifier();
}
