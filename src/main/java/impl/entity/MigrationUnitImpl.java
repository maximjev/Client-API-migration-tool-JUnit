package impl.entity;

import api.entity.types.MigrationUnitName;
import api.entity.types.MigrationUnitWithFullName;
import api.entity.types.MigrationUnitWithIdentifier;
import api.entity.types.MigrationUnitWithQualifier;

import java.util.Optional;

public abstract class MigrationUnitImpl implements MigrationUnitWithFullName, MigrationUnitWithIdentifier, MigrationUnitWithQualifier {
    private MigrationUnitName originalName;
    private MigrationUnitName newName;

    public MigrationUnitImpl(String originalName, String newName) {
        this.originalName = new MigrationUnitNameImpl(originalName);
        this.newName = new MigrationUnitNameImpl(newName);
    }

    @Override
    public MigrationUnitName getOriginalName() {
        return originalName;
    }

    @Override
    public MigrationUnitName getNewName() {
        return newName;
    }

    @Override
    public String getOriginalIdentifier() {
        return originalName.getIdentifier();
    }

    @Override
    public String getNewIdentifier() {
        return newName.getIdentifier();
    }

    @Override
    public Optional<MigrationUnitName> getOriginalQualifier() {
        return originalName.getQualifier();
    }

    @Override
    public Optional<MigrationUnitName> getNewQualifier() {
        return newName.getQualifier();
    }
}
