package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


import static utils.MigrationUtils.resolveAnnotation;

public class JUnitAnnotationVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        super.visit(n, arg);
        String newAnnotation = resolveAnnotation(n.getNameAsString());
        if (!newAnnotation.equals(n.getNameAsString())) {
            n.replace(StaticJavaParser.parseAnnotation("@" + newAnnotation));
        }
    }
}
