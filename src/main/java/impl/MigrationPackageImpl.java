package impl;

import api.entity.MigrationClassUnit;
import api.entity.MigrationMethodUnit;
import api.entity.types.MigrationUnitWithClass;
import api.service.MigrationPackage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MigrationPackageImpl implements MigrationPackage {

    private List<MigrationClassUnit> annotations;
    private List<MigrationMethodUnit> methods;

    public MigrationPackageImpl(List<MigrationClassUnit> annotations, List<MigrationMethodUnit> methods) {
        this.annotations = annotations;
        this.methods = methods;
    }

    @Override
    public List<MigrationClassUnit> getAnnotations() {
        return annotations;
    }

    @Override
    public List<MigrationMethodUnit> getMethods() {
        return methods;
    }

    @Override
    public List<MigrationClassUnit> getImports() {
        return Stream.concat(annotations.stream(), methods.stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<MigrationMethodUnit> getStaticImports() {
        return methods;
    }
}
