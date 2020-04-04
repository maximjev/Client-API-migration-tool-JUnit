package api.entity.types;

import api.entity.MigrationUnit;

public interface MigrationUnitWithMethod extends MigrationUnit {
    String getOriginalMethod();
    String getNewMethod();
}
