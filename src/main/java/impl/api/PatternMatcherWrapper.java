package impl.api;

import impl.matcher.*;
import impl.type.MigrationUnitImpl;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

public class PatternMatcherWrapper<T extends MigrationUnitImpl, N> {
    private final List<MigrationMatcher> matchers;

    public PatternMatcherWrapper() {
        matchers = Arrays.asList(
                new NameIdentifierMatcher(),
                new NameMatcher(),
                new NameQualifierMatcher(),
                new SimpleNameIdentifierMatcher()
        );
    }

    @SuppressWarnings("unchecked")
    public boolean anyMatch(N node, List<T> units, String pattern) {
        return resolveMatchers(node, pattern)
                .stream()
                .anyMatch(m -> m.matches(node, units));
    }

    @SuppressWarnings("unchecked")
    public boolean allMatch(N node, List<T> units, String pattern) {
        return resolveMatchers(node, pattern)
                .stream()
                .allMatch(m -> m.matches(node, units));
    }

    @SuppressWarnings("unchecked")
    public T find(N node, List<T> units, String pattern) {
        return (T) resolveMatchers(node, pattern)
                .stream()
                .filter(m -> m.matches(node, units))
                .map(m -> m.find(node, units))
                .findFirst()
                .get();
    }

    @SuppressWarnings("unchecked")
    private List<MigrationMatcher> resolveMatchers(N node, String pattern) {
        return matchers.stream()
                .filter(m -> m.supports(node, pattern))
                .collect(toList());
    }
}
