package impl.entity;

import api.entity.MigrationMethodUnit;

public class MigrationMethodUnitImpl extends MigrationClassUnitImpl implements MigrationMethodUnit {
    private String originalMethod;
    private String newMethod;

    public MigrationMethodUnitImpl(String originalName, String newName, String originalMethod, String newMethod) {
        super(originalName, newName);
        this.originalMethod = originalMethod;
        this.newMethod = newMethod;
    }

    @Override
    public String getOriginalMethod() {
        return originalMethod;
    }

    @Override
    public String getNewMethod() {
        return newMethod;
    }

    @Override
    public String getOriginalFullName() {
        return getFullName(getOriginalName(), getOriginalMethod());
    }

    @Override
    public String getNewFullName() {
        return getFullName(getNewName(), getNewMethod());
    }

    private String getFullName(String name, String method) {
        return name + "." + method;
    }
}
