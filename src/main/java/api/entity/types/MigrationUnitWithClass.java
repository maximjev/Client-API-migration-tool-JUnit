package api.entity.types;

public interface MigrationUnitWithClass extends MigrationUnitWithIdentifier, MigrationUnitWithQualifier {
    String getOriginalName();
    String getNewName();
}
