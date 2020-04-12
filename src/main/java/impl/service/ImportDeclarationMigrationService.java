package impl.service;

import api.entity.MigrationUnitType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import impl.api.MigrationService;
import impl.entity.ImportDeclarationUnit;
import impl.entity.MethodCallUnit;
import impl.type.MigrationUnitImpl;

import java.util.Set;

import static api.entity.MigrationUnitType.*;

public class ImportDeclarationMigrationService extends MigrationService<MigrationUnitImpl, ImportDeclaration> {

    private static Set<MigrationUnitType> SUPPORTED_TYPES = Set.of(
      MARKER_ANNOTATION,
      METHOD_CALL,
      IMPORT_DECLARATION
    );

    protected boolean supports(MigrationUnitType unitType) {
        return SUPPORTED_TYPES.contains(unitType);
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
