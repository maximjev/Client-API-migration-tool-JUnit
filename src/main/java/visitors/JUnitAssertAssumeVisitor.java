package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static utils.Constants.*;
import static utils.MigrationUtils.resolveImportName;

public class JUnitAssertAssumeVisitor extends VoidVisitorAdapter<Void> {

    private static final String STRING_REF = "java.lang.String";

    private static final Set<String> ASSERT_METHODS = new HashSet<>(
            Arrays.asList("assertFalse", "assertArrayEquals", "assertNotSame",
                    "assertSame", "assertTrue", "assertNull", "assertNotNull"));

    private static final Set<String> ASSUME_METHODS = new HashSet<>(
            Arrays.asList("assumeTrue", "assumeFalse", "assumeNoException", "assumeNotNull", "assumeNull")
    );

    private static final Set<String> ASSERT_OVERRIDEN_METHODS = new HashSet<>(
            Arrays.asList("assertEquals", "assertNotEquals")
    );

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        super.visit(n, arg);

        if (!isAssertMethod(n.getNameAsString()) && !isAssumeMethod(n.getNameAsString())) {
            return;
        }

        if (n.getScope().isPresent()) {
            if (isAssert(n.getScope().get().toString())) {
                n.setScope(StaticJavaParser.parseExpression(ASSERTIONS_STRING));
            }
            if (isAssume(n.getScope().get().toString())) {
                n.setScope(StaticJavaParser.parseExpression(ASSUMPTIONS_STRING));
            }
        } else {
            String importString = resolveImportName(n);
            if (!isStatic(importString)) {
                return;
            }

        }

        Expression argument = n.getArgument(0);

        if (resolveImportName(argument).equals(STRING_REF) ||
                (isStringOverridenMethod(n.getNameAsString()) && n.getArguments().size() == 3)) {
            n.getArguments().removeFirst();
            n.getArguments().addLast(argument);
        }
    }

    private boolean isAssert(String className) {
        return ASSERT_STRING.equals(className);
    }

    private boolean isAssume(String className) {
        return ASSUME_STRING.equals(className);
    }

    private boolean isStatic(String importString) {
        return importString.equals(OLD_IMPORT + "." + ASSUME_STRING)
                || importString.equals(OLD_IMPORT + "." + ASSERT_STRING);
    }

    private boolean isAssertMethod(String method) {
        return ASSERT_METHODS.contains(method) || ASSERT_OVERRIDEN_METHODS.contains(method);
    }

    private boolean isAssumeMethod(String method) {
        return ASSUME_METHODS.contains(method);
    }

    private boolean isStringOverridenMethod(String method) {
        return ASSERT_OVERRIDEN_METHODS.contains(method);
    }
}
