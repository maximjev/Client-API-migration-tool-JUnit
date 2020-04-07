package impl.service;

import api.entity.MigrationUnitType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import impl.api.MigrationService;
import impl.entity.MigrationAnnotationUnit;

import java.util.List;


public class AnnotationMigrationService extends MigrationService<MigrationAnnotationUnit, MarkerAnnotationExpr> {

    protected boolean supports(MigrationUnitType unitType) {
        return MigrationUnitType.MARKER_ANNOTATION.equals(unitType);
    }

    @Override
    protected boolean filterPredicate(CompilationUnit cu, MarkerAnnotationExpr node) {
        return matcher.anyMatch(node.getName(), units, "I") && hasImport(cu, units);
    }

    protected MarkerAnnotationExpr process(MarkerAnnotationExpr node) {
        MigrationAnnotationUnit unit = (MigrationAnnotationUnit) matcher.find(node.getName(), units, "I");
        return new MarkerAnnotationExpr(unit.getNewIdentifier());
    }

    protected Class<MarkerAnnotationExpr> getClassType() {
        return MarkerAnnotationExpr.class;
    }

    @SuppressWarnings("unchecked")
    private boolean hasImport(CompilationUnit cu, List<MigrationAnnotationUnit> units) {
        return !cu.findAll(ImportDeclaration.class, n -> matcher.anyMatch(n.getName(), units, "QN")).isEmpty();
    }
}
