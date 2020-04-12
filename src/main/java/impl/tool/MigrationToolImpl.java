package impl.tool;

import api.service.MigrationPackage;
import api.service.MigrationTool;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import impl.api.MigrationService;
import impl.service.CustomMigrationService;
import impl.service.ImportDeclarationMigrationService;
import impl.service.MarkerAnnotationMigrationService;
import impl.service.MethodCallMigrationService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.of;

public class MigrationToolImpl implements MigrationTool {
    private List<MigrationService> migrationServices;
    private MigrationPackage migrationPackage;

    public MigrationToolImpl(MigrationPackage migrationPackage) {
        this.migrationPackage = migrationPackage;
        this.migrationServices = getMigrationServices();
    }

    public String migrate(String code) {
        configure();
        return of(code)
                .map(StaticJavaParser::parse)
                .map(LexicalPreservingPrinter::setup)
                .map(this::process)
                .map(LexicalPreservingPrinter::print)
                .map(Objects::toString)
                .collect(joining());
    }

    private void configure() {
        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        TypeSolver javaParserTypeSolver = new ReflectionTypeSolver();
        typeSolver.add(javaParserTypeSolver);
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
    }

    private CompilationUnit process(CompilationUnit cu) {
        this.migrationServices
                .stream()
                .filter(m -> m.supports(migrationPackage))
                .forEach(m -> m.migrate(cu));
        return cu;
    }

    private List<MigrationService> getMigrationServices() {
        return Arrays.asList(
                new MarkerAnnotationMigrationService(),
                new MethodCallMigrationService(),
                new CustomMigrationService(),
                new ImportDeclarationMigrationService()
        );
    }

    public void addMigrationService(MigrationService service) {
        this.migrationServices.add(0, service);
    }
}
