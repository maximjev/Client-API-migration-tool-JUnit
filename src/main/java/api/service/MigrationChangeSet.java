package api.service;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Map;

public interface MigrationChangeSet {
    Map<Node, Node> getReplacements();

    List<ImportDeclaration> getImports();

    void addImport(ImportDeclaration importDeclaration);

    MigrationChangeSet merge(Map<Node, Node> changes);

    MigrationChangeSet merge(MigrationChangeSet other);

    boolean isChangeSetApplied();
}
