package impl.entity;

import api.entity.MigrationUnitArg;
import api.entity.MigrationUnitType;
import api.entity.types.MigrationUnitWithMethod;
import impl.type.MigrationUnitImpl;

import java.util.ArrayList;
import java.util.List;

public class MethodCallUnit extends MigrationUnitImpl implements MigrationUnitWithMethod {
    private List<MigrationUnitArg> arguments = new ArrayList<>();

    public MethodCallUnit(String originalName, String newName) {
        super(originalName, newName);
    }

    public MethodCallUnit(String originalName, String newName, List<MigrationUnitArg> arguments) {
        super(originalName, newName);
        this.arguments = arguments;
    }

    @Override
    public MigrationUnitType getType() {
        return MigrationUnitType.METHOD_CALL;
    }

    @Override
    public List<MigrationUnitArg> getArguments() {
        return arguments;
    }
}
