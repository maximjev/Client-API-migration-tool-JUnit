package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import utils.MigrationUtils;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static utils.Constants.NEW_IMPORT;
import static utils.Constants.OLD_IMPORT;

public class JUnitImportVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(ImportDeclaration n, Void arg) {
        super.visit(n, arg);
        Name oldQualifier = JavaParser.parseName(OLD_IMPORT);
        Name newQualifier = JavaParser.parseName(NEW_IMPORT);

        String newIdentifier = Stream.of(n.getName().getIdentifier())
                .map(MigrationUtils::resolveNewAnnotation)
                .map(MigrationUtils::resolveAssertAssume)
                .collect(joining());

        n.getName().getQualifier().ifPresent(q -> {
            if (q.equals(oldQualifier)) {
                n.setName(newIdentifier);
                n.getName().setQualifier(newQualifier);
            }
        });
        if (n.isAsterisk() && n.getName().equals(oldQualifier)) {
            n.setName(newQualifier);
        }
    }
}
