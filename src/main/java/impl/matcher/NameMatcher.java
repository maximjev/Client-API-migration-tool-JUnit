package impl.matcher;

import api.entity.types.MigrationUnitWithFullName;
import com.github.javaparser.ast.expr.Name;
import impl.api.MigrationMatcher;

public class NameMatcher extends MigrationMatcher<MigrationUnitWithFullName, Name> {
    protected boolean match(Name node, MigrationUnitWithFullName unit) {
        return matchesMethodName(node, unit) || matchesClassName(node, unit);
    }

    private boolean matchesMethodName(Name node, MigrationUnitWithFullName unit) {
        return unit.getOriginalName().getQualifier().get().getName().equals(node.toString());
    }

    private boolean matchesClassName(Name node, MigrationUnitWithFullName unit) {
        return unit.getOriginalName().getName().equals(node.toString());
    }

    @Override
    public boolean matches(Name node, String pattern) {
        return node.toString().equals(pattern);
    }

    @Override
    protected String matcherId() {
        return "N";
    }

    @Override
    protected Class<Name> getNodeType() {
        return Name.class;
    }
}
