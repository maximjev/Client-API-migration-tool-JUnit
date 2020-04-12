package impl.matcher;

import api.type.MigrationUnitWithQualifier;
import com.github.javaparser.ast.expr.Name;
import impl.MigrationMatcher;


public class NameQualifierMatcher extends MigrationMatcher<MigrationUnitWithQualifier, Name> {
    protected boolean match(Name node, MigrationUnitWithQualifier unit) {
        return presentCheck(node, unit) && (matchesMethodQualifier(node, unit) || matchesClassQualifier(node, unit));
    }

    private boolean presentCheck(Name node, MigrationUnitWithQualifier unit) {
        return unit.getOriginalQualifier().isPresent()
                && unit.getOriginalQualifier().get().getQualifier().isPresent()
                && node.getQualifier().isPresent();
    }

    private boolean matchesMethodQualifier(Name node, MigrationUnitWithQualifier unit) {
        return unit.getOriginalQualifier().get().getQualifier().get().getName().equals(node.toString());
    }

    private boolean matchesClassQualifier(Name node, MigrationUnitWithQualifier unit) {
        return unit.getOriginalQualifier().get().getName().equals(node.toString());
    }

    @Override
    public boolean matches(Name node, String pattern) {
        return node.getQualifier().isPresent()
                && node.getQualifier().get().toString().equals(pattern);
    }

    @Override
    protected String matcherId() {
        return "Q";
    }

    @Override
    protected Class<Name> getNodeType() {
        return Name.class;
    }
}
