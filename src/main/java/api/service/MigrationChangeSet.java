package api.service;

import com.github.javaparser.ast.Node;

import java.util.Map;

public interface MigrationChangeSet {
    Map<Node, Node> getChangeSet();

    MigrationChangeSet merge(Map<Node, Node> changes);

    MigrationChangeSet merge(MigrationChangeSet other);

    boolean isChangeSetApplied();
}
