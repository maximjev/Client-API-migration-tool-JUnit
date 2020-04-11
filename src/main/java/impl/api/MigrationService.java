package impl.api;

import api.entity.MigrationUnit;
import api.entity.MigrationUnitType;
import api.service.MigrationPackage;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.printer.ConcreteSyntaxModel;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class MigrationService<U extends MigrationUnit, N extends Node> {

    protected List<U> units = Collections.emptyList();

    protected PatternMatcherWrapper matcher = new PatternMatcherWrapper();

    @SuppressWarnings("unchecked")
    public boolean supports(MigrationPackage mu) {
        this.units = mu.getUnits()
                .stream()
                .filter(u -> supports(u.getType()))
                .map(a -> (U) a)
                .collect(toList());
        return units.size() > 0;
    }

    public void migrate(CompilationUnit cu) {
        cu.findAll(getClassType(), n -> filterPredicate(cu, n))
                .forEach(n -> process(cu, n));
    }

    protected BlockStmt formatBlock(BlockStmt blockStmt) {
        return StaticJavaParser.parseBlock(ConcreteSyntaxModel.genericPrettyPrint(blockStmt));
    }

    protected abstract boolean supports(MigrationUnitType unitType);

    protected abstract boolean filterPredicate(CompilationUnit cu, N node);

    protected abstract void process(CompilationUnit cu, N node);

    protected abstract Class<N> getClassType();
}
