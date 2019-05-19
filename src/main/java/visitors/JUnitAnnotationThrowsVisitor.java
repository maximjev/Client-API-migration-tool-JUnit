package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.printer.PrettyPrinter;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;

import static utils.Constants.*;

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

            MethodDeclaration method = (MethodDeclaration) n.getParentNode().get();
            if (!method.getBody().isPresent()) {
                continue;
            }

            n.tryAddImportToParentCompilationUnit(Assertions.class);

            method.setBody(new BlockStmt(new NodeList<>(constructLambdaStatement(method.getBody().get(), exception))));

            i.remove();
            if (n.getPairs().isEmpty()) {
                n.replace(StaticJavaParser.parseAnnotation("@" + TEST_STRING));
            }
            break;
        }
    }

    private ExpressionStmt constructLambdaStatement(BlockStmt body, String exception) {
        LambdaExpr lambda = new LambdaExpr()
                .setBody(body)
                .setEnclosingParameters(true);

        return new ExpressionStmt(
                new MethodCallExpr(StaticJavaParser.parseExpression(ASSERTIONS_STRING), "assertThrows")
                        .addArgument(exception)
                        .addArgument(lambda)
        );
    }
}
