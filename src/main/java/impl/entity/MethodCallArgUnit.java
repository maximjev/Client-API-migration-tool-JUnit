package impl.entity;

import api.entity.MigrationUnitArg;

import java.util.Optional;

public class MethodCallArgUnit implements MigrationUnitArg {
    private String type;
    private int originalPosition;
    private int newPosition;

    public MethodCallArgUnit(int originalPosition, int newPosition) {
        this.originalPosition = originalPosition;
        this.newPosition = newPosition;
    }

    public MethodCallArgUnit(String type, int originalPosition, int newPosition) {
        this.type = type;
        this.originalPosition = originalPosition;
        this.newPosition = newPosition;
    }

    @Override
    public Optional<String> getOriginalType() {
        return Optional.ofNullable(type);
    }

    @Override
    public int getOriginalPosition() {
        return originalPosition;
    }

    @Override
    public int getNewPosition() {
        return newPosition;
    }
}
