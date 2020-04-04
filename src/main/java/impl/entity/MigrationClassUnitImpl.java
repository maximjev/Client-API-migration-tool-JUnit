package impl.entity;

import api.entity.MigrationClassUnit;

public class MigrationClassUnitImpl implements MigrationClassUnit {
    private String originalName;
    private String newName;

    public MigrationClassUnitImpl(String originalName, String newName) {
        this.originalName = originalName;
        this.newName = newName;
    }

    @Override
    public String getOriginalName() {
        return originalName;
    }

    @Override
    public String getNewName() {
        return newName;
    }

    @Override
    public String getOriginalIdentifier() {
        return getLastToken(originalName);
    }

    @Override
    public String getNewIdentifier() {
        return getLastToken(newName);
    }

    @Override
    public String getOriginalQualifier() {
        return getQualifier(originalName);
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
