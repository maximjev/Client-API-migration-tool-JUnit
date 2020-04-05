package api.entity.types;

import java.util.Optional;

public interface MigrationUnitName {
    String getName();

    String getIdentifier();

    Optional<MigrationUnitName> getQualifier();
}
