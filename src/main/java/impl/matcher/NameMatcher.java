package impl.matcher;

import api.entity.types.MigrationUnitWithFullName;
import com.github.javaparser.ast.expr.Name;
import impl.api.MigrationMatcher;
import impl.entity.MethodCallUnit;

public class NameMatcher extends MigrationMatcher<MigrationUnitWithFullName, Name> {
    protected boolean match(Name node, MigrationUnitWithFullName unit) {
        if (unit.getClass().isAssignableFrom(MethodCallUnit.class)) {
            return unit.getOriginalName().getQualifier().get().getName().equals(node.toString())
                    || unit.getOriginalName().getName().equals(node.toString());
        } else {
            return unit.getOriginalName().getName().equals(node.toString());
        }
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
