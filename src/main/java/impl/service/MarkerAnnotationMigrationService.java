package impl.service;

import api.entity.MigrationUnitType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import impl.api.MigrationService;
import impl.entity.MarkerAnnotationUnit;

import java.util.List;


public class MarkerAnnotationMigrationService extends MigrationService<MarkerAnnotationUnit, MarkerAnnotationExpr> {

    protected boolean supports(MigrationUnitType unitType) {
        return MigrationUnitType.MARKER_ANNOTATION.equals(unitType);
    }

    @Override
    protected boolean filterPredicate(CompilationUnit cu, MarkerAnnotationExpr node) {
        return matcher.anyMatch(node.getName(), units, "I") && hasImport(cu, units);
    }

    protected void process(CompilationUnit cu, MarkerAnnotationExpr node) {
        MarkerAnnotationUnit unit = (MarkerAnnotationUnit) matcher.find(node.getName(), units, "I");
        node.replace(new MarkerAnnotationExpr(unit.getNewIdentifier()));
    }

    protected Class<MarkerAnnotationExpr> getClassType() {
        return MarkerAnnotationExpr.class;
    }

    @SuppressWarnings("unchecked")
    private boolean hasImport(CompilationUnit cu, List<MarkerAnnotationUnit> units) {
        return !cu.findAll(ImportDeclaration.class, n -> matcher.anyMatch(n.getName(), units, "QN")).isEmpty();
    }
}
