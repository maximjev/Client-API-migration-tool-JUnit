package impl.matcher;

import api.entity.types.MigrationUnitWithIdentifier;
import com.github.javaparser.ast.expr.Name;

public class NameIdentifierMatcher extends MigrationMatcher<MigrationUnitWithIdentifier, Name> {
    protected boolean match(Name node, MigrationUnitWithIdentifier unit) {
        return unit.getOriginalIdentifier().equals(node.getIdentifier());
    }

    @Override
    protected String getPattern() {
        return "I";
    }

    @Override
    protected Class<Name> getNodeType() {
        return Name.class;
    }
}
