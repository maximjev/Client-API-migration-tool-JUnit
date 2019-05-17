package tool;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import visitors.JUnitAssertAssumeVisitor;
import visitors.JUnitAnnotationVisitor;
import visitors.JUnitAnnotationThrowsVisitor;
import visitors.JUnitImportVisitor;


import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.of;

public class JUnitMigrationTool {

    public String migrate(String code) {
        configure();

        return of(code)
                .map(JavaParser::parse)
                .map(this::migrateAnnotations)
                .map(this::migrateExceptionThrowing)
                .map(this::migrateAssertions)
                .map(this::migrateImports)
                .map(CompilationUnit::toString)
                .collect(joining());
    }

    private void configure() {
        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        JavaParser.getStaticConfiguration().setSymbolResolver(symbolSolver);
    }

    private CompilationUnit migrateImports(CompilationUnit cu) {
        new JUnitImportVisitor().visit(cu, null);
        return cu;
    }

    private CompilationUnit migrateAnnotations(CompilationUnit cu) {
        new JUnitAnnotationVisitor().visit(cu, null);
        return cu;
    }

    private CompilationUnit migrateExceptionThrowing(CompilationUnit cu) {
        new JUnitAnnotationThrowsVisitor().visit(cu, null);
        return cu;
    }

    private CompilationUnit migrateAssertions(CompilationUnit cu) {
        new JUnitAssertAssumeVisitor().visit(cu, null);
        return cu;
    }
}
