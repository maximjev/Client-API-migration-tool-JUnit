package service;

import api.entity.MigrationClassUnit;
import api.entity.MigrationMethodUnit;
import api.entity.types.MigrationUnitWithMethod;
import api.service.MigrationChangeSet;
import api.service.MigrationPackage;
import api.service.MigrationService;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import impl.MigrationChangeSetImpl;
import impl.matcher.MigrationUnitMatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationMigrationService implements MigrationService {

    private List<MigrationClassUnit> units = Collections.emptyList();
    private MigrationUnitMatcher<NodeWithName, MigrationClassUnit, NodeWithSimpleName, MigrationMethodUnit> matcher = new MigrationUnitMatcher<>();

    @Override
    public MigrationService setup(MigrationPackage mu) {
        units = mu.getAnnotations();
        return this;
    }

    @Override
    public MigrationChangeSet migrate(CompilationUnit cu) {
        Map<Node, Node> changeSet = new HashMap<>();

        cu.findAll(MarkerAnnotationExpr.class, n -> matcher.matchesIdentifier(n, units) && hasImport(cu, units))
                .forEach(n -> matcher.findByIdentifier(n, units)
                        .ifPresent(u -> changeSet.put(n, new MarkerAnnotationExpr(u.getNewIdentifier()))));

        return new MigrationChangeSetImpl(changeSet);
    }

    private boolean hasImport(CompilationUnit cu, List<MigrationClassUnit> units) {
        return !cu.findAll(ImportDeclaration.class, n -> matcher.matchesQualifier(n, units)).isEmpty()
                || !cu.findAll(ImportDeclaration.class, n -> matcher.matchesName(n, units)).isEmpty();
    }
}
