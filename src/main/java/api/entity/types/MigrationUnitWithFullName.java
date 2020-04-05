package api.entity.types;

import api.entity.MigrationUnit;

public interface MigrationUnitWithFullName extends MigrationUnit {
    MigrationUnitName getOriginalName();

    MigrationUnitName getNewName();
}
