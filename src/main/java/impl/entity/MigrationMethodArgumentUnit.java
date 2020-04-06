package impl.entity;

import api.entity.MigrationArgumentUnit;

public class MigrationMethodArgumentUnit implements MigrationArgumentUnit {
    private int originalPosition;
    private int newPosition;

    public MigrationMethodArgumentUnit(int originalPosition, int newPosition) {
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
