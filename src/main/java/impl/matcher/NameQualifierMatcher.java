package impl.matcher;

import api.entity.types.MigrationUnitWithQualifier;
import com.github.javaparser.ast.expr.Name;
import impl.api.MigrationMatcher;


public class NameQualifierMatcher extends MigrationMatcher<MigrationUnitWithQualifier, Name> {
    protected boolean match(Name node, MigrationUnitWithQualifier unit) {
        return unit.getOriginalQualifier().isPresent()
                && node.getQualifier().isPresent()
                && unit.getOriginalQualifier().get().getName().equals(node.toString());
    }

    @Override
    protected String getPattern() {
        return "Q";
    }

    @Override
    protected Class<Name> getNodeType() {
        return Name.class;
    }
}
