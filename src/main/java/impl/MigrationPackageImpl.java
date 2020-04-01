package impl;

import api.entity.MigrationAnnotationUnit;
import api.entity.MigrationMethodUnit;
import api.matcher.MigrationUnitWithClass;
import api.service.MigrationPackage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MigrationPackageImpl implements MigrationPackage {

    private List<MigrationAnnotationUnit> annotations;
    private List<MigrationMethodUnit> methods;

    public MigrationPackageImpl(List<MigrationAnnotationUnit> annotations, List<MigrationMethodUnit> methods) {
        this.annotations = annotations;
        this.methods = methods;
    }

    @Override
    public List<MigrationAnnotationUnit> getAnnotations() {
        return annotations;
    }

    @Override
    public List<MigrationMethodUnit> getMethods() {
        return methods;
    }

    @Override
    public List<MigrationUnitWithClass> getImports() {
        return Stream.concat(annotations.stream(), methods.stream())
                .collect(Collectors.toList());
    }
}
