package api.entity.types;

import api.entity.MigrationUnitArg;
import api.entity.MigrationUnit;

import java.util.List;

public interface MigrationUnitWithMethod extends MigrationUnit {
    List<MigrationUnitArg> getArguments();
}
