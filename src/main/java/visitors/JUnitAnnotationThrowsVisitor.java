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
import com.github.javaparser.ast.stmt.EmptyStmt;
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

        n.getPairs().forEach(p -> {
            String exception;
            if (p.getName().getIdentifier().equals(EXPECTED_STRING)
                    && n.getParentNode().isPresent()) {
                exception = p.getValue().toString();
            } else {
                return;
            }

            MethodDeclaration method = (MethodDeclaration) n.getParentNode().get();
            if (!method.getBody().isPresent()) {
                return;
            }

            n.tryAddImportToParentCompilationUnit(Assertions.class);

            NodeList<Statement> methodBody = new NodeList<>(
                    constructLambdaStatement(method.getBody().get().getStatements(), exception));

            method.setBody(new BlockStmt(methodBody));
            n.replace(StaticJavaParser.parseAnnotation("@" + TEST_STRING));
        });
    }

    private ExpressionStmt constructLambdaStatement(NodeList<Statement> body, String exception) {
        LambdaExpr lambda = new LambdaExpr()
                .setBody(new BlockStmt(body))
                .setEnclosingParameters(true);

        return new ExpressionStmt(
                new MethodCallExpr(StaticJavaParser.parseExpression(ASSERTIONS_STRING), "assertThrows")
                        .addArgument(exception)
                        .addArgument(lambda)
        );
    }
}
