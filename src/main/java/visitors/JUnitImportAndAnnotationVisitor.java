package visitors;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JUnitImportAndAnnotationVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(ImportDeclaration n, Void arg) {
        super.visit(n, arg);
        String oldQualifier = "org.junit";
        String newPrefix = "org.junit.jupiter.api.";
        String newIdentifier = resolveNewAnnotation(n.getName().getIdentifier());

        n.getName().getQualifier().ifPresent(q -> {
            if(q.toString().equals(oldQualifier)) {
                n.setName(newPrefix + newIdentifier);
            }
        });
    }

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        super.visit(n, arg);

        String newAnnotation = resolveNewAnnotation(n.getNameAsString());
        if (!newAnnotation.equals(n.getNameAsString())) {
            n.setName(newAnnotation);
        }
    }

    private String resolveNewAnnotation(String old) {
        switch (old) {
            case "Before":
                return "BeforeEach";
            case "After":
                return "AfterEach";
            case "BeforeClass":
                return "BeforeAll";
            case "AfterClass":
                return "AfterAll";
            case "Ignore":
                return "Disabled";
            default:
                return old;
        }
    }
}
