package utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.types.ResolvedType;
import exception.ParsingException;
import org.junit.jupiter.api.*;

import static utils.Constants.*;

public class MigrationUtils {

    public static String resolveAssertAssume(String old) {
        switch (old) {
            case ASSERT_STRING:
                return ASSERTIONS_STRING;
            case ASSUME_STRING:
                return ASSUMPTIONS_STRING;
            default:
                return old;
        }
    }

    public static String resolveAnnotation(String old) {
        return ANNOTATION_MAP.get(old) != null ? ANNOTATION_MAP.get(old) : old;
    }

    public static String resolveImportName(Expression expression) {
        try {
            ResolvedType type = expression.calculateResolvedType();
            return type.describe();
        } catch (UnsolvedSymbolException ex) {
            return ex.getName();
        } catch(RuntimeException ex) {
            return "";
        }
    }

    public static Class resolveAnnotationImport(String annotation) {
        switch (annotation) {
            case "BeforeEach":
                return BeforeEach.class;
            case "AfterEach":
                return AfterEach.class;
            case "BeforeAll":
                return BeforeAll.class;
            case "AfterAll":
                return AfterAll.class;
            case "Disabled":
                return Disabled.class;
            default:
                throw new ParsingException("Annotation not resolved");
        }
    }
}
