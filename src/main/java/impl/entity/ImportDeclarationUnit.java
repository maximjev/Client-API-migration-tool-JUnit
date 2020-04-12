package impl.entity;

import api.entity.MigrationUnitType;
import impl.type.MigrationUnitImpl;

public class ImportDeclarationUnit extends MigrationUnitImpl {
    public ImportDeclarationUnit(String originalName, String newName) {
        super(originalName, newName);
    }

    @Override
    public MigrationUnitType getType() {
        return MigrationUnitType.IMPORT_DECLARATION;
    }
}
