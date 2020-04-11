package impl.matcher;

import api.entity.types.MigrationUnitWithIdentifier;
import com.github.javaparser.ast.expr.SimpleName;
import impl.api.MigrationMatcher;

public class SimpleNameIdentifierMatcher extends MigrationMatcher<MigrationUnitWithIdentifier, SimpleName> {
    protected boolean match(SimpleName node, MigrationUnitWithIdentifier unit) {
        return unit.getOriginalIdentifier().equals(node.getIdentifier());
    }

    @Override
    public boolean matches(SimpleName node, String pattern) {
        return node.getIdentifier().equals(pattern);
    }

    @Override
    protected String matcherId() {
        return "I";
    }

    @Override
    protected Class<SimpleName> getNodeType() {
        return SimpleName.class;
    }
}
