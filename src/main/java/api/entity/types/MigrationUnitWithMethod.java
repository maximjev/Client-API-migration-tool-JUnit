package api.entity.types;

import api.entity.MigrationArgumentUnit;
import api.entity.MigrationUnit;

import java.util.List;

public interface MigrationUnitWithMethod extends MigrationUnit {
    List<MigrationArgumentUnit> getArguments();
}
