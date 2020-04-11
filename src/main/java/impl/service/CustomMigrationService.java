package impl.service;

import api.entity.MigrationUnitType;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import impl.api.MigrationService;
import impl.entity.CustomMigrationUnit;

import java.util.Map;
import java.util.Optional;


public class CustomMigrationService extends MigrationService<CustomMigrationUnit, MethodDeclaration> {

    private static final String CUSTOM_MIGRATION_NAME = "expectedToAssertThrows";

    @Override
    protected boolean supports(MigrationUnitType unitType) {
        return MigrationUnitType.CUSTOM.equals(unitType);
    }

    @Override
    protected boolean filterPredicate(CompilationUnit cu, MethodDeclaration node) {
        Optional<CustomMigrationUnit> unitOptional = getUnitByName(CUSTOM_MIGRATION_NAME);
        if (unitOptional.isEmpty()) {
            return false;
        }
        Map<String, String> params = unitOptional.get().getParams();
        if (node.getAnnotationByName(params.get("annotation")).isEmpty()
                || !node.getAnnotationByName(params.get("annotation")).get().isNormalAnnotationExpr()) {
            return false;
        }

        return node.getAnnotationByName(params.get("annotation"))
                .get()
                .asNormalAnnotationExpr()
                .getPairs()
                .stream()
                .anyMatch(p -> matchKeyParam(p, params.get("keyParam")));
    }

    private Optional<CustomMigrationUnit> getUnitByName(String name) {
        return units.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst();
    }

    private boolean matchKeyParam(MemberValuePair p, String keyParam) {
        return matcher.anyPatternMatch(p.getName(), keyParam, "I");
    }

    @Override
    protected void process(CompilationUnit cu, MethodDeclaration node) {
        Map<String, String> params = getUnitByName(CUSTOM_MIGRATION_NAME).get().getParams();
        NormalAnnotationExpr annotation = (NormalAnnotationExpr) node.getAnnotationByName(params.get("annotation")).get();

        MemberValuePair pair = annotation.getPairs()
                .stream()
                .filter(p -> matchKeyParam(p, params.get("keyParam")))
                .findFirst()
                .get();

        annotation.replace(new MarkerAnnotationExpr(annotation.getName()));

        if (node.getBody().isPresent()) {
            node.setBody(constructNewBody(node.getBody().get(), pair.getValue(), params));
        }

        if (!hasImport(cu, params)) {
            cu.addImport(new ImportDeclaration(params.get("newImport"), false, false));
        }
    }

    private BlockStmt constructNewBody(BlockStmt blockStmt, Expression exception, Map<String, String> params) {
        LambdaExpr lambda = new LambdaExpr()
                .setBody(new BlockStmt(blockStmt.getStatements()))
                .setEnclosingParameters(true);

        MethodCallExpr method = new MethodCallExpr(new NameExpr(params.get("scope")), params.get("method"))
                .addArgument(exception)
                .addArgument(lambda);

        NodeList<Statement> methodStatements = new NodeList<>();
        methodStatements.add(new ExpressionStmt(method));

        return new BlockStmt(methodStatements);
    }

    private boolean hasImport(CompilationUnit cu, Map<String, String> params) {
        return hasImport(cu, params.get("oldImport"));
    }

    private boolean hasImport(CompilationUnit cu, String importParam) {
        return cu.findAll(ImportDeclaration.class,
                n -> matcher.anyPatternMatch(n.getName(), importParam, "QN")
                        && ((n.isStatic() && n.isAsterisk()) || !n.isStatic())).size() > 0;
    }

    @Override
    protected Class<MethodDeclaration> getClassType() {
        return MethodDeclaration.class;
    }
}
