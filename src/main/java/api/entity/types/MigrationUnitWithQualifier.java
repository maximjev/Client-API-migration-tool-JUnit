package api.entity.types;


import api.entity.MigrationUnit;

public interface MigrationUnitWithQualifier extends MigrationUnit {
    String getOldQualifier();
    String getNewQualifier();
}
