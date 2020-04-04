package api.entity.types;

public interface MigrationUnitWithClass extends MigrationUnitWithIdentifier, MigrationUnitWithQualifier {
    String getOldName();
    String getNewName();
}
