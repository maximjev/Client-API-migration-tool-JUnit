package api.tool;

import api.entity.MigrationUnit;

import java.util.List;

public interface MigrationPackage {
    List<MigrationUnit> getUnits();
}
