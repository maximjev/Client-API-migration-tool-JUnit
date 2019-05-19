package visitors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import utils.MigrationUtils;

import java.util.stream.Stream;

import static java.lang.String.*;
import static java.util.stream.Collectors.joining;
import static utils.Constants.NEW_IMPORT;
import static utils.Constants.OLD_IMPORT;

public class JUnitImportVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(ImportDeclaration n, Void arg) {
        super.visit(n, arg);

        if (isStaticClassImport(n)) {
            n.getName().getQualifier().ifPresent(q -> {
                n.replace(parseStaticImport(resolveIdentifier(q.getIdentifier()), n.getName().getIdentifier()));
            });
        } else if (isClassImport(n)) {
            n.setName(resolveIdentifier(n.getName().getIdentifier()));
            n.getName().setQualifier(StaticJavaParser.parseName(NEW_IMPORT));
        } else if (isAsteriskImport(n)) {
            n.setName(StaticJavaParser.parseName(NEW_IMPORT));
        }
    }

    private String resolveIdentifier(String identifier) {
        return Stream.of(identifier)
                .map(MigrationUtils::resolveAnnotation)
                .map(MigrationUtils::resolveAssertAssume)
                .collect(joining());
    }

    private boolean isStaticClassImport(ImportDeclaration n) {
        Name oldQualifier = StaticJavaParser.parseName(OLD_IMPORT);
        return (n.isStatic()
                && n.getName().getQualifier().isPresent()
                && n.getName().getQualifier().get().getQualifier().isPresent()
                && n.getName().getQualifier().get().getQualifier().get().equals(oldQualifier));
    }

    private boolean isAsteriskImport(ImportDeclaration n) {
        return (n.isAsterisk() && n.getNameAsString().equals(OLD_IMPORT));
    }

    private boolean isClassImport(ImportDeclaration n) {
        Name oldQualifier = StaticJavaParser.parseName(OLD_IMPORT);
        return n.getName().getQualifier().isPresent()
                && n.getName().getQualifier().get().equals(oldQualifier);
    }

    private ImportDeclaration parseStaticImport(String className, String methodName) {
        return StaticJavaParser
                .parseImport(format("import %s.%s.%s;\n", NEW_IMPORT, className, methodName))
                .setStatic(true);
    }
}
