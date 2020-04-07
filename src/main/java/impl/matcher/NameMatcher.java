package impl.matcher;

import api.entity.types.MigrationUnitWithFullName;
import com.github.javaparser.ast.expr.Name;
import impl.api.MigrationMatcher;
import impl.entity.MigrationMethodUnit;

public class NameMatcher extends MigrationMatcher<MigrationUnitWithFullName, Name> {
    protected boolean match(Name node, MigrationUnitWithFullName unit) {
        if (unit.getClass().isAssignableFrom(MigrationMethodUnit.class)) {
            return unit.getOriginalName().getQualifier().get().getName().equals(node.toString())
                    || unit.getOriginalName().getName().equals(node.toString());
        } else {
            return unit.getOriginalName().getName().equals(node.toString());
        }
    }

    @Override
    protected String getPattern() {
        return "N";
    }

    @Override
    protected Class<Name> getNodeType() {
        return Name.class;
    }
}
