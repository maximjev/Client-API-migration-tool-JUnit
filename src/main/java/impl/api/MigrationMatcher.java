package impl.api;

import api.entity.MigrationUnit;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class MigrationMatcher<U extends MigrationUnit, N> {
    private Optional<U> findByPredicate(List<U> units, Predicate<U> predicate) {
        return units.stream()
                .filter(predicate)
                .findFirst();
    }

    public boolean matches(N node, List<U> units) {
        return findByPredicate(units, u -> match(node, u)).isPresent();
    }

    public U find(N node, List<U> units) {
        return findByPredicate(units, u -> match(node, u))
                .orElse(null);
    }

    protected abstract boolean match(N node, U unit);

    public boolean supports(N node, String pattern) {
        return getNodeType().isAssignableFrom(node.getClass())
                && pattern != null
                && pattern.contains(getPattern());
    }

    protected abstract String getPattern();

    protected abstract Class<N> getNodeType();
}