package impl.service;

import api.entity.MigrationUnit;
import api.entity.MigrationUnitType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import impl.api.MigrationService;
import impl.entity.MethodCallUnit;
import impl.type.MigrationUnitImpl;

import static api.entity.MigrationUnitType.*;

public class ImportDeclarationMigrationService extends MigrationService<MigrationUnitImpl, ImportDeclaration> {

    protected boolean supports(MigrationUnitType unitType) {
        return MARKER_ANNOTATION.equals(unitType) ||
                METHOD_CALL.equals(unitType);
    }

    @Override
    protected boolean filterPredicate(CompilationUnit cu, ImportDeclaration node) {
        return matcher.anyMatch(node.getName(), units, "NQ");
    }

    @Override
    protected void process(CompilationUnit cu, ImportDeclaration node) {
        MigrationUnitImpl unit = (MigrationUnitImpl) matcher.find(node.getName(), units, "NQ");
        ImportDeclaration newNode;
        if (unit.getClass().isAssignableFrom(MethodCallUnit.class)) {
            newNode = processMethodImport(node, unit);
        } else {
            newNode = processClassImport(node, unit);
        }
        cu.replace(node, newNode);
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
    protected Class<ImportDeclaration> getClassType() {
        return ImportDeclaration.class;
    }
}
