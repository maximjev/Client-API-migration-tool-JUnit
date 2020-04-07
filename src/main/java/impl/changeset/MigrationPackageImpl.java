package impl;

import api.entity.MigrationUnit;
import api.service.MigrationPackage;

import java.util.List;

public class MigrationPackageImpl implements MigrationPackage {

    private List<MigrationUnit> units;

    public MigrationPackageImpl(List<MigrationUnit> units) {
        this.units = units;
    }

    @Override
    public List<MigrationUnit> getUnits() {
        return units;
    }

}
