package impl.matcher;

import api.entity.MigrationClassUnit;
import api.entity.MigrationMethodUnit;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


public class MigrationUnitMatcher<T extends NodeWithName, N extends MigrationClassUnit,
        S extends NodeWithSimpleName, M extends MigrationMethodUnit> {

    public MigrationUnitMatcher() {
    }

    private Optional<N> findClassByPredicate(List<N> units, Predicate<? super N> predicate) {
        return units.stream()
                .filter(predicate)
                .findFirst();
    }

    public Optional<N> findByIdentifier(T node, List<N> units) {
        return findClassByPredicate(units, u -> matchesIdentifier(node, u));
    }

    public Optional<N> findByQualifier(T node, List<N> units) {
        return findClassByPredicate(units, u -> matchesQualifier(node, u));
    }

    public Optional<N> findByName(T node, List<N> units) {
        return findClassByPredicate(units, u -> matchesName(node, u));
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

    private Optional<M> findMethodByPredicate(List<M> units, Predicate<? super M> predicate) {
        return units.stream()
                .filter(predicate)
                .findFirst();
    }

    public Optional<M> findByMethod(S node, List<M> units) {
        return findMethodByPredicate(units, u -> matchesMethod(node, u));
    }

    public boolean matchesMethod(S node, List<M> units) {
        return findByMethod(node, units).isPresent();
    }

    private boolean matchesMethod(S node, M unit) {
        return unit.getOldMethod().equals(node.getNameAsString());
    }

    public Optional<M> findByStaticMethod(T node, List<M> units) {
        return findMethodByPredicate(units, u -> matchesStaticMethod(node, u));
    }

    public boolean matchesStaticMethod(T node, List<M> units) {
        return findByStaticMethod(node, units).isPresent();
    }

    private boolean matchesStaticMethod(T node, M unit) {
        return unit.getOldFullName().equals(node.getNameAsString());
    }
}
