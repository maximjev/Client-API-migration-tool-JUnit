package impl.entity;

import api.entity.MigrationAnnotationUnit;

public class MigrationAnnotationUnitImpl extends MigrationUnitWithClassImpl implements MigrationAnnotationUnit {
    public MigrationAnnotationUnitImpl(String oldName, String newName) {
        super(oldName, newName);
    }
}
