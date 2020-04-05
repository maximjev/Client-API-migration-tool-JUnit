package impl.entity;

import api.entity.MigrationUnitType;

public class MigrationMethodUnit extends MigrationUnitImpl {
    public MigrationMethodUnit(String originalName, String newName) {
        super(originalName, newName);
    }

    @Override
    public MigrationUnitType getType() {
        return MigrationUnitType.METHOD_CALL;
    }
}
