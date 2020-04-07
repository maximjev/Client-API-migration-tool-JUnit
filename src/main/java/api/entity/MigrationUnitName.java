package api.entity;

import java.util.Optional;

public interface MigrationUnitName {
    String getName();

    String getIdentifier();

    Optional<MigrationUnitName> getQualifier();
}
