package impl;

import api.entity.MigrationUnit;
import impl.matcher.*;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.*;

public class PatternMatcherWrapper<T extends MigrationUnit, N> {
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
    public boolean anyMatch(N node, List<T> units, String matcherId) {
        return resolveMatchers(node, matcherId)
                .stream()
                .anyMatch(m -> m.matches(node, units));
    }

    public boolean anyPatternMatch(N node, String pattern, String matcherId) {
        return resolveMatchers(node, matcherId)
                .stream()
                .anyMatch(m -> m.matches(node, pattern));
    }

    @SuppressWarnings("unchecked")
    public T find(N node, List<T> units, String matcherId) {
        return (T) resolveMatchers(node, matcherId)
                .stream()
                .filter(m -> m.matches(node, units))
                .map(m -> m.find(node, units))
                .findFirst()
                .get();
    }

    @SuppressWarnings("unchecked")
    private List<MigrationMatcher> resolveMatchers(N node, String matcherId) {
        return matchers.stream()
                .filter(m -> m.supports(node, matcherId))
                .collect(toList());
    }
}
