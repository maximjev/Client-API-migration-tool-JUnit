package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.Pair;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;

import static java.lang.String.*;

public class JunitAnnotationThrowsVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(NormalAnnotationExpr n, Void arg) {
        super.visit(n, arg);
        if (!n.getNameAsString().equals("Test")) {
            return;
        }

        var i = n.getPairs().iterator();
        while (i.hasNext()) {
            var p = i.next();
            String exception;
            if (p.getName().getIdentifier().equals("expected")) {
                exception = p.getValue().toString();
            } else {
                continue;
            }

            var node = (MethodDeclaration) n.getParentNode().get();
            if (!node.getBody().isPresent()) {
                continue;
            }

            var line = format("{ Assertions.assertThrows(%s, () -> %s); }", exception, node.getBody().get().toString());
            n.tryAddImportToParentCompilationUnit(Assertions.class);
            node.setBody(JavaParser.parseBlock(line));

            i.remove();
        }
    }
}
