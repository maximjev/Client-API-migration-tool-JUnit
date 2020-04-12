package api.tool;


import impl.MigrationService;

public interface MigrationTool {
    String migrate(String code);

    void addMigrationService(MigrationService service);
}
