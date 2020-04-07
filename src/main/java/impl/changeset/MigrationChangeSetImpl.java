package impl;

import com.github.javaparser.ast.Node;
import api.service.MigrationChangeSet;

import java.util.Map;

public class MigrationChangeSetImpl implements MigrationChangeSet {

    private Map<Node, Node> changeSet;

    public MigrationChangeSetImpl(Map<Node, Node> changeSet) {
        this.changeSet = changeSet;
    }

    @Override
    public Map<Node, Node> getChangeSet() {
        return changeSet;
    }

    @Override
    public MigrationChangeSet merge(Map<Node, Node> changes) {
        changeSet.putAll(changes);
        return this;
    }

    @Override
    public MigrationChangeSet merge(MigrationChangeSet other) {
        changeSet.putAll(other.getChangeSet());
        return this;
    }

    @Override
    public boolean isChangeSetApplied() {
        return true;
    }
}
