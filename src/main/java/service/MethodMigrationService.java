package service;

import api.entity.MigrationUnit;
import api.entity.MigrationUnitType;
import api.service.MigrationChangeSet;
import api.service.MigrationPackage;
import api.service.MigrationService;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import impl.entity.MigrationMethodUnit;

import java.util.List;

import static api.entity.MigrationUnitType.METHOD_CALL;

public class MethodMigrationService extends MigrationService<MigrationMethodUnit, MethodCallExpr> {

    protected boolean supports(MigrationUnitType unitType) {
        return METHOD_CALL.equals(unitType);
    }

    @Override
    protected boolean filterPredicate(CompilationUnit cu, MethodCallExpr node, List<MigrationMethodUnit> units) {
        return matcher.anyMatch(node.getName(), units, "I") && hasImport(cu, units);
    }

    @Override
    protected MethodCallExpr process(MethodCallExpr node) {
        MigrationMethodUnit unit = (MigrationMethodUnit) matcher.find(node.getName(), units, "I");
        Expression scope = null;
        if (node.getScope().isPresent()) {
            scope = new NameExpr(unit.getNewQualifier().get().getIdentifier());
        }
        return new MethodCallExpr(scope, unit.getNewIdentifier(), node.getArguments());
    }

    @Override
    protected Class<MethodCallExpr> getType() {
        return MethodCallExpr.class;
    }

    private boolean hasImport(CompilationUnit cu, List<MigrationMethodUnit> units) {
        return !(cu.findAll(ImportDeclaration.class, n -> matcher.anyMatch(n.getName(), units, "QN")).isEmpty());
    }
}