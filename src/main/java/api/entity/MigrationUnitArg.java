package api.entity;

import java.util.Optional;

public interface MigrationUnitArg {

    Optional<String> getOriginalType();

    int getOriginalPosition();

    int getNewPosition();
}
