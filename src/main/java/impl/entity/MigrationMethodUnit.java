package impl.entity;

import api.entity.MigrationArgumentUnit;
import api.entity.MigrationUnitType;
import api.entity.types.MigrationUnitWithMethod;

import java.util.ArrayList;
import java.util.List;

public class MigrationMethodUnit extends MigrationUnitImpl implements MigrationUnitWithMethod {
    private List<MigrationArgumentUnit> arguments = new ArrayList<>();

    public MigrationMethodUnit(String originalName, String newName) {
        super(originalName, newName);
    }

    public MigrationMethodUnit(String originalName, String newName, List<MigrationArgumentUnit> arguments) {
        super(originalName, newName);
        this.arguments = arguments;
    }

    @Override
    public MigrationUnitType getType() {
        return MigrationUnitType.METHOD_CALL;
    }

    @Override
    public List<MigrationArgumentUnit> getArguments() {
        return arguments;
    }
}
