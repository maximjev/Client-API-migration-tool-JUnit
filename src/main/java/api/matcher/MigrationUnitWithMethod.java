package api.matcher;

import api.entity.MigrationUnit;

public interface MigrationUnitWithMethod extends MigrationUnit {
    String getOldMethod();
    String getNewMethod();
}
