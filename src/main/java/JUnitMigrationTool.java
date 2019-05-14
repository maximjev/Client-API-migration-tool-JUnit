import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import visitors.JUnitImportAndAnnotationVisitor;
import visitors.JunitAnnotationThrowsVisitor;


import java.util.Objects;

import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.of;

public class JUnitMigrationTool {

    public String migrate(String code) {
        return of(code)
                .map(JavaParser::parse)
                .map(this::migrateAnnotations)
                .map(this::migrateExceptionThrowing)
                .map(Objects::toString)
                .collect(joining());
    }

    private CompilationUnit migrateAnnotations(CompilationUnit cu) {
        new JUnitImportAndAnnotationVisitor().visit(cu, null);
        return cu;
    }

    private CompilationUnit migrateExceptionThrowing(CompilationUnit cu) {
        new JunitAnnotationThrowsVisitor().visit(cu, null);
        return cu;
    }
}
