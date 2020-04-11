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

    public boolean supports(N node, String matcherId) {
        return getNodeType().isAssignableFrom(node.getClass())
                && matcherId != null
                && matcherId.contains(matcherId());
    }

    public abstract boolean matches(N node, String pattern);

    protected abstract boolean match(N node, U unit);

    protected abstract String matcherId();

    protected abstract Class<N> getNodeType();
}