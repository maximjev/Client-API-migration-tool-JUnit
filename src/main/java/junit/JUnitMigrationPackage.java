package junit;

import api.entity.MigrationUnit;
import api.entity.MigrationUnitArg;
import api.service.MigrationPackage;
import com.google.common.collect.Streams;
import impl.changeset.MigrationPackageImpl;
import impl.entity.CustomMigrationUnit;
import impl.entity.MarkerAnnotationUnit;
import impl.entity.MethodCallArgUnit;
import impl.entity.MethodCallUnit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JUnitMigrationPackage {

    public static MigrationPackage getInstance() {

        List<MigrationUnitArg> threeArgs = Arrays.asList(
                new MethodCallArgUnit(1, 3),
                new MethodCallArgUnit(2, 1),
                new MethodCallArgUnit(3, 2)
        );
        List<MigrationUnitArg> twoArgs = Arrays.asList(
                new MethodCallArgUnit(1, 2),
                new MethodCallArgUnit(2, 1)
        );

        List<MigrationUnit> assertions = Arrays.asList(
                new MethodCallUnit("org.junit.Assert.assertEquals", "org.junit.jupiter.api.Assertions.assertEquals", threeArgs),
                new MethodCallUnit("org.junit.Assert.assertNotEquals", "org.junit.jupiter.api.Assertions.assertNotEquals", threeArgs),
                new MethodCallUnit("org.junit.Assert.assertArrayEquals", "org.junit.jupiter.api.Assertions.assertArrayEquals", threeArgs),
                new MethodCallUnit("org.junit.Assert.assertSame", "org.junit.jupiter.api.Assertions.assertSame", threeArgs),
                new MethodCallUnit("org.junit.Assert.assertNotSame", "org.junit.jupiter.api.Assertions.assertNotSame", threeArgs),
                new MethodCallUnit("org.junit.Assert.assertTrue", "org.junit.jupiter.api.Assertions.assertTrue", twoArgs),
                new MethodCallUnit("org.junit.Assert.assertFalse", "org.junit.jupiter.api.Assertions.assertFalse", twoArgs),
                new MethodCallUnit("org.junit.Assert.assertNull", "org.junit.jupiter.api.Assertions.assertNull", twoArgs),
                new MethodCallUnit("org.junit.Assert.assertNotNull", "org.junit.jupiter.api.Assertions.assertNotNull", twoArgs),
                new MethodCallUnit("org.junit.Assert.assertAll", "org.junit.jupiter.api.Assertions.assertAll", threeArgs)
        );

        Map<String, String> params = new HashMap<>();
        params.put("annotation", "Test");
        params.put("keyParam", "expected");
        params.put("newImport", "org.junit.jupiter.api.Assertions");
        params.put("oldImport", "org.junit.Assert");
        params.put("scope", "Assertions");
        params.put("method", "assertThrows");

        List<MigrationUnit> custom = Arrays.asList(
                new CustomMigrationUnit("expectedToAssertThrows", params)
        );

        List<MigrationUnit> assumptions = Arrays.asList(
                new MethodCallUnit("org.junit.Assume.assumeThat", "org.junit.jupiter.api.Assumptions.assumeThat", threeArgs),
                new MethodCallUnit("org.junit.Assume.assumeTrue", "org.junit.jupiter.api.Assumptions.assumeTrue", twoArgs),
                new MethodCallUnit("org.junit.Assume.assumeFalse", "org.junit.jupiter.api.Assumptions.assumeFalse", twoArgs),
                new MethodCallUnit("org.junit.Assume.assumeNoException", "org.junit.jupiter.api.Assumptions.assumeNoException", twoArgs)
        );

        List<MigrationUnit> annotations = Arrays.asList(
                new MarkerAnnotationUnit("org.junit.Before", "org.junit.jupiter.api.BeforeEach"),
                new MarkerAnnotationUnit("org.junit.After", "org.junit.jupiter.api.AfterEach"),
                new MarkerAnnotationUnit("org.junit.BeforeClass", "org.junit.jupiter.api.BeforeAll"),
                new MarkerAnnotationUnit("org.junit.AfterClass", "org.junit.jupiter.api.AfterAll"),
                new MarkerAnnotationUnit("org.junit.Ignore", "org.junit.jupiter.api.Disabled"),
                new MarkerAnnotationUnit("org.junit.Test", "org.junit.jupiter.api.Test")
        );

        List<MigrationUnit> combined = Streams.concat(
                assertions.stream(),
                assumptions.stream(),
                annotations.stream(),
                custom.stream()
        ).collect(Collectors.toList());

        return new MigrationPackageImpl(combined);
    }
}
