package api.matcher;


public interface MigrationUnitWithQualifier extends MigrationUnitWithIdentifier {
    String getOldQualifier();
    String getNewQualifier();
}
