package tool;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.ConcreteSyntaxModel;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.google.common.collect.ImmutableList;
import exception.ParsingException;
import visitors.*;


import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.of;
import static utils.MigrationUtils.resolveAnnotationImport;

public class JUnitMigrationTool {

    public String migrate(String code) {
        configure();

        return of(code)
                .map(StaticJavaParser::parse)
                .map(LexicalPreservingPrinter::setup)
                .map(this::migrateExceptionThrowing)
                .map(this::migrateImports)
                .map(this::migrateAnnotations)
                .map(this::migrateAssertions)
                .map(LexicalPreservingPrinter::print)
//                .map(ConcreteSyntaxModel::genericPrettyPrint)
                .collect(joining());
    }

    public static void configure() {
        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
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
