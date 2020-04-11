package impl.entity;

import api.entity.MigrationUnitArg;

public class MethodCallArgUnit implements MigrationUnitArg {
    private int originalPosition;
    private int newPosition;

    public MethodCallArgUnit(int originalPosition, int newPosition) {
        this.originalPosition = originalPosition;
        this.newPosition = newPosition;
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
