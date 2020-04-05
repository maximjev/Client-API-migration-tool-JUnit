package impl.matcher;

import api.entity.types.MigrationUnitWithIdentifier;
import com.github.javaparser.ast.expr.SimpleName;

public class SimpleNameIdentifierMatcher extends MigrationMatcher<MigrationUnitWithIdentifier, SimpleName> {
    protected boolean match(SimpleName node, MigrationUnitWithIdentifier unit) {
        return unit.getOriginalIdentifier().equals(node.getIdentifier());
    }

    @Override
    protected String getPattern() {
        return "I";
    }

    @Override
    protected Class<SimpleName> getNodeType() {
        return SimpleName.class;
    }
}
