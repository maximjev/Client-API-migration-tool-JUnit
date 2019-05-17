package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;

import static java.lang.String.*;
import static utils.Constants.EXPECTED_STRING;
import static utils.Constants.TEST_STRING;

public class JUnitAnnotationThrowsVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(NormalAnnotationExpr n, Void arg) {
        super.visit(n, arg);
        if (!n.getNameAsString().equals(TEST_STRING)) {
            return;
        }

        Iterator<MemberValuePair> i = n.getPairs().iterator();
        while (i.hasNext()) {
            MemberValuePair p = i.next();
            String exception;
            if (p.getName().getIdentifier().equals(EXPECTED_STRING)) {
                exception = p.getValue().toString();
            } else {
                continue;
            }

            MethodDeclaration node = (MethodDeclaration) n.getParentNode().get();
            if (!node.getBody().isPresent()) {
                continue;
            }

            String line = format("{ Assertions.assertThrows(%s, () -> %s); }", exception, node.getBody().get().toString());
            n.tryAddImportToParentCompilationUnit(Assertions.class);
            node.setBody(JavaParser.parseBlock(line));

            i.remove();
        }
    }
}
