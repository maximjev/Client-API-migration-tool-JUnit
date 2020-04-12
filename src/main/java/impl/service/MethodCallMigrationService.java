package impl.service;

import api.entity.MigrationUnitArg;
import api.entity.MigrationUnitType;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import impl.api.MigrationService;
import impl.entity.MethodCallUnit;

import java.util.Comparator;
import java.util.List;

import static api.entity.MigrationUnitType.METHOD_CALL;

public class MethodCallMigrationService extends MigrationService<MethodCallUnit, MethodCallExpr> {

    protected boolean supports(MigrationUnitType unitType) {
        return METHOD_CALL.equals(unitType);
    }

    @Override
    protected boolean filterPredicate(CompilationUnit cu, MethodCallExpr node) {
        return matcher.anyMatch(node.getName(), units, "I") && hasImport(cu, units);
    }

    @Override
    protected void process(CompilationUnit cu, MethodCallExpr node) {
        MethodCallUnit unit = (MethodCallUnit) matcher.find(node.getName(), units, "I");

        Expression scope = resolveScope(node, unit);
        node.setScope(scope);
        node.setName(unit.getNewIdentifier());
        resolveArgumentPositions(node.getArguments(), unit.getArguments());
    }

    private Expression resolveScope(MethodCallExpr node, MethodCallUnit unit) {
        return node.getScope().map(s -> new NameExpr(unit.getNewQualifier().get().getIdentifier()))
                .orElse(null);
    }

    private void resolveArgumentPositions(NodeList<Expression> nodeList, List<MigrationUnitArg> arguments) {
        if (!validateArguments(nodeList, arguments)) {
            return;
        }

        NodeList<Expression> newNodeList = new NodeList<>();
        arguments.stream()
                .sorted(Comparator.comparing(MigrationUnitArg::getNewPosition))
                .forEach(a -> {
                    Expression arg = nodeList.get(a.getOriginalPosition() - 1);
                    newNodeList.add(a.getNewPosition() - 1, arg);
                });
        nodeList.removeAll(newNodeList);
        nodeList.addAll(newNodeList);
    }

    private boolean validateArguments(NodeList<Expression> nodeList, List<MigrationUnitArg> arguments) {
        if (nodeList.size() != arguments.size() || nodeList.size() == 0) {
            return false;
        }
        int originalArgSum = arguments.stream()
                .map(MigrationUnitArg::getOriginalPosition)
                .reduce(0, Integer::sum);
        int newArgSum = arguments.stream()
                .map(MigrationUnitArg::getNewPosition)
                .reduce(0, Integer::sum);

        if (originalArgSum != newArgSum) {
            return false;
        }
        return arguments.stream()
                .filter(a -> a.getOriginalType().isPresent())
                .allMatch(a -> matchesResolvedType(a, nodeList.get(a.getOriginalPosition() - 1)));
    }

    private boolean matchesResolvedType(MigrationUnitArg arg, Expression expression) {
        try {
            return expression.calculateResolvedType().describe().equals(arg.getOriginalType().get());
        } catch (UnsolvedSymbolException ex) {
            return false;
        }
    }

    @Override
    protected Class<MethodCallExpr> getClassType() {
        return MethodCallExpr.class;
    }

    private boolean hasImport(CompilationUnit cu, List<MethodCallUnit> units) {
//        return true;
        return !(cu.findAll(ImportDeclaration.class, n -> matcher.anyMatch(n.getName(), units, "QN")).isEmpty());
    }
}