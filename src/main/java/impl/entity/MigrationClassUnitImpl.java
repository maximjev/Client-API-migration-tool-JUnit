package impl.entity;

import api.entity.MigrationClassUnit;

public class MigrationClassUnitImpl implements MigrationClassUnit {
    private String oldName;
    private String newName;

    public MigrationClassUnitImpl(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    public String getOldName() {
        return oldName;
    }

    @Override
    public String getNewName() {
        return newName;
    }

    @Override
    public String getOldIdentifier() {
        return getLastToken(oldName);
    }

    @Override
    public String getNewIdentifier() {
        return getLastToken(newName);
    }

    @Override
    public String getOldQualifier() {
        return getQualifier(oldName);
    }

    @Override
    public String getNewQualifier() {
        return getQualifier(newName);
    }

    private String getLastToken(String name) {
        String[] tokens = name.split("\\.");
        return tokens[tokens.length - 1];
    }

    private String getQualifier(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }
}
