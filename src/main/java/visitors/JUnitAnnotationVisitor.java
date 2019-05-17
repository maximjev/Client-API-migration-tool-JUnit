package visitors;

import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


import static utils.MigrationUtils.resolveNewAnnotation;

public class JUnitAnnotationVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        super.visit(n, arg);
        n.setName(resolveNewAnnotation(n.getNameAsString()));
    }
}
