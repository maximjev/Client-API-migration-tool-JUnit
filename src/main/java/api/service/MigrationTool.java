package api.service;


import impl.api.MigrationService;

public interface MigrationTool {
    String migrate(String code);

    void addMigrationService(MigrationService service);
}
