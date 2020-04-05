package api.service;

import api.entity.MigrationUnit;
import api.entity.MigrationUnitType;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import impl.MigrationChangeSetImpl;
import impl.matcher.PatternMatcherWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public abstract class MigrationService<U extends MigrationUnit, N extends Node> {

    protected List<U> units = Collections.emptyList();

    protected PatternMatcherWrapper matcher = new PatternMatcherWrapper();

    @SuppressWarnings("unchecked")
    public MigrationService setup(MigrationPackage mu) {
        this.units = mu.getUnits()
                .stream()
                .filter(u -> supports(u.getType()))
                .map(a -> (U) a)
                .collect(toList());
        return this;
    }

    protected abstract boolean supports(MigrationUnitType unitType);

    public MigrationChangeSet migrate(CompilationUnit cu) {
        Map<Node, Node> changeSet = new HashMap<>();

        cu.findAll(getType(), n -> filterPredicate(cu, n, units))
                .forEach(n -> changeSet.put(n, process(n)));

        return new MigrationChangeSetImpl(changeSet);
    }

    protected abstract boolean filterPredicate(CompilationUnit cu, N node, List<U> units);

    protected abstract N process(N node);

    protected abstract Class<N> getType();


}
