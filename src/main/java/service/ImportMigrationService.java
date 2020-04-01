package service;

import api.entity.MigrationAnnotationUnit;
import api.entity.MigrationMethodUnit;
import api.matcher.MigrationUnitWithClass;
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
import java.util.stream.Collectors;

public class ImportMigrationService implements MigrationService {

    private List<MigrationUnitWithClass> units = Collections.emptyList();

    private MigrationUnitWithClassMatcher<NodeWithName, MigrationUnitWithClass> matcher = new MigrationUnitWithClassMatcher<>();

    @Override
    public MigrationService setup(MigrationPackage mu) {
        units = mu.getImports();
        return this;
    }

    @Override
    public MigrationChangeSet migrate(CompilationUnit cu) {
        Map<Node, Node> changeSet = new HashMap<>();

        cu.findAll(ImportDeclaration.class, n -> matcher.matchesName(n, units))
                .forEach(n -> matcher.findByName(n, units)
                        .ifPresent(u -> changeSet.put(n, new ImportDeclaration(u.getNewName(), n.isStatic(), false))));

        cu.findAll(ImportDeclaration.class, n -> matcher.matchesQualifier(n, units) && n.isAsterisk())
                .forEach(n -> matcher.findByQualifier(n, units)
                        .ifPresent(u -> changeSet.put(n, new ImportDeclaration(u.getNewQualifier(), n.isStatic(), true))));

        return new MigrationChangeSetImpl(changeSet);
    }
}
