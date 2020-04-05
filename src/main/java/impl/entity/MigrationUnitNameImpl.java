package impl.entity;

import api.entity.types.MigrationUnitName;

import java.util.Optional;

public class MigrationUnitNameImpl implements MigrationUnitName {

    private String name;

    private String identifier;

    private MigrationUnitName qualifier;

    public MigrationUnitNameImpl(String name) {
        this.name = name;
        this.identifier = getLastToken(name);

        if (!name.equals(identifier)) {
            this.qualifier = new MigrationUnitNameImpl(getQualifier(name));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Optional<MigrationUnitName> getQualifier() {
        return Optional.ofNullable(this.qualifier);
    }

    private String getLastToken(String name) {
        String[] tokens = name.split("\\.");
        if (tokens.length == 0) {
            return name;
        }
        return tokens[tokens.length - 1];
    }

    private String getQualifier(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }

    @Override
    public String toString() {
        return name;
    }
}
