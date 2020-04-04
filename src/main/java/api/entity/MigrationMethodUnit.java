package api.entity;

import api.entity.types.MigrationUnitWithClass;
import api.entity.types.MigrationUnitWithMethod;

public interface MigrationMethodUnit extends MigrationUnitWithMethod, MigrationUnitWithClass, MigrationClassUnit {
    String getOldFullName();
    String getNewFullName();
}
