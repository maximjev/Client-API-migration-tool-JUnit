package tool;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import visitors.JUnitAssertVisitor;
import visitors.JUnitImportAndAnnotationVisitor;
import visitors.JUnitAnnotationThrowsVisitor;


import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.of;

public class JUnitMigrationTool {

    public String migrate(String code) {
        return of(code)
                .map(JavaParser::parse)
                .map(this::migrateAnnotations)
                .map(this::migrateExceptionThrowing)
                .map(this::migrateAssertions)
                .map(CompilationUnit::toString)
                .collect(joining());
    }

    private CompilationUnit migrateAnnotations(CompilationUnit cu) {
        new JUnitImportAndAnnotationVisitor().visit(cu, null);
        return cu;
    }

    private CompilationUnit migrateExceptionThrowing(CompilationUnit cu) {
        new JUnitAnnotationThrowsVisitor().visit(cu, null);
        return cu;
    }

    private CompilationUnit migrateAssertions(CompilationUnit cu) {
        new JUnitAssertVisitor().visit(cu, null);
        return cu;
    }
}
