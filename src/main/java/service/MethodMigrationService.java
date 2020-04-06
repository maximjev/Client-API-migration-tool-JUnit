package service;

import api.entity.MigrationArgumentUnit;
import api.entity.MigrationUnitType;
import api.service.MigrationService;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import impl.entity.MigrationMethodUnit;
import javassist.expr.Expr;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        Expression scope = resolveScope(node, unit);
        NodeList<Expression> arguments = resolveArgumentPositions(node.getArguments(), unit.getArguments());
        return new MethodCallExpr(scope, unit.getNewIdentifier(), arguments);
    }

    private Expression resolveScope(MethodCallExpr node, MigrationMethodUnit unit) {
        return node.getScope().map(s -> new NameExpr(unit.getNewQualifier().get().getIdentifier()))
                .orElse(new NameExpr());
    }

    private NodeList<Expression> resolveArgumentPositions(NodeList<Expression> nodeList, List<MigrationArgumentUnit> arguments) {
        if (!validateArguments(nodeList, arguments)) {
            return nodeList;
        }

        NodeList<Expression> newNodeList = new NodeList<>();
        newNodeList.addAll(arguments.stream()
                .sorted(Comparator.comparing(MigrationArgumentUnit::getNewPosition))
                .map(a -> nodeList.get(a.getOriginalPosition() - 1))
                .collect(Collectors.toList()));
        return newNodeList;
    }

    private boolean validateArguments(NodeList<Expression> nodeList, List<MigrationArgumentUnit> arguments) {
        if (nodeList.size() != arguments.size() || nodeList.size() == 0) {
            return false;
        }
        int originalArgSum = arguments.stream()
                .map(MigrationArgumentUnit::getOriginalPosition)
                .reduce(0, Integer::sum);
        int newArgSum = arguments.stream()
                .map(MigrationArgumentUnit::getNewPosition)
                .reduce(0, Integer::sum);

        if (originalArgSum != newArgSum) {
            return false;
        }
        return true;
    }

    @Override
    protected Class<MethodCallExpr> getType() {
        return MethodCallExpr.class;
    }

    private boolean hasImport(CompilationUnit cu, List<MigrationMethodUnit> units) {
        return !(cu.findAll(ImportDeclaration.class, n -> matcher.anyMatch(n.getName(), units, "QN")).isEmpty());
    }
}