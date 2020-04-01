package impl.matcher;

import com.github.javaparser.ast.nodeTypes.NodeWithName;
import api.matcher.MigrationUnitWithClass;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class MigrationUnitWithClassMatcher<T extends NodeWithName, N extends MigrationUnitWithClass> {

    public MigrationUnitWithClassMatcher() {
    }

    private Optional<N> findByPredicate(List<N> units, Predicate<? super N> predicate) {
        return units.stream()
                .filter(predicate)
                .findFirst();
    }

    public Optional<N> findByIdentifier(T node, List<N> units) {
        return findByPredicate(units, u -> matchesIdentifier(node, u));
    }

    public Optional<N> findByQualifier(T node, List<N> units) {
        return findByPredicate(units, u -> matchesQualifier(node, u));
    }

    public Optional<N> findByName(T node, List<N> units) {
        return findByPredicate(units, u -> matchesName(node, u));
    }

    public boolean matchesIdentifier(T node, List<N> units) {
        return findByIdentifier(node, units).isPresent();
    }

    public boolean matchesQualifier(T node, List<N> units) {
        return findByQualifier(node, units).isPresent();
    }

    public boolean matchesName(T node, List<N> units) {
        return findByName(node, units).isPresent();
    }

    private boolean matchesIdentifier(T node, N unit) {
        return unit.getOldIdentifier().equals(node.getName().getIdentifier());
    }

    private boolean matchesQualifier(T node, N unit) {
        return unit.getOldQualifier().equals(node.getName().asString());
    }

    private boolean matchesName(T node, N unit) {
        return unit.getOldName().equals(node.getNameAsString());
    }
}
