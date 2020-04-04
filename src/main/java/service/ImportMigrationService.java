package service;

import api.entity.MigrationClassUnit;
import api.entity.MigrationMethodUnit;
import api.service.MigrationChangeSet;
import api.service.MigrationPackage;
import api.service.MigrationService;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import impl.MigrationChangeSetImpl;
import impl.matcher.MigrationUnitMatcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportMigrationService implements MigrationService {

    private List<MigrationClassUnit> importUnits = Collections.emptyList();
    private List<MigrationMethodUnit> staticUnits = Collections.emptyList();

    private MigrationUnitMatcher<NodeWithName, MigrationClassUnit, NodeWithSimpleName, MigrationMethodUnit> matcher = new MigrationUnitMatcher<>();

    @Override
    public MigrationService setup(MigrationPackage mu) {
        staticUnits = mu.getStaticImports();
        importUnits = mu.getImports();
        return this;
    }

    @Override
    public MigrationChangeSet migrate(CompilationUnit cu) {
        Map<Node, Node> changeSet = new HashMap<>();

        cu.findAll(ImportDeclaration.class, n -> matcher.matchesName(n, importUnits))
                .forEach(n -> matcher.findByName(n, importUnits)
                        .ifPresent(u -> changeSet.put(n, new ImportDeclaration(u.getNewName(), n.isStatic(), n.isAsterisk()))));

        cu.findAll(ImportDeclaration.class, n -> matcher.matchesQualifier(n, importUnits) && n.isAsterisk())
                .forEach(n -> matcher.findByQualifier(n, importUnits)
                        .ifPresent(u -> changeSet.put(n, new ImportDeclaration(u.getNewQualifier(), n.isStatic(), true))));

        cu.findAll(ImportDeclaration.class, n -> matcher.matchesStaticMethod(n, staticUnits) && n.isStatic())
                .forEach(n -> matcher.findByStaticMethod(n, staticUnits)
                        .ifPresent(u -> changeSet.put(n, new ImportDeclaration(u.getNewFullName(), true, n.isAsterisk()))));

        return new MigrationChangeSetImpl(changeSet);
    }
}
