package impl.matcher;

import api.matcher.MigrationUnitWithMethod;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MigrationUnitWithMethodMatcher<T extends NodeWithSimpleName, N extends MigrationUnitWithMethod> {
    public MigrationUnitWithMethodMatcher() {
    }

    private Optional<N> findByPredicate(List<N> units, Predicate<? super N> predicate) {
        return units.stream()
                .filter(predicate)
                .findFirst();
    }

    public Optional<N> findByMethod(T node, List<N> units) {
        return findByPredicate(units, u -> matchesMethod(node, u));
    }

    public boolean matchesMethod(T node, List<N> units) {
        return findByMethod(node, units).isPresent();
    }

    private boolean matchesMethod(T node, N unit) {
        return unit.getOldMethod().equals(node.getNameAsString());
    }


    public Optional<N> findByStaticMethod(T node, List<N> units) {
        return findByPredicate(units, u -> matchesStaticMethod(node, u));
    }

    public boolean matchesStaticMethod(T node, List<N> units) {
        return findByStaticMethod(node, units).isPresent();
    }

    private boolean matchesStaticMethod(T node, N unit) {
        return unit.getOldMethod().equals(node.getNameAsExpression().getNameAsString());
    }
}
