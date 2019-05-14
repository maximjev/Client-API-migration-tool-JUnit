package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class JUnitAssertVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(ImportDeclaration n, Void arg) {
        super.visit(n, arg);

        Name oldQualifier = JavaParser.parseName(Constants.OLD_IMPORT.toString());
        Name newQualifier = JavaParser.parseName(Constants.NEW_IMPORT.toString());
        String newIdentifier = resolveNewClass(n.getName().getIdentifier());

        n.getName().getQualifier().ifPresent(q -> {
            if (q.equals(oldQualifier)) {
                n.setName(newIdentifier);
                n.getName().setQualifier(newQualifier);
            }
        });
        if (n.isAsterisk() && n.getName().equals(oldQualifier)) {
            n.setName(newQualifier);
        }
    }

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        super.visit(n, arg);

        if(!n.isClassExpr()) {
            return;
        }
    }

    private String resolveNewClass(String old) {
        return old.equals("Assert") ? "Assertions" : old;
    }
}
