package api.service;

import api.entity.MigrationClassUnit;
import api.entity.MigrationMethodUnit;

import java.util.List;

public interface MigrationPackage {
    List<MigrationClassUnit> getAnnotations();

    List<MigrationMethodUnit> getMethods();

    List<MigrationClassUnit> getImports();

    List<MigrationMethodUnit> getStaticMethodImports();
}
