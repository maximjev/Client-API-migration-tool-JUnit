package impl.entity;

import api.entity.MigrationUnit;
import api.entity.MigrationUnitType;

import java.util.Map;

public class CustomMigrationUnit implements MigrationUnit {
    private String name;
    private Map<String, String> params;

    public CustomMigrationUnit(String name, Map<String, String> params) {
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public MigrationUnitType getType() {
        return MigrationUnitType.CUSTOM;
    }
}
