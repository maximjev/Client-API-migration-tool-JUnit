package service;

import api.entity.MigrationAnnotationUnit;
import api.service.MigrationChangeSet;
import api.service.MigrationPackage;
import api.service.MigrationService;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import impl.MigrationChangeSetImpl;
import impl.matcher.MigrationUnitWithClassMatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationMigrationService implements MigrationService {

    private List<MigrationAnnotationUnit> units = Collections.emptyList();

    private MigrationUnitWithClassMatcher<NodeWithName, MigrationAnnotationUnit> matcher = new MigrationUnitWithClassMatcher<>();

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

        cu.findAll(ImportDeclaration.class, n -> matcher.matchesQualifier(n, units))
                .stream()
                .filter(ImportDeclaration::isAsterisk)
                .forEach(n -> matcher.findByQualifier(n, units)
                        .ifPresent(u -> changeSet.put(n, new ImportDeclaration(u.getNewQualifier(), n.isStatic(), true))));

        cu.findAll(ImportDeclaration.class, n -> matcher.matchesName(n, units))
                .forEach(n -> matcher.findByName(n, units)
                        .ifPresent(u -> changeSet.put(n, new ImportDeclaration(u.getNewName(), n.isStatic(), false))));


        return new MigrationChangeSetImpl(changeSet);
    }

    private boolean hasImport(CompilationUnit cu, List<MigrationAnnotationUnit> units) {
        return !cu.findAll(ImportDeclaration.class, n -> matcher.matchesQualifier(n, units)).isEmpty()
                || !cu.findAll(ImportDeclaration.class, n -> matcher.matchesName(n, units)).isEmpty();
    }
}
