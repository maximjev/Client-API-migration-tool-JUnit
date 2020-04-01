package api.matcher;

public interface MigrationUnitWithClass extends MigrationUnitWithIdentifier, MigrationUnitWithQualifier {
    String getOldName();
    String getNewName();
}
