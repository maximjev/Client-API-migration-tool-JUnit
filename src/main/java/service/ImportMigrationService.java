package service;

import api.entity.MigrationUnitType;
import api.service.MigrationService;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import impl.entity.MigrationMethodUnit;
import impl.entity.MigrationUnitImpl;

import java.util.List;

import static api.entity.MigrationUnitType.*;

public class ImportMigrationService extends MigrationService<MigrationUnitImpl, ImportDeclaration> {

    protected boolean supports(MigrationUnitType unitType) {
        return MARKER_ANNOTATION.equals(unitType) ||
                METHOD_CALL.equals(unitType);
    }

    @Override
    protected boolean filterPredicate(CompilationUnit cu, ImportDeclaration node, List<MigrationUnitImpl> units) {
        return matcher.anyMatch(node.getName(), units, "NQ");
    }

    @Override
    protected ImportDeclaration process(ImportDeclaration node) {
        MigrationUnitImpl unit = matcher.find(node.getName(), units, "NQ");
        if (unit.getClass().isAssignableFrom(MigrationMethodUnit.class)) {
            return processMethodImport(node, unit);
        } else {
            return processClassImport(node, unit);
        }
    }

    private ImportDeclaration processMethodImport(ImportDeclaration node, MigrationUnitImpl unit) {
        if (node.isStatic()) {
            if (node.isAsterisk()) {
                return createImport(unit.getNewQualifier().get().getName(), node.isStatic(), node.isAsterisk());
            } else {
                return createImport(unit.getNewName().getName(), node.isStatic(), node.isAsterisk());
            }
        } else {
            if (node.isAsterisk()) {
                return createImport(unit.getNewQualifier().get().getQualifier().get().getName(), node.isStatic(), node.isAsterisk());
            } else {
                return createImport(unit.getNewQualifier().get().getName(), node.isStatic(), node.isAsterisk());
            }
        }
    }

    private ImportDeclaration processClassImport(ImportDeclaration node, MigrationUnitImpl unit) {
        if (node.isAsterisk()) {
            return createImport(unit.getNewQualifier().get().getName(), node.isStatic(), node.isAsterisk());
        } else {
            return createImport(unit.getNewName().getName(), node.isStatic(), node.isAsterisk());
        }
    }

    private ImportDeclaration createImport(String name, boolean isStatic, boolean isAsterisk) {
        return new ImportDeclaration(name, isStatic, isAsterisk);
    }

    @Override
    protected Class<ImportDeclaration> getType() {
        return ImportDeclaration.class;
    }
}
