package impl.matcher;

import api.entity.types.MigrationUnitWithIdentifier;
import com.github.javaparser.ast.expr.Name;
import impl.api.MigrationMatcher;

public class NameIdentifierMatcher extends MigrationMatcher<MigrationUnitWithIdentifier, Name> {
    protected boolean match(Name node, MigrationUnitWithIdentifier unit) {
        return unit.getOriginalIdentifier().equals(node.getIdentifier());
    }

    @Override
    public boolean matches(Name node, String pattern) {
        return node.getIdentifier().equals(pattern);
    }

    @Override
    protected String matcherId() {
        return "I";
    }

    @Override
    protected Class<Name> getNodeType() {
        return Name.class;
    }
}
