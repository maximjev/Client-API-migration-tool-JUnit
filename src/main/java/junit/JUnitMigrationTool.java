package junit;

import api.service.MigrationChangeSet;
import api.service.MigrationService;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import service.AnnotationMigrationService;
import api.service.MigrationPackage;
import api.service.MigrationTool;
import service.ImportMigrationService;
import service.MethodMigrationService;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.of;

public class JUnitMigrationTool implements MigrationTool {
    private MigrationPackage migrationPackage;

    public JUnitMigrationTool(MigrationPackage migrationPackage) {
        this.migrationPackage = migrationPackage;
    }

    public String migrate(String code) {
        configure();
        return of(code)
                .map(StaticJavaParser::parse)
                .map(LexicalPreservingPrinter::setup)
                .map(this::process)
                .map(LexicalPreservingPrinter::print)
//                .map(ConcreteSyntaxModel::genericPrettyPrint)
                .map(Objects::toString)
                .collect(joining());
    }

    private void configure() {
        TypeSolver typeSolver = new CombinedTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
    }

    private CompilationUnit process(CompilationUnit cu) {
        getMigrationServices()
                .stream()
                .map(m -> m.setup(migrationPackage))
                .map(m -> m.migrate(cu))
                .reduce((cs, o) -> o.merge(cs))
                .ifPresent(cs -> applyChangeSet(cs, cu));

        return cu;
    }

    private void applyChangeSet(MigrationChangeSet changeSet, CompilationUnit cu) {
        changeSet.getChangeSet().forEach((o, n) -> {
            o.replace(n);
            cu.replace(o, n);
        });
    }

    private List<MigrationService> getMigrationServices() {
        return Arrays.asList(
                new AnnotationMigrationService(),
                new ImportMigrationService(),
                new MethodMigrationService()
        );
    }
}
