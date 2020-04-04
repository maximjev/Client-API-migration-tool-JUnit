package impl.entity;

import api.entity.MigrationMethodUnit;

public class MigrationMethodUnitImpl extends MigrationClassUnitImpl implements MigrationMethodUnit {
    private String oldMethod;
    private String newMethod;

    public MigrationMethodUnitImpl(String oldName, String newName, String oldMethod, String newMethod) {
        super(oldName, newName);
        this.oldMethod = oldMethod;
        this.newMethod = newMethod;
    }

    @Override
    public String getOldMethod() {
        return oldMethod;
    }

    @Override
    public String getNewMethod() {
        return newMethod;
    }

    @Override
    public String getOldFullName() {
        return getFullName(getOldName(), getOldMethod());
    }

    @Override
    public String getNewFullName() {
        return getFullName(getNewName(), getNewMethod());
    }

    private String getFullName(String name, String method) {
        return name + "." + method;
    }
}
