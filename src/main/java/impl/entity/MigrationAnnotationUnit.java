package impl.entity;

import api.entity.MigrationUnitType;
import impl.type.MigrationUnitImpl;

public class MigrationAnnotationUnit extends MigrationUnitImpl {
    public MigrationAnnotationUnit(String originalName, String newName) {
        super(originalName, newName);
    }

    @Override
    public MigrationUnitType getType() {
        return MigrationUnitType.MARKER_ANNOTATION;
    }

}

