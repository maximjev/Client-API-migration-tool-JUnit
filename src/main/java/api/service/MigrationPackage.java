package api.service;

import api.entity.MigrationAnnotationUnit;
import api.entity.MigrationMethodUnit;
import api.entity.MigrationUnit;
import api.matcher.MigrationUnitWithClass;

import java.util.List;

public interface MigrationPackage {
    List<MigrationAnnotationUnit> getAnnotations();

    List<MigrationMethodUnit> getMethods();

    List<MigrationUnitWithClass> getImports();
}
