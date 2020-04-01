package api.service;

import com.github.javaparser.ast.CompilationUnit;

public interface MigrationService {

    MigrationService setup(MigrationPackage mu);

    MigrationChangeSet migrate(CompilationUnit cu);
}
