package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JUnitImportAndAnnotationVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(ImportDeclaration n, Void arg) {
        super.visit(n, arg);
        Name oldQualifier = JavaParser.parseName(Constants.OLD_IMPORT.toString());
        Name newQualifier = JavaParser.parseName(Constants.NEW_IMPORT.toString());
        String newIdentifier = resolveNewAnnotation(n.getName().getIdentifier());

        n.getName().getQualifier().ifPresent(q -> {
            if(q.equals(oldQualifier)) {
                n.setName(newIdentifier);
                n.getName().setQualifier(newQualifier);
            }
        });
        if(n.isAsterisk() && n.getName().equals(oldQualifier)) {
            n.setName(newQualifier);
        }
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
